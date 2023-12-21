package com.lg.example.function;

import com.lg.framework.impl.function.FunctionManager;
import com.lg.modle.common.Info;
import com.lg.modle.common.Status;

import java.util.ArrayList;
import java.util.List;

public class TestNGDemo {

    public static void main(String[] args) {
        Info info = new Info( "功能测试");
        List<DemoTest> classes = new ArrayList<>();
        classes.add(new DemoTest());
        classes.add(new DemoTest1());
        FunctionManager<DemoTest> functionManager = new FunctionManager<>(info, classes);
        functionManager.go();
    }
}
