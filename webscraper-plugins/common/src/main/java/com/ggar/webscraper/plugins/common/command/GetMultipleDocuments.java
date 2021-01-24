package com.ggar.webscraper.plugins.common.command;

import com.ggar.webscraper.interfaces.HttpService;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.ggar.webscraper.plugins.common.interfaces.CommandFactory;
import com.ggar.webscraper.plugins.common.utils.UrlUtils;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetMultipleDocuments implements Command<Collection<Document>> {

    private final HttpService<Document> httpService;
    private final CommandFactory commandFactory;
    private final Iterator<Document> iterator;
    private final Function<Document, Elements>[] filters;

    @AssistedInject
    public GetMultipleDocuments(HttpService<Document> httpService, CommandFactory commandFactory, @Assisted Iterator<Document> iterator, @Assisted Function<Document, Elements>[] filters) {
        this.httpService = httpService;
        this.commandFactory = commandFactory;
        this.iterator = iterator;
        this.filters = filters;
    }

    @Override
    public Collection<Document> execute() {
        return new LinkedHashSet<Document>() {{
            while (iterator.hasNext()) {
                add(iterator.next());
            }
        }}.stream()
            .map(listingPage -> commandFactory.filterNodes(listingPage, filters).execute())
            .flatMap(Collection::stream)
            .distinct()
            .map(element -> UrlUtils.create(element.attr("abs:href")))
            .map(url -> httpService.execute(url))
            .collect(Collectors.toList());
    }
}
