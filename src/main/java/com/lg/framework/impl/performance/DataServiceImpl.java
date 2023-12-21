package com.lg.framework.impl.performance;

import com.lg.framework.service.DataService;
import com.lg.modle.common.Info;
import com.lg.modle.common.Status;
import com.lg.modle.performance.AggregationReport;
import com.lg.modle.performance.ResourceMsg;
import com.lg.modle.performance.WorkReport;
import com.lg.tool.common.FileHelper;
import com.lg.framework.impl.common.InfluxDBService;
import com.lg.framework.impl.common.ResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.*;

/**
 * 压力数据的收集及上报
 */
public class DataServiceImpl implements DataService {
    private static Logger logger = LogManager.getLogger(DataServiceImpl.class);
    private boolean working;                                   // 是否持续收集数据
    private List<WorkReport> workReports = new ArrayList<>();  // 记录下全部响应数据
    private AnalysisService analysisService;
    private Info info;
    private InfluxDBService influxDBService;
    private FileHelper fileHelper;
    private ResourceService resourceService;
    private FileHelper errorFile;

    public DataServiceImpl(Info info) {
        this.info = info;
        // 本次测试的相关数据
        this.working = Status.receiveYes;
        this.analysisService = new AnalysisService(info);
        this.influxDBService = new InfluxDBService(info);
        this.resourceService = new ResourceService(info);
    }

    public void setReportObj(FileHelper report) {
        this.fileHelper = report;
    }

    public void setErrorObj(FileHelper errorObj) {
        this.errorFile = errorObj;
    }

    /**
     * 记录下开始时间，并异步接收压力测试结果
     * 当一个线程测试结束后，或者接受数据量达到4000个以后，提交到 AnalysisService 分析结果
     */
    @Override
    public void startNow() {
        this.analysisService.setPointData(false);       // 设置不需要打点数据
        // 这里收集数据，[当前活动的线程数]
        new Thread(() -> {
            while (this.working) {
                if (!ManagerServiceImpl.workReports.isEmpty()) {
                    WorkReport workReport = ManagerServiceImpl.workReports.poll();
                    if (!workReport.isAssertResult()) {
                        if (workReport.getErrMsg() != null && !workReport.getErrMsg().equals("")) {
                            System.out.println("\033[31;1m" + workReport.getErrMsg() + "\033[0m");
                        }
                        if (this.errorFile != null && workReport.getErrMsg() != null && !workReport.getErrMsg().equals("")) {
                            this.errorFile.write("[" + workReport.getEnd() + "]  " + workReport.getErrMsg() + "\n");
                        }
                    }
                    switch (workReport.getState()) {
                        case Status.testWORKING:     // 将些数据添加到列表中，后续上传
                            this.workReports.add(workReport);
                            if (this.workReports.size() >= 4000) {  // 只有队列中有足够数据时才会上传结果，否则不传，等待当前线程结束状态变为 testSUSPEND
                                this.analysisService.analysis(this.workReports);
                                this.workReports = new ArrayList<>();
                            }
                            break;
                        case Status.testSUSPEND:  // 当前线程数结束，将列表中的已有数据上传，且当前数据为空，不需要添加到列表中
                            logger.info("Analysis ... ");
                            if (this.workReports.size() > 0) { // 只有有数据的情况下，才会上传，并处理数据
                                this.analysisService.analysis(this.workReports);
                            }
                            // 通过聚合结果，拿到此线程开始和结束时间，由此去获取物理资源数据
                            Instant start;
                            Instant end;
                            try {
                                start = this.analysisService.getAggregationResult().getStartTime();
                                end = this.analysisService.getAggregationResult().getEndTime();
                            } catch (Exception e) {
                                // 没有获取到开始时间/结束时间, 无法统计结果，退出当前case，不做后续处理
                                logger.error("Get start/end time error\n" + e.getMessage());
                                break;
                            }
                            if (start == null || end == null) {
                                logger.error("Get start/end time is null\n");
                                break;
                            }
                            // 没有prometheusService实例，即没有配置prometheus,
                            if (ManagerServiceImpl.prometheusService == null) {
                                this.fileHelper.write(this.analysisService.getAggregationResult().toCSVRow());
                            } else {
                                // 物理资源列表
                                List<ResourceMsg> resourceMsgs = this.resourceService.getResourceInfo(start, end);
                                if (resourceMsgs.size() > 0) {
                                    // 如果有物理资源数据，则写入
                                    for (ResourceMsg msg : resourceMsgs) {
                                        // 添加特征资源信息到聚合对象中
                                        this.analysisService.getAggregationResult().setResourceMsg(msg);
                                        // 把结果输出到指定目录  将聚合对象写到csv文件
                                        this.fileHelper.write(this.analysisService.getAggregationResult().toCSVRow());
                                    }
                                } else {
                                    this.fileHelper.write(this.analysisService.getAggregationResult().toCSVRow());
                                }
                            }

                            // 重新初始化，为下一个线程做准备
                            this.analysisService = new AnalysisService(info);
                            this.workReports = new ArrayList<>();
                            break;
                        case Status.testEND:      // 测试结束，将调用停止接口
                            logger.info("End ... ");
                            // 停止循环
                            this.working = Status.receiveNo;
                            break;
                    }
                }
            }
        }, "SAVE").start();
    }

    @Override
    public void start() {
        // 这里收集数据，[当前活动的线程数]
        new Thread(() -> {
            while (this.working) {
                if (!ManagerServiceImpl.workReports.isEmpty()) {
                    WorkReport workReport = ManagerServiceImpl.workReports.poll();
                    switch (workReport.getState()) {
                        case Status.testWORKING:     // 将些数据添加到列表中，后续上传
                            this.workReports.add(workReport);
                            if (this.workReports.size() >= 4000) {
                                logger.info("Analysis data ... ");
                                this.analysisService.analysis(this.workReports);
                                this.analysisService.listPointData().forEach(pointData -> {
                                    this.influxDBService.insertAnalysisResult(pointData);
                                    System.out.println(pointData.toString());
                                });
                                workReports = new ArrayList<>();
                            }
                            break;
                        case Status.testSUSPEND:
                            // 分析最后一批数据
                            if (this.workReports.size() > 0) {
                                logger.info("Analysis data ... ");
                                this.analysisService.analysis(this.workReports);
                                this.analysisService.listPointData().forEach(pointData -> {
                                    this.influxDBService.insertAnalysisResult(pointData);
                                    System.out.println(pointData.toString());
                                });
                            }
                            break;
                        case Status.testEND:      // 测试结束，将调用停止接口
                            logger.info("End ... \n");
                            // 停止循环
                            this.working = Status.receiveNo;
                            break;
                    }
                }
            }
        }, "SAVE-DATA").start();
    }
}
