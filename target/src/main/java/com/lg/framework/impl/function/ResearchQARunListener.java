package com.lg.framework.impl.function;

import com.lg.modle.common.Info;
import com.lg.modle.function.FuncData;
import com.lg.modle.function.FuncReport;
import org.testng.*;

import java.time.Instant;


public class ResearchQARunListener implements IInvokedMethodListener, ISuiteListener {
    private int reportId;
    public static Info info;
    private static int success = 0;
    private static int failure = 0;
    private Instant start;

    public ResearchQARunListener() {

    }

    /**
     * 每个Test执行前
     *
     * @param iInvokedMethod
     * @param iTestResult
     */
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        this.start = Instant.now();
    }

    /**
     * 每个用例执行后
     *
     * @param iInvokedMethod
     * @param iTestResult
     */
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        FuncData report = new FuncData();
        report.setStartTime(this.start);
        report.setEndTime(Instant.now());
        if (iInvokedMethod.isTestMethod() && (iTestResult.getStatus() == 1 || iTestResult.getStatus() == 2)) {
            String caseName = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(ResearchQADescription.class).name();
            int colorNum = iTestResult.getStatus() == 1 ? 34 : 31;
            System.out.printf("\033[%d;2m[%s - %s]\n\033[0m", colorNum, caseName, iInvokedMethod.getTestMethod().getMethodName());
            report.setCaseName(caseName);
            report.setCaseStep(iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(ResearchQADescription.class).step());
            report.setAssertResult(iTestResult.isSuccess());
            if (!iTestResult.isSuccess()) {
                System.out.printf("\033[%d;2m%s\n\033[0m", 33, iTestResult.getThrowable().toString());
                report.setErrMsg(iTestResult.getThrowable().toString());
                failure += 1;
            } else {
                success += 1;
            }
            // 将 report 上传
            System.out.println(report);
        }
    }

    /**
     * 套件执行前
     *
     * @param iSuite
     */
    public void onStart(ISuite iSuite) {
        if (this.reportId <= 0) {
            System.err.println("========================= reportId error! =======================");
        }
    }

    /**
     * 套件执行后
     *
     * @param iSuite
     */
    public void onFinish(ISuite iSuite) {
        FuncReport funcReport = new FuncReport(success, failure);
        funcReport.setId(this.reportId);
        funcReport.setFinish_time(Instant.now());
    }
}
