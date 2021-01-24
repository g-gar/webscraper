package com.ggar.webscraper.plugins.elpais.command;

import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.ggar.webscraper.plugins.elpais.model.Article;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertDocumentToArticle implements Command<Article> {

    private final Document document;

    @AssistedInject
    public ConvertDocumentToArticle(@Assisted Document document) {
        this.document = document;
    }

    @Override
    public Article execute() {
        List<String> tags = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        Article.ArticleBuilder builder = Article.builder();
        for (Element meta : document.select("meta")) {
            if (meta.attributes().get("property") != "") {
                switch (meta.attributes().get("property")) {
                    case "article:tag":
                        tags.add(meta.attr("content"));
                        break;
                    case "og:article:author":
                        authors.add(meta.attr("content"));
                        break;
                    case "og:title":
                        builder.title(meta.attr("content"));
                        break;
                    case "og:description":
                        builder.summary(meta.attr("content"));
                        break;
                    case "article:section":
                        builder.section(meta.attr("content"));
                        break;
                    case "article:published_time":
                        builder.publishedDate(meta.attr("content"));
                        break;
                    case "article:modified_time":
                        builder.modifiedDate(meta.attr("content"));
                        break;
                }
            } else if (meta.attributes().get("name") != "") {
                switch (meta.attributes().get("name")) {
                    case "lang":
                        builder.language(meta.attr("content"));
                        break;
                }
            }
        }
        builder.url(document.location());
        builder.tags(tags);
        builder.authors(authors);
        builder.content(document.select("div#fusion-app div.article_body").text());
        return builder.build();
    }
}
