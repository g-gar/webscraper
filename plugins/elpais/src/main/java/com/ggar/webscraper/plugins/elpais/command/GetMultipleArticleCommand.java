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

import com.ggar.webscraper.core.AbstractEntity;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.elpais.ElPais;
import com.ggar.webscraper.plugins.elpais.config.Operations;
import com.ggar.webscraper.plugins.elpais.util.UrlIterator;

public class GetMultipleArticleCommand implements Command<UrlIterator, List<AbstractEntity>> {

    private final Logger log = Logger.getLogger(ElPais.class.getName());

    @Override
    public List<AbstractEntity> execute(UrlIterator iterator) {
        List<AbstractEntity> articles = Collections.synchronizedList(new ArrayList<AbstractEntity>());
        Function<Document, List<AbstractEntity>> fn = document -> document.selectFirst("div#fusion-app").select("article").stream()
                .map(e -> e.selectFirst("h2 > a").attr("abs:href"))
                .filter(url -> url.trim().length() > 0)
                .map(url -> new GetSingleArticleCommand().execute(new PluginParams<Operations>(Operations.SINGLE_ARTICLE, url) {
                }))
                .collect(Collectors.toList());

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        while (iterator.hasNext()) {
            executor.submit(() -> {
                for (AbstractEntity article : fn.apply(iterator.next())) {
                    articles.add(article);
                }
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
