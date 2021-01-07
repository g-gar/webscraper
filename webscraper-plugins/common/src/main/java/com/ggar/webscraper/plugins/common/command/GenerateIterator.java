package com.ggar.webscraper.plugins.common.command;

import com.ggar.webscraper.interfaces.DownloaderService;
import com.ggar.webscraper.interfaces.UrlTemplate;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.ggar.webscraper.plugins.common.interfaces.InternalStateSupervisor;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class GenerateIterator implements Command<Iterator<Document>>, Iterator<Document>, InternalStateSupervisor<GenerateIterator> {

    @Getter private final AtomicInteger counter;
    @Getter private final Queue<Document> items;
    private final UrlTemplate template;
    @Getter private final String[] replacements;
    private final DownloaderService<Document> service;
    private final List<Function<GenerateIterator, Boolean>> stateCheckerFunctions;

    @AssistedInject
    public GenerateIterator(DownloaderService<Document> service, @Assisted UrlTemplate template, @Assisted Integer offset, @Assisted String... replacements) {
        this.service = service;
        this.template = template;
        this.replacements = replacements;
        this.counter = new AtomicInteger(offset);
        this.items = new LinkedBlockingDeque<>();
        this.stateCheckerFunctions = new LinkedList<>();
    }

    @Override
    public Iterator<Document> execute() {
        return this;
    }

    @Override @SneakyThrows(MalformedURLException.class)
    public boolean hasNext() {
        if (this.checkCurrentState()) {
            URL url = new URL(String.format(this.template.get(replacements), this.counter.getAndIncrement()));
            this.items.add(this.service.get(url));
        }
        return !this.items.isEmpty();
    }

    @SneakyThrows(NoSuchElementException.class)
    @Override
    public Document next() {
        return items.remove();
    }

    //TODO: apply a deep copy of this to the state checker function for security reasons
    @Override
    public GenerateIterator setStateCheckerFunctions(Function<GenerateIterator, Boolean>... stateCheckerFunctions) {
        this.stateCheckerFunctions.clear();
        for (Function<GenerateIterator, Boolean> stateCheckerFunction : stateCheckerFunctions) {
            this.stateCheckerFunctions.add(stateCheckerFunction);
        }
        return this;
    }

    //TODO: apply a deep copy of this to the state checker function for security reasons
    @Override
    public boolean checkCurrentState() {
        boolean result = true;
        int index = 0;
        while (index < this.stateCheckerFunctions.size() && result) {
            result = result && this.stateCheckerFunctions.get(index++).apply(this);
        }
        return result;
    }
}
