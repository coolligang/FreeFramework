package com.lg.example.performance;

import com.lg.framework.impl.performance.TaskObject;
import com.lg.modle.performance.WorkReport;

import java.util.Random;

public class DemoTask extends TaskObject {
    /**
     * 单次事务的前期准备工作
     */
    @Override
    public void setUp() {

    }

    /**
     * 单次事务的收尾工作
     */
    @Override
    public void tearDown() {

    }

    /**
     * 实现此方法，将测试过程写在这里
     *
     * @param workReport 存储 断言结果，异常信息，更准确的开始时间、结束时间，
     */
    @Override
    public void work(WorkReport workReport) throws AssertionError {
        try {
            Random random = new Random();
            int a = random.nextInt(10);
            Thread.sleep(a * 100);
            System.out.println("OK~");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
