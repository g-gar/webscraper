package com.ggar.webscraper.plugins.common.command;

import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.function.Function;

public class FilterNodesFromDocument implements Command<Elements> {

    private final Document document;
    private final Function<Document, Elements>[] functions;

    @AssistedInject
    public FilterNodesFromDocument(@Assisted Document document, @Assisted Function<Document, Elements>... functions) {
        this.document = document;
        this.functions = functions;
    }

    @Override
    public Elements execute() {
        return new Elements() {{
            for (Function<Document, Elements> fn : functions) {
                addAll(fn.apply(document));
            }
        }};
    }

}
