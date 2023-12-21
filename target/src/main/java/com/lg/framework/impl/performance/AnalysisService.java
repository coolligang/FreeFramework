package com.lg.framework.impl.performance;

import com.lg.modle.common.Info;
import com.lg.modle.performance.AggregationReport;
import com.lg.modle.performance.PointData;
import com.lg.modle.performance.WorkReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 分析结果，计算出 tps, 平均响应时间等
 * 初始化的 List 是同一个线程的数据
 */
public class AnalysisService {
    private static Logger logger = LogManager.getLogger(AnalysisService.class);
    private Info info;
    private AggregationReport aggregationResult;                 // 存放聚合结果
    private List<PointData> listPointData = new ArrayList<>();   // 存放打点数据
    private int interval = 1000;          // 默认打点时间间隔为1000ms
    private long start = -1;              // 初始化后，第1个数据的开始时间，作为本次分析的开始时间
    private long nextPoint = -1;          // 当前时间位置
    private String taskName;              // 任务名
    private int threadCount = -1;         // 当前分析数据的线程数
    private List<WorkReport> tempWorkReports = new ArrayList<>();  // 临时存放数据
    private boolean pointData = true;       // 是否需要打点数据，默认为需要
    private ArrayList<Long> responseTimes = new ArrayList<>();  // 响应时间列表


    /**
     * 每次初始化只处理相同线程数的WorkReport
     */
    public AnalysisService(Info info) {
        this.info = info;
        this.aggregationResult = new AggregationReport(info);
    }

    /**
     * 每次初始化只处理相同线程数的WorkReport
     *
     * @param interval 取样时间间隔（单位：ms）
     */
    public AnalysisService(Info info, int interval) {
        this.info = info;
        this.interval = interval;
        this.aggregationResult = new AggregationReport(info);
    }

    public void setPointData(boolean pointData) {
        this.pointData = pointData;
    }

    public boolean isPointData() {
        return pointData;
    }

    // 设置打点间隔时间
    public void setInterval(int interval) {
        this.interval = interval;
    }

    public AggregationReport getAggregationResult() {
        if (this.responseTimes.size() > 0) {
            Collections.sort(this.responseTimes);  // 升序列
            int len = this.responseTimes.size();
            int index50 = (int) Math.ceil(len * 0.5);
            int index90 = (int) Math.ceil(len * 0.9);
            int index95 = (int) Math.ceil(len * 0.95);
            int index99 = (int) Math.ceil(len * 0.99);
            this.aggregationResult.setResponse50(this.responseTimes.get(index50 - 1));
            this.aggregationResult.setResponse90(this.responseTimes.get(index90 - 1));
            this.aggregationResult.setResponse95(this.responseTimes.get(index95 - 1));
            this.aggregationResult.setResponse99(this.responseTimes.get(index99 - 1));
            this.aggregationResult.setMinResponse(this.responseTimes.get(0));           // 第一个为最小值
            this.aggregationResult.setMaxResponse(this.responseTimes.get(len - 1));     // 最后一个为最大值
            this.aggregationResult.setSumRequest(this.responseTimes.size());            // 时间序列的总数就是请求的总数
        }
        return this.aggregationResult;
    }

    public List<PointData> listPointData() {
        // 最后一部分数据提交去分析，最后1s数据直接抛弃
//        if (tempWorkReports.size() > 0) {
//            PointData pointData = analysis(tempWorkReports, this.nextPoint);
//            this.listPointData.add(pointData);
//        }
        return this.listPointData;
    }

    private void setStart(List<WorkReport> workReports) {
        if (this.start <= 0) {
            for (WorkReport workReport : workReports) {
                if (workReport.getStartTime() > 0) {
                    this.start = workReport.getStartTime();
                    break;
                }
            }
        }
    }

    private void setTaskName(List<WorkReport> workReports) {
        if (this.taskName == null) {
            for (WorkReport workReport : workReports) {
                if (workReport.getTaskName() != null) {
                    this.taskName = workReport.getTaskName();
                    break;
                }
            }
        }
    }

    private void setThreadCount(List<WorkReport> workReports) {
        if (this.threadCount < 0) {
            for (WorkReport workReport : workReports) {
                if (workReport.getThreadCount() > 0) {
                    this.threadCount = workReport.getThreadCount();
                    break;
                }
            }
        }
    }

