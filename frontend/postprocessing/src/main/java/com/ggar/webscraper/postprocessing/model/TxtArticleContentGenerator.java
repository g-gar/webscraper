package com.ggar.webscraper.postprocessing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TxtArticleContentGenerator implements Function<Article, String> {
    @Override
    public String apply(Article article) {
        List<String> fields = new ArrayList<String>() {
            {
                add(article.getUrl());
                add(article.getPublishedDate());
                add(article.getModifiedDate());
                add(article.getTitle());
                add(article.getSummary());
                add(article.getContent());
                add(article.getLanguage());
                add(article.getSection());
                add(article.getTags().stream().collect(Collectors.joining(", ")));
                add(article.getAuthors().stream().collect(Collectors.joining(", ")));
            }
        };
        return fields.stream().collect(Collectors.joining("\n#*#\n"));
    }
}
