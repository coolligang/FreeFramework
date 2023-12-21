package com.lg.test;

import java.util.Objects;
import java.util.concurrent.*;

public class ThreadPool extends Thread {
    private BlockingDeque<Runnable> bq = new LinkedBlockingDeque<>();
    private ExecutorService es = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS, bq);

    public ThreadPool() {
        try {
            for (int i = 0; i < 100; i++) {
                int finalI = i;
                bq.put(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("thread "+ finalI);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runTask() {
        for (int i = 0; i < 20; i++) {
            es.submit(Objects.requireNonNull(bq.poll()));
        }
    }

    public void run(){
        runTask();
        runTask();
    }


    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        threadPool.runTask();
    }
}
