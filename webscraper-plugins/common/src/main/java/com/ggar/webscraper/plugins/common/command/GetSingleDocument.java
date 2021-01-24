package com.ggar.webscraper.plugins.common.command;

import com.ggar.webscraper.interfaces.HttpService;
import com.ggar.webscraper.model.Interceptor;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.function.Function;

public class GetSingleDocument implements Command<Document> {

    private final HttpService<Document> service;
    private final URL url;
    private final Interceptor<Connection>[] interceptors;

    @AssistedInject
    public GetSingleDocument(HttpService<Document> service, @Assisted URL url, @Assisted Interceptor<Connection>... interceptors) {
        this.service = service;
        this.url = url;
        this.interceptors = interceptors;
    }

//    TODO: fix interceptors
    public Document execute() {
        return service
//            .registerInterceptors(interceptors)
            .execute(url);
    }
}
