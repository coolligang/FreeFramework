package com.lg.example.function;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class DemoNGTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testAdd_Positive() {
        assertEquals("加法运算出错", 6, 3 + 3);
    }

    @Ignore()
    @Test(expectedExceptions = ArithmeticException.class)
    public void testAdd_Negetive() {
        assertEquals("请检查运算结果是否溢出及测试平台支持的基本数据类型取值范围", 220000000, 1100000000 + 1100000000);
    }

    @Test(description = "这里是TEST描述...")
    public void testMinus_Positive() {
        assertEquals("减法运算出错", 0, 3 - 3);
    }

    @Test(timeOut = 200)
    public void testTimeout() throws InterruptedException {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            Thread.sleep(1000);
            sum = i + sum;
        }
    }
}
