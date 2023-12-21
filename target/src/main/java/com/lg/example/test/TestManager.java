package com.lg.example.test;

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
        Info info = new Info("test");
        TestManager testManager = new TestManager(info);
        testManager.setRunTime(5*60);
        testManager.addTaskObject(new Task("test", new TestTask(),200,200,50));
        testManager.go();
    }
}
