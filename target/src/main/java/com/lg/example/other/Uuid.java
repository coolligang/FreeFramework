package com.lg.example.other;

/**
 * ProtocolFreeFramework
 * Uuid
 *
 * @author: ligang30
 * @date: 2022/10/21
 */

public class Uuid implements Runnable {

    @Override
    public void run() {
        String id = "";
        synchronized (id) {
            id = java.util.UUID.randomUUID().toString();
        }
        System.out.println(id);
    }
}
