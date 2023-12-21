package com.lg.example.other;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ProtocolFreeFramework
 * Pool
 *
 * @author: ligang30
 * @date: 2022/10/21
 */

public class Pool {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            pool.submit(new Uuid2());
        }
        pool.shutdown();

        System.out.println();
    }
}
