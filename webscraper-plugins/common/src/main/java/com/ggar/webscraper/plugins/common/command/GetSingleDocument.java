package com.ggar.webscraper.plugins.common.command;

import com.ggar.webscraper.interfaces.DownloaderService;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jsoup.nodes.Document;

import java.net.URL;

public class GetSingleDocument implements Command<Document> {

    private final DownloaderService<Document> service;
    private final URL url;

    @AssistedInject
    public GetSingleDocument(DownloaderService<Document> service, @Assisted URL url) {
        this.service = service;
        this.url = url;
    }

    public Document execute() {
        return service.get(url);
    }
}
