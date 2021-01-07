package com.ggar.webscraper;

import com.ggar.webscraper.interfaces.ExecutorService;
import com.ggar.webscraper.interfaces.LoggerService;
import com.ggar.webscraper.services.DefaultLoggerService;
import com.ggar.webscraper.services.ThreadPoolExecutorService;
import com.google.inject.*;
import com.google.inject.Module;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BaseModule extends AbstractModule {

    protected final List<Module> modules;

    public BaseModule(Module... modules) {
        this.modules = Arrays.asList(modules);
    }

    @Override
    protected void configure() {
        bind(LoggerService.class).to(DefaultLoggerService.class);
        bind(ThreadPoolExecutor.class).toInstance((ThreadPoolExecutor) Executors.newCachedThreadPool());
        bind(ExecutorService.class).to(ThreadPoolExecutorService.class);

        for (Module module : this.modules) {
            install(module);
        }
    }

    public Injector getInjector() {
        return Guice.createInjector(this);
    }
}
