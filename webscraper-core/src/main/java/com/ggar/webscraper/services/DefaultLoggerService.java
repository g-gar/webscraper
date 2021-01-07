package com.ggar.webscraper.services;

import com.ggar.webscraper.interfaces.LoggerService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultLoggerService implements LoggerService {

    private final Logger logger = Logger.getGlobal();

    public DefaultLoggerService() {
        logger.setLevel(Level.FINE);
    }

    @Override
    public void log(String message) {
        logger.info(String.format("[%s][%s]\nMESSAGE: %s\n", Thread.currentThread().getName(), getCallerCallerClassName(), message));
    }

    public static String getCallerCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(DefaultLoggerService.class.getName())&& ste.getClassName().indexOf("java.lang.Thread")!=0) {
                if (callerClassName==null) {
                    callerClassName = ste.getClassName();
                } else if (!callerClassName.equals(ste.getClassName())) {
                    return ste.getClassName();
                }
            }
        }
        return null;
    }
}
