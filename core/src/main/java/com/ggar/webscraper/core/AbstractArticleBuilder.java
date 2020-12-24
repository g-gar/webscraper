package com.ggar.webscraper.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractArticleBuilder<T extends Entity> {

    protected String url, publishedDate, modifiedDate, title, summary, content, language, section;
    protected List<String> tags, authors;

    public AbstractArticleBuilder() {

    }

    public AbstractArticleBuilder<T> setUrl(String url) {
        this.url = url;
		return this;
	}

	public AbstractArticleBuilder<T> setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
		return this;
	}

	public AbstractArticleBuilder<T> setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
		return this;
	}

	public AbstractArticleBuilder<T> setTitle(String title) {
		this.title = title;
		return this;
	}

	public AbstractArticleBuilder<T> setSummary(String summary) {
		this.summary = summary;
		return this;
	}
	
	public AbstractArticleBuilder<T> setContent(String content) {
		this.content = content;
		return this;
	}

	public AbstractArticleBuilder<T> setLanguage(String language) {
		this.language = language;
		return this;
	}
	
	public AbstractArticleBuilder<T> setSection(String section) {
		this.section = section;
		return this;
	}
	
	public AbstractArticleBuilder<T> setTags(List<String> tags) {
		this.tags = tags;
		return this;
	}
	
	public AbstractArticleBuilder<T> setTag(String tag) {
		if (this.tags == null) this.tags = new ArrayList<String>();
		this.tags.add(tag);
		return this;
	}

	public AbstractArticleBuilder<T> setAuthors(List<String> authors) {
		this.authors = authors;
		return this;
	}
	
	public AbstractArticleBuilder<T> setAuthor(String author) {
		if (this.authors == null) this.authors = new ArrayList<String>();
		this.authors.add(author);
		return this;
	}
	
	public abstract T build();
	
}
