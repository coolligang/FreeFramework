package com.lg.framework.service;

import com.lg.modle.performance.Task;
import com.lg.tool.prometheus.PrometheusService;

import java.util.List;

public interface ManagerService {
    // 设置最小线程数，默认为2^0
    void setMinPower(int power);

    void setMin(int min);

    // 设置最大线程数，默认为2^10
    void setMaxPower(int power);

    void setMax(int max);

    void setStep(int step);

    // 批量添加任务
    void setTasks(List<Task> tasks);

    // 添加任务
    void addTaskObject(Task task);

    // 获取任务
    List<Task> getTasks();

    // 设置运行时间
    void setRunTime(int runTime);

    // 开始测试
    void go();

    void setReportPath(String reportPath);

    void saveAssertMsg(boolean is);

    void setPrometheusService(PrometheusService prometheusService);

    PrometheusService getPrometheusService();
}
