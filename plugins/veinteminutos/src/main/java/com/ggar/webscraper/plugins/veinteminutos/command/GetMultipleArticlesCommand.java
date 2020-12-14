package com.ggar.webscraper.plugins.veinteminutos.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;

import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.veinteminutos.VeinteMinutos;
import com.ggar.webscraper.plugins.veinteminutos.config.Operations;
import com.ggar.webscraper.plugins.veinteminutos.model.Article;
import com.ggar.webscraper.plugins.veinteminutos.util.UrlIterator;

public class GetMultipleArticlesCommand implements Command<UrlIterator, List<Article>> {

	private final Logger log = Logger.getLogger(VeinteMinutos.class.getName());
	
//	TODO: improve article collection (there are multiple types)
	@Override
	public List<Article> execute(UrlIterator iterator) {
		List<Article> articles = Collections.synchronizedList(new ArrayList<Article>());
		Function<Document, List<Article>> fn = document -> document
                .select("section.content > article.media").stream()
                .map(media -> media.select("figure > a").attr("href"))
                .filter(url -> !url.equals(null) && url.trim().length() > 0)
                .map(url -> new GetSingleArticleCommand().execute(new PluginParams<Operations>(Operations.SINGLE_ARTICLE, url) {
                }))
				.collect(Collectors.toList());

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		while (iterator.hasNext()) {
			executor.submit(() -> {
				for (Article article : fn.apply(iterator.next())) {
					articles.add(article);
				}
				log.info(String.format("Current articles No: %s\n", articles.size()));
			});
		}
		
		try {
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return articles;
	}

}
