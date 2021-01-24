package com.ggar.webscraper.plugins.elpais.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindArticleNodesFromListing implements Function<Document, Elements> {
    @Override
    public Elements apply(Document document) {
        return new Elements() {{
            List<Element> elements = document.selectFirst("div#fusion-app").select("article").stream()
                    .map(e -> e.selectFirst("h2 > a"))
                    .collect(Collectors.toList());
            addAll(elements);
        }};
    }
}
