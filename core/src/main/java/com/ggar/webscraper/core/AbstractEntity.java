package com.ggar.webscraper.core;

import java.util.List;

public abstract class AbstractEntity implements Entity {

    protected final String url, publishedDate, modifiedDate, title, summary, content, language, section;
    protected final List<String> tags, authors;

    public AbstractEntity(String url, String publishedDate, String modifiedDate, String title, String summary,
                          String content, String language, String section, List<String> tags, List<String> authors) {
        super();
        this.url = url;
        this.publishedDate = publishedDate;
        this.modifiedDate = modifiedDate;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.language = language;
        this.section = section;
        this.tags = tags;
		this.authors = authors;
	}

	public String getUrl() {
		return url;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getContent() {
		return content;
	}

	public String getLanguage() {
		return language;
	}

	public String getSection() {
		return section;
	}

	public List<String> getTags() {
		return tags;
	}

	public List<String> getAuthors() {
		return authors;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractArticle [url=").append(url).append(", publishedDate=").append(publishedDate)
				.append(", modifiedDate=").append(modifiedDate).append(", title=").append(title).append(", summary=")
				.append(summary).append(", content=").append(content).append(", language=").append(language)
				.append(", section=").append(section).append(", tags=").append(tags).append(", authors=")
				.append(authors).append("]");
		return builder.toString();
	}

}
