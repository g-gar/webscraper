package com.ggar.webscraper.plugins.veinteminutos.util;

import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.ggar.webscraper.core.PluginUrlIterator;
import com.ggar.webscraper.plugins.veinteminutos.VeinteMinutos;

public class UrlIterator implements PluginUrlIterator<Document> {

    public static final Integer INITIAL_INDEX = 1;
    public static final Boolean STOPPED = false;
    public static final Boolean ACTIVE = true;
    private Boolean state;

    private final Function<Integer, URL> fn;
    private final AtomicInteger index;
    private Document document;

//	TODO: Get a proxy list and randomly pick one for each request. Also inject a variable user-agent in each request.	

    public UrlIterator(Function<Integer, URL> fn) {
        this.fn = fn;
        index = new AtomicInteger(UrlIterator.INITIAL_INDEX);
        this.state = UrlIterator.ACTIVE;
    }

    @Override
    synchronized public boolean hasNext() {
        Boolean result = false;
        this.document = null;
        URL url = null;
        try {
            url = this.fn.apply(this.index.get());
            this.document = Jsoup.connect(url.toString())
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            result = document.select("section.content > article.media")
                    .stream()
                    .map(media -> media.select("figure > a").attr("href"))
                    .filter(e -> !e.equals(null) && e.trim().length() > 0)
                    .collect(Collectors.toList()).size() > 0;
            this.index.set(this.index.get() + 1);
        } catch (Exception e) {
            Logger.getLogger(VeinteMinutos.class.getName()).info(String.format("Error loading url [%s]\n", url));
            result = false;
            this.state = UrlIterator.STOPPED;
        }

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000 + 1));
        } catch (Exception e) {

        }

        return this.state && result;
    }

	@Override
	synchronized public Document next() {
		return this.document;
	}
	
	synchronized public Boolean getState() {
		return this.state;
	}
	
	synchronized public Integer getCurrentIteration() {
		return index.get() - UrlIterator.INITIAL_INDEX;
	}

}
