package com.ggar.webscraper.plugins.veinteminutos.command;

import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.ggar.webscraper.plugins.veinteminutos.model.Article;
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
//                    case "content:section":
//                        builder.setContentSection(meta.attr("content"));
//                        break;
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
                    case "language":
                        builder.language(meta.attr("content"));
                        break;
                }
            }
        }
        authors.addAll(document.select("span.article-author > a > strong, span.article-author > strong").stream().map(Element::text).collect(Collectors.toList()));

        builder.url(document.location());
        builder.tags(tags);
        builder.authors(authors);
        builder.content(document.select("div.article-text").text());
        builder.summary(document.select("div.article-intro li").text());
        return builder.build();
    }
}
