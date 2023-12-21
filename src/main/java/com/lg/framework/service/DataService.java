package com.lg.framework.service;

import com.lg.tool.common.FileHelper;

public interface DataService {
    /**
     * 性能测试，数据接收器，请求数据会上报给COOK平台
     */
    void startNow();

    /**
     * 稳定性测试，数据不上报，直接打印日志
     */
    void start();

    void setReportObj(FileHelper fileHelper);

    void setErrorObj(FileHelper fileHelper);
}
