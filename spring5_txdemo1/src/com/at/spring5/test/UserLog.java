package com.at.spring5.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLog {
    private static final Logger logger = LoggerFactory.getLogger(UserLog.class);

    public static void main(String[] args) {
        logger.info("hello log4j2");
        logger.warn("hello log4j2");
    }
}
