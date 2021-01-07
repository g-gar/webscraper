package com.ggar.webscraper.plugins.veinteminutos.model;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindArticleNodesFromListing implements Function<Document, Elements> {
    @Override
    public Elements apply(Document document) {
        return new Elements() {{
            List<Elements> list = document.select("section.content > article.media")
                    .stream()
                    .map(media -> media.select("figure > a"))
                    .collect(Collectors.toList());
            for (Elements elements : list) {
                addAll(elements);
            }
        }};
    }
}
