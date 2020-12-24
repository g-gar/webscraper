package com.ggar.webscraper.plugins.elpais.command;

import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ggar.webscraper.core.AbstractEntity;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.core.util.HtmlUtils;
import com.ggar.webscraper.plugins.elpais.ElPais;
import com.ggar.webscraper.plugins.elpais.config.Operations;
import com.ggar.webscraper.plugins.elpais.model.ArticleBuilder;

public class GetSingleArticleCommand implements Command<PluginParams<Operations>, AbstractEntity> {

    private final Logger log = Logger.getLogger(ElPais.class.getName());

    @Override
    public AbstractEntity execute(PluginParams<Operations> param) {
        log.info(String.format("[%s] Extracting single article from %s\n", Thread.currentThread().getName(), param.getValue()));
        String url = param.getValue();
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Elements metas = HtmlUtils.extractMetaTags(document);
		ArticleBuilder builder = new ArticleBuilder();
		
		builder.setUrl(url);
		
		for (Element meta : metas) {
			if (meta.attributes().get("property") != "") {
				switch (meta.attributes().get("property")) {
				case "article:tag":
					builder.setTag(meta.attr("content"));
					break;
				case "og:article:author":
					builder.setAuthor(meta.attr("content"));
					break;
				case "og:title":
					builder.setTitle(meta.attr("content"));
					break;
				case "og:description":
					builder.setSummary(meta.attr("content"));
					break;
				case "article:section":
					builder.setSection(meta.attr("content"));
					break;
				case "article:published_time":
					builder.setPublishedDate(meta.attr("content"));
					break;
				case "article:modified_time":
					builder.setModifiedDate(meta.attr("content"));
					break;
				}
			} else if (meta.attributes().get("name") != "") {
				switch (meta.attributes().get("name")) {
				case "lang":
					builder.setLanguage(meta.attr("content"));
					break;
				}
			}
		}
		
//		Find content
		builder.setContent(HtmlUtils.select(document, "div#fusion-app div.article_body").text());
		
		return builder.build();
	}

}
