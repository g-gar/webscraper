package com.ggar.webscraper.plugins.elpais.command;

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

import com.ggar.webscraper.core.AbstractArticle;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.elpais.ElPais;
import com.ggar.webscraper.plugins.elpais.config.Operations;
import com.ggar.webscraper.plugins.elpais.util.UrlIterator;

public class GetMultipleArticleCommand implements Command<UrlIterator, List<AbstractArticle>> {
	
	private final Logger log = Logger.getLogger(ElPais.class.getName());

	@Override
	public List<AbstractArticle> execute(UrlIterator iterator) {
		List<AbstractArticle> articles = Collections.synchronizedList(new ArrayList<AbstractArticle>());
		Function<Document, List<AbstractArticle>> fn = document -> document.selectFirst("div#fusion-app").select("article").stream()
				.map(e -> e.selectFirst("h2 > a").attr("abs:href"))
				.filter(url -> url.trim().length() > 0)
				.map(url -> new GetSingleArticleCommand().execute(new PluginParams<Operations>(Operations.SINGLE_ARTICLE, url) {}))
				.collect(Collectors.toList());

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		while (iterator.hasNext()) {
			executor.submit(() -> {
				for (AbstractArticle article : fn.apply(iterator.next())) {
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
