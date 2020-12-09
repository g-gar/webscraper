package com.ggar.webscraper.plugins.veinteminutos.command;

import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.core.util.HtmlUtils;
import com.ggar.webscraper.plugins.veinteminutos.VeinteMinutos;
import com.ggar.webscraper.plugins.veinteminutos.config.Operations;
import com.ggar.webscraper.plugins.veinteminutos.model.Article;
import com.ggar.webscraper.plugins.veinteminutos.model.ArticleBuilder;

public class GetSingleArticleCommand implements Command<PluginParams<Operations>, Article> {

	private final Logger log = Logger.getLogger(VeinteMinutos.class.getName());
	
	@Override
	public Article execute(PluginParams<Operations> param) {
		log.info(String.format("Extracting single article from %s\n", param.getValue()));
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
				case "content:section":
					builder.setContentSection(meta.attr("content"));
					break;
				case "article:section":
					builder.setArticleSection(meta.attr("content"));
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
				case "language":
					builder.setLanguage(meta.attr("content"));
					break;
				}
			}
		}

//		Find Summary
		builder.setSummary(HtmlUtils.select(document, "div.article-intro li").text());

//		Find content
		builder.setContent(HtmlUtils.select(document, "div.article-text").text());

//		Find author/s
		HtmlUtils.select(document, "span.article-author > a > strong, span.article-author > strong").stream()
				.map(e -> e.text()).forEach(e -> builder.setAuthor(e));

		return builder.build();
	}

}
