package com.lg.framework.impl.performance;

import com.lg.framework.service.DataService;
import com.lg.modle.common.Info;
import com.lg.modle.performance.AggregationReport;
import com.lg.tool.common.FileHelper;

public class DataServiceFactory {
    private Info info;

    public DataServiceFactory(Info info) {
        this.info = info;
    }

    public DataService getDataService() {
        DataService dataService = new DataServiceImpl(info);
        String fileName = "report_" + info.getTestId() + ".csv";
        FileHelper fileHelper = new FileHelper(ManagerServiceImpl.reportPath, fileName);
        if (ManagerServiceImpl.firstTask) {
            fileHelper.write(AggregationReport.getCSVHeader());
            ManagerServiceImpl.firstTask = false;
        }
        dataService.setReportObj(fileHelper);

        if (ManagerServiceImpl.saveAssertMsg) {
            fileName = "error_" + info.getTestId() + ".txt";
            FileHelper errorFile = new FileHelper(ManagerServiceImpl.reportPath, fileName);
            dataService.setErrorObj(errorFile);
        }
        return dataService;
    }
}
