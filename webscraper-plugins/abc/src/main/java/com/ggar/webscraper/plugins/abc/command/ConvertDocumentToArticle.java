package com.ggar.webscraper.plugins.abc.command;

import com.ggar.webscraper.plugins.abc.model.Article;
import com.ggar.webscraper.plugins.common.interfaces.Command;
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
        Article.ArticleBuilder builder = Article.builder();
        for (Element meta : document.select("meta")) {
            if (meta.attributes().get("property") != "") {
                switch (meta.attributes().get("property")) {
                    case "og:title":
                        builder.title(meta.attr("content"));
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
                    case "description":
                        builder.summary(meta.attr("content"));
                        break;
                    case "article:tag":
                        tags.add(meta.attr("content"));
                        break;
                }
            }
        }
        builder.url(document.location());
        builder.tags(tags);
        builder.authors(document.select("footer.autores > span.autor > a.nombre").stream().map(Element::text).collect(Collectors.toList()));
        builder.content(document.select("span.cuerpo-texto>h3, span.cuerpo-texto>aside.despiece, span.cuerpo-texto>p").text());
        return builder.build();
    }
}
