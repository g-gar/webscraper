package com.ggar.webscraper.services;

import com.ggar.webscraper.interfaces.BackgroundService;
import com.ggar.webscraper.interfaces.ExecutorService;
import com.ggar.webscraper.interfaces.LoggerService;
import com.google.inject.Inject;

import java.sql.Date;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorService implements BackgroundService, ExecutorService {

    @Inject private ThreadPoolExecutor executor;
    @Inject private LoggerService logger;
    private final AtomicInteger counter;

    @Inject
    public ThreadPoolExecutorService() {
        this.counter = new AtomicInteger(0);
    }

    @Override
    public void start() {
        this.logger.log(String.format("Service started [%s]", Date.from(Instant.now())));
    }

    @Override
    public void stop() {
        try {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (Exception e) {
            this.logger.log(e.toString());
        } finally {
            this.logger.log(String.format("Service stopped [%s]", Date.from(Instant.now())));
        }
    }

    public <T> Future<T> execute(Callable<T> task) {
        long start, end;
        Future<T> result = null;
        int id = this.counter.getAndIncrement();
        logger.log(String.format("[id=%s] Received task %s", id, task));
        start = System.currentTimeMillis();
        result = executor.submit(task);
        end = System.currentTimeMillis();
        logger.log(String.format("[id=%s][ms=%s] Finished task %s", id, end - start, task));
        return result;
    }
}
