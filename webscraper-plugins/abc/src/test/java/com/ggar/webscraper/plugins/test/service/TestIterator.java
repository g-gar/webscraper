package com.ggar.webscraper.plugins.test.service;

import com.ggar.webscraper.interfaces.DownloaderService;
import com.ggar.webscraper.plugins.abc.Abc;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class TestIterator implements Iterator<Document> {

    private final AtomicInteger counter;
    private final Stack<Document> items;
    private final URL url;
    private final DownloaderService<Document> service;

    @AssistedInject
    public TestIterator(DownloaderService<Document> service, @Assisted URL url) {
        this.service = service;
        this.url = url;
        this.counter = new AtomicInteger(Abc.INITIAL_INDEX);
        this.items = new Stack<>();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Document next() {
        return service.get(url);
    }
}
