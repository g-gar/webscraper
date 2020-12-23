package com.ggar.webscraper.postprocessing.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CsvStringToArticleConversor implements Function<String, Article> {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Logger log = Logger.getLogger(CsvStringToArticleConversor.class.getName());

    @Override
    public Article apply(String line) {
        List<String> params = new ArrayList<String>() {
            {
                String temp = "";
                boolean containsCommas = false;
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    switch (c) {
                        case '"':
                            containsCommas = containsCommas ? false : true;
                            break;
                        case ',':
                            if (!containsCommas) {
                                add(temp);
                                temp = "";
                                break;
                            }
                        default:
                            temp += c;
                    }
                }
                add(temp);
            }
        };

        Article article = null;
        try {
            article = new Article.ArticleBuilder()
                    .tags(params.size() >= 8
                            ? Arrays.asList(params.get(8).split("; ")).stream().map(String::trim).collect(Collectors.toList())
                            : null)
                    .authors(params.size() >= 9
                            ? Arrays.asList(params.get(9).split("; ")).stream().map(String::trim).collect(Collectors.toList())
                            : null)
                    .url(params.get(0).trim())
                    .publishedDate(params.get(1).trim())
                    .modifiedDate(params.get(2).trim())
                    .title(params.get(3).trim())
                    .summary(params.get(4).trim())
                    .content(params.get(5).trim())
                    .language(params.get(6).trim())
                    .section(params.get(7).trim())
                    .build();
        } catch (Exception e) {
            log.info(String.format("Error at parsing line index %s\n", counter.getAndIncrement()));
        }
        return article;
    }
}