    private void setNextPoint() {
        if (this.nextPoint > 0) {
            this.nextPoint += this.interval;
        } else {
            this.nextPoint = this.start + this.interval;
        }
    }

    /**
     * 分析传入的 WorkReport 列表
     *
     * @param listWorkReport 批量的WorkReport列表
     */
    public void analysis(List<WorkReport> listWorkReport) {
        this.setTaskName(listWorkReport);
        this.setStart(listWorkReport);
        this.setThreadCount(listWorkReport);
        //======================= 时间点 =========================
        if (this.pointData) {
            listPointData(listWorkReport);
        }
        // ======================= 聚合结果 ======================
        // 这个利用了打点的结果，必须放在后面 ??? 利用了哪一块数据，忘了
        this.aggregationResult(listWorkReport);
    }

    /**
     * 通过一组 WorkReport 获取一个打点列表
     *
     * @param workReports 一组原始数据
     */
    private void listPointData(List<WorkReport> workReports) {
        // 设置下一个时间点
        setNextPoint();
        for (WorkReport workReport : workReports) {
            if (workReport.getEndTime() < this.nextPoint) { // 返回时间在时间内，加入列表待分析
                this.tempWorkReports.add(workReport);
            } else { // 超出时间范围，可以进行分析，计算结果
                PointData pointData = analysis(this.tempWorkReports, this.nextPoint);
                // 计算出的结果写到结果列表中
                this.listPointData.add(pointData);
                // 重新生成一个列表去接收新的数据
                this.tempWorkReports = new ArrayList<>();
                // 重置截止时间
                setNextPoint();
            }
        }
    }

    /**
     * 通过一组数据记录，返回分析结果
     *
     * @param workReports 一个时段内的 WorkReport 列表
     * @param pointTime   influxDb timestamp(ms)
     * @return AnalysisResult
     */
    private PointData analysis(List<WorkReport> workReports, long pointTime) {
        int success = 0;
        long sumDuringTime = 0;
        for (WorkReport report : workReports) {
            sumDuringTime += report.getDuring();
            if (report.isAssertResult()) success += 1;
        }
        PointData pointData = new PointData(this.info);
        pointData.setTaskName(this.taskName);
        pointData.setThreadCount(this.threadCount);
        pointData.setTps((double) workReports.size() / ((double) this.interval / 1000));
        pointData.setAvgResponseTime((double) sumDuringTime / (long) workReports.size());
        pointData.setSuccessRate((double) success / (double) workReports.size());
        // 将毫秒转为纳秒
        pointData.setPointTime(Long.parseLong(pointTime + "000000"));
        return pointData;
    }

    private void appendResponseTime(List<WorkReport> workReports) {
        for (WorkReport workReport : workReports) {
            this.responseTimes.add(workReport.during);
        }
    }

    /**
     * 聚合结果
     *
     * @param workReports list
     */
    private void aggregationResult(List<WorkReport> workReports) {
        // 设置启始时间
        this.aggregationResult.setStart(this.start);
        this.aggregationResult.setTaskName(this.taskName);
        this.aggregationResult.setThreadCount(this.threadCount);
        this.aggregationResult.setEnd(workReports.get(workReports.size() - 1).getEndTime());

        int success = 0;
        long sumDuringTime = 0;
        for (WorkReport report : workReports) {
            sumDuringTime += report.getDuring();
            if (report.isAssertResult()) success += 1;
        }
        this.aggregationResult.setSumData(workReports.size());
        this.aggregationResult.setSumSuccess(success);
        this.aggregationResult.setSumDuring(sumDuringTime);

        // 通过现在数据计算均值
        this.aggregationResult.setAvgTps((float) this.aggregationResult.getSumData() / (float) (this.aggregationResult.getEnd() - this.aggregationResult.getStart()) * 1000);
        this.aggregationResult.setAvgResponse((float) this.aggregationResult.getSumDuring() / (float) this.aggregationResult.getSumData());
        this.aggregationResult.setSuccessRate((float) this.aggregationResult.getSumSuccess() / (float) this.aggregationResult.getSumData());

        // 把响应时间添加到列表
        this.appendResponseTime(workReports);
    }
}
