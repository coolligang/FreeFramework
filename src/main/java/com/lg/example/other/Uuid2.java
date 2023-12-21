package com.lg.example.other;

import com.lg.tool.common.OtherTool;

import java.time.Instant;

/**
 * ProtocolFreeFramework
 * Uuid2
 *
 * @author: ligang30
 * @date: 2022/10/21
 */

public class Uuid2 implements Runnable {
    @Override
    public void run() {
        System.out.println(OtherTool.getUUID());
    }
}
