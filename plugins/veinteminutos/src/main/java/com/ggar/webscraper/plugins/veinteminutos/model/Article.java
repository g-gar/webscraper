package com.ggar.webscraper.plugins.veinteminutos.model;

import java.util.List;

import com.ggar.webscraper.core.AbstractArticle;

public class Article extends AbstractArticle {

	public Article(String url, String publishedDate, String modifiedDate, String title, String summary,
			String content, String language, String section, List<String> tags, List<String> authors) {
		super(url, publishedDate, modifiedDate, title, summary, content, language, section, tags, authors);
	}

}
