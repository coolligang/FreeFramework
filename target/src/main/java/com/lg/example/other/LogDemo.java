package com.lg.example.other;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogDemo {
    private static Logger logger = LogManager.getLogger(LogDemo.class);

    public static void main(String[] args) {

        System.out.println("This is println message.");

        logger.trace("This is trace message");
        // 记录debug级别的信息
        logger.debug("This is debug message.");

        logger.warn("This is warn message");
        // 记录info级别的信息
        logger.info("This is info message.");

        // 记录error级别的信息
        logger.error("This is error message.");

    }
}
