package com.lg.example.other;

import java.util.Random;
import java.util.concurrent.*;

public class AtomicIntegerFieldUpdaterTest {

    public void TestCallable() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "apple";
            }
        });
        try {
            System.out.println("I want result: " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public void TestTaskFuture() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        System.out.println("主线程 do something...");
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(2000);
                System.out.println("子线程 do something...");
                return 100;
            }
        });
        executorService.execute(futureTask);
        System.out.println("我不管子线程，我需要干点其他事，主线程继续do something");
        try {
            System.out.println("执行结果为" + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public void TestCompletionService() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);
        System.out.println("主线程 do something...");
        // 向CompletionService中提交10个任务
        for (int i = 0; i < 50; i++) {
            final int sequence = i;  // 记录任务号
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("running " + sequence);
                    Thread.sleep(new Random().nextInt(5000));
                    return sequence;
                }
            });
        }
        System.out.println("我不管子线程，我需要干点其他事，主线程继续do something");
        // 获取结果
        for (int i = 0; i < 50; i++) {
            try {
                System.out.println("got " + completionService.take().get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    /**
     * 这些方法[thenApply thenApplyAsync]的输入是上一个阶段计算后的结果，返回值是经过转化后结果
     */
    public void TestCompletionThenAsync() {
        String result = CompletableFuture.supplyAsync(() -> {
            return "Hello ";
        }).thenApplyAsync(v -> v + "world").join();
        System.out.println(result);
    }

    /**
     * 这些方法[thenAccept thenAcceptAsync]只是针对结果进行消费，入参是Consumer，没有返回值
     */
    public void TestCompletionFuture() {
        CompletableFuture.supplyAsync(() -> {
            return "Hello ";
        }).thenAccept(v -> {
            System.out.println("consumer: " + v);
        });
    }

    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterTest atomicIntegerFieldUpdaterTest = new AtomicIntegerFieldUpdaterTest();
//        atomicIntegerFieldUpdaterTest.TestCallable();
//        atomicIntegerFieldUpdaterTest.TestTaskFuture();
//        atomicIntegerFieldUpdaterTest.TestCompletionService();
        atomicIntegerFieldUpdaterTest.TestCompletionThenAsync();
    }
}
