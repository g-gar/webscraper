package com.ggar.webscraper.plugins.abc.command;

import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ggar.webscraper.core.AbstractArticle;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.core.util.HtmlUtils;
import com.ggar.webscraper.plugins.abc.Abc;
import com.ggar.webscraper.plugins.abc.config.Operations;
import com.ggar.webscraper.plugins.abc.model.ArticleBuilder;

public class GetSingleArticleCommand implements Command<PluginParams<Operations>, AbstractArticle> {

	private final Logger log = Logger.getLogger(Abc.class.getName());
	
	@Override
	public AbstractArticle execute(PluginParams<Operations> param) {
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
				case "og:title":
					builder.setTitle(meta.attr("content"));
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
				case "description":
					builder.setSummary(meta.attr("content"));
					break;
				case "article:tag":
					builder.setTag(meta.attr("content"));
					break;
				}
			}
		}
		
//		Find authors
		HtmlUtils.select(document, "footer.autores > span.autor > a.nombre").forEach(e -> builder.setAuthor(e.text()));
		
//		Find content
		builder.setContent(HtmlUtils.select(document, "span.cuerpo-texto>h3, span.cuerpo-texto>aside.despiece, span.cuerpo-texto>p").text());
		
		return builder.build();
	}

}
