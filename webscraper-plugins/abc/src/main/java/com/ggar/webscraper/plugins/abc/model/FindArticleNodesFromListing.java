package com.ggar.webscraper.plugins.abc.model;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO: change to flatmap
public class FindArticleNodesFromListing implements Function<Document, Elements> {
    @Override
    public Elements apply(Document document) {
        return new Elements() {{
            List<Elements> list = document.select("ul#results-content > li").stream()
                    .map(e -> e.select("h2 > a.titulo"))
                    .collect(Collectors.toList());
            for (Elements elements : list) {
                addAll(elements);
            }
        }};
    }
}
