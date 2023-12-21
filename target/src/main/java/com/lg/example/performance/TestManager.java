package com.lg.example.performance;

import com.lg.framework.impl.performance.ManagerServiceImpl;
import com.lg.modle.common.Info;
import com.lg.modle.performance.Task;
import com.lg.tool.httpClient.HttpHelper;


import java.util.ArrayList;

import java.util.List;

public class TestManager extends ManagerServiceImpl {

    public static List<HttpHelper> httpHelpers = new ArrayList<>();

    public TestManager(Info info) {
        super(info);
    }

    @Override
    public void classBefore() {
        for (int i = 0; i < 100; i++) {
            httpHelpers.add(new HttpHelper());
        }
    }

    @Override
    public void classEnd() {

    }

    public static void main(String[] args) {
        Info info = new Info("Demo测试");
        TestManager testManager = new TestManager(info);
        testManager.setPrometheusService(prometheusService);
        testManager.setRunTime(10);
        testManager.setMinPower(6);
        testManager.setMaxPower(6);
        testManager.addTaskObject(new Task("test", new DemoTask()));
        testManager.go();
    }
}
