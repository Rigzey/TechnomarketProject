package com.example.technomarketproject;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

@Component
public class Logger {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Logger.class);

    public void info(String message) {
        LOGGER.info(message);
    }

    public void error(String message) {
        LOGGER.error(message);
    }

    public void fatal(String message) {
        LOGGER.fatal(message);
    }
}
