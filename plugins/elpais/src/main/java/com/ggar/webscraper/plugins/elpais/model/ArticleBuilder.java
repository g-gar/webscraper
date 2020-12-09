package com.ggar.webscraper.plugins.elpais.model;

import com.ggar.webscraper.core.AbstractArticle;
import com.ggar.webscraper.core.AbstractArticleBuilder;

public class ArticleBuilder extends AbstractArticleBuilder<AbstractArticle> {

	@Override
	public AbstractArticle build() {
		return new AbstractArticle(
				url,
				publishedDate, 
				modifiedDate, 
				title, 
				summary, 
				content, 
				language,
				section,
				tags, 
				authors
			) {};
	}

}
