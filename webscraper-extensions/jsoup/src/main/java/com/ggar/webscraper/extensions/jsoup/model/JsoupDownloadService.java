package com.ggar.webscraper.extensions.jsoup.model;

import com.ggar.webscraper.interfaces.*;
import com.ggar.webscraper.model.Interceptor;
import com.google.inject.Inject;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class JsoupDownloadService implements HttpService<Document>, Interceptable<JsoupDownloadService, Connection> {

    @Inject private DownloadRegistry<Document> pages;
    @Inject private ExecutorService executor;
    @Inject private LoggerService logger;
    private final List<Interceptor<Connection>> interceptors;

    public JsoupDownloadService() {
        this.interceptors = new LinkedList<>();
    }

    public HttpService<Document> registerInterceptors(Interceptor<Connection>... interceptors) {
        this.interceptors.clear();
        this.interceptors.addAll(Arrays.asList(interceptors));
        return this;
    }

    @SneakyThrows({TimeoutException.class, ExecutionException.class, InterruptedException.class})
    @Override
    public Document execute(URL url) {
        Document result = null;
        Future<Document> future = null;
        if (pages.containsKey(url)) {
            result = pages.remove(url);
        } else {
            future = executor.execute( new JsoupDownloadPageTask(url, interceptors) );
            pages.put(url, future.get(Integer.MAX_VALUE, TimeUnit.DAYS));
            result = pages.get(url);
        }
        return result;
    }

    @Override
    public JsoupDownloadService registerInterceptor(Function<Connection, Connection>... interceptors) {
        return null;
    }

    @Override
    public Connection intercept(Connection entity) {
        return null;
    }
}
