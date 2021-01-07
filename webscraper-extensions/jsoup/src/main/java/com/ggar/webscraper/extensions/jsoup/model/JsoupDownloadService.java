package com.ggar.webscraper.extensions.jsoup.model;

import com.ggar.webscraper.interfaces.DownloadRegistry;
import com.ggar.webscraper.interfaces.DownloaderService;
import com.ggar.webscraper.interfaces.ExecutorService;
import com.ggar.webscraper.interfaces.LoggerService;
import com.google.inject.Inject;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class JsoupDownloadService implements DownloaderService<Document> {

    @Inject private DownloadRegistry<Document> pages;
    @Inject private ExecutorService executor;
    @Inject private LoggerService logger;
    @Inject private Function<Connection, Connection>[] interceptors;

    public JsoupDownloadService() {}

    @SneakyThrows({TimeoutException.class, ExecutionException.class, InterruptedException.class})
    @Override
    public Document get(URL url) {
        Document result = null;
        Future<Document> future = null;
        if (pages.containsKey(url)) {
            result = pages.get(url);
        } else {
            future = executor.execute(new JsoupDownloadPageTask(url).registerConnectionInterceptors(interceptors));
            pages.put(url, future.get(Integer.MAX_VALUE, TimeUnit.DAYS));
            result = pages.get(url);
        }
        return result;
    }

    @Override
    public Collection<Document> get(Collection<URL> urls)  {
        Set<Document> result = new HashSet<>();
        for (URL url : urls) {
            result.add(JsoupDownloadService.this.get(url));
        }
        return result;
    }

}
