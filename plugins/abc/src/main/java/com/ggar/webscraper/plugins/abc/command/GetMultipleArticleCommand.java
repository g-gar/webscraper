package com.ggar.webscraper.plugins.abc.command;

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
import com.ggar.webscraper.plugins.abc.Abc;
import com.ggar.webscraper.plugins.abc.config.Operations;
import com.ggar.webscraper.plugins.abc.util.UrlIterator;

public class GetMultipleArticleCommand implements Command<UrlIterator, List<AbstractEntity>> {

    private final Logger log = Logger.getLogger(Abc.class.getName());

    @Override
    public List<AbstractEntity> execute(UrlIterator iterator) {
        List<AbstractEntity> articles = Collections.synchronizedList(new ArrayList<AbstractEntity>());
        Function<Document, List<AbstractEntity>> fn = document -> document.select("ul#results-content > li").stream()
                .map(e -> e.select("h2 > a.titulo").attr("href"))
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
