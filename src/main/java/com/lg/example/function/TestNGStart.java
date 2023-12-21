package com.lg.example.function;

import org.testng.TestNG;

public class TestNGStart {

    public static void main(String[] args) {

        Class[] runTest = new Class[1];
        runTest[0] = DemoNGTest.class;
        TestNG testNG = new TestNG();
        testNG.setTestClasses(runTest);
        testNG.run();
    }
}
