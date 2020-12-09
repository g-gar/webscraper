package com.ggar.webscraper.plugins.veinteminutos.model;

import com.ggar.webscraper.core.AbstractArticleBuilder;

public class ArticleBuilder extends AbstractArticleBuilder<Article> {

	private String contentSection, articleSection;
	
	public ArticleBuilder() {
		super();
	}
	
	public ArticleBuilder setContentSection(String contentSection) {
		this.contentSection = contentSection;
		return this;
	}
	
	public ArticleBuilder setArticleSection(String articleSection) {
		this.articleSection = articleSection;
		return this;
	}
	
	@Override
	public Article build() {
		return new Article(
				url,
				publishedDate, 
				modifiedDate, 
				title, 
				summary, 
				content, 
				language,
				String.format("%s:%s", this.contentSection, this.articleSection),
				tags, 
				authors
			);
	}

}
