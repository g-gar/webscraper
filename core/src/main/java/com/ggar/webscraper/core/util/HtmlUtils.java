package com.ggar.webscraper.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// TODO: change to org.Jsoup.Document to avoid extra loading and parsing urls
public class HtmlUtils {

	public static Elements extractMetaTags(String url) {
		return select(url, "meta");
	}

	public static Elements select(String url, String selection) {
		Elements elements = null;
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
			elements = document.select(selection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elements;
	}
	
	public static Elements extractMetaTags(Document document) {
		return select(document, "meta");
	}
	
	public static Elements select(Document document, String selection) {
		Elements elements = null;
		try {
			elements = document.select(selection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elements;
	}
}
