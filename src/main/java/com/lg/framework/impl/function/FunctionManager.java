package com.lg.framework.impl.function;

import com.lg.modle.common.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.TestNG;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FunctionManager<T> {
    private static Logger logger = LogManager.getLogger(FunctionManager.class);
    private List<T> objects;
    public static Info publicInfo;

    public FunctionManager(Info info) {
        publicInfo = info;
        ResearchQARunListener.info = info;
    }

    public FunctionManager(Info info, List<T> objects) {
        publicInfo = info;
        ResearchQARunListener.info = info;
        this.objects = objects;
    }

    public List<T> getClasses() {
        return objects;
    }

    public void setClasses(List<T> classes) {
        this.objects = classes;
    }

    /**
     * 开始运行功能测试
     */
    public void go() {
        List<Class> result = getTests(this.objects);
        if (result.size() > 0) {
            Class[] runTest = new Class[result.size()];
            TestNG testNG = new TestNG();
            testNG.setTestClasses(result.toArray(runTest));
            testNG.run();
        } else {
            logger.error("No cases ~");
        }
    }

    private List<Class> getTests(List<T> baseTests) {
        List<Class> results = new ArrayList<>();
        for (T test : baseTests) {
            results.add(test.getClass());
        }
        return results;
    }
}
