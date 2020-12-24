package com.ggar.webscraper.plugins.elpais.model;

import com.ggar.webscraper.core.AbstractEntity;
import com.ggar.webscraper.core.AbstractArticleBuilder;

public class ArticleBuilder extends AbstractArticleBuilder<AbstractEntity> {

    @Override
    public AbstractEntity build() {
        return new AbstractEntity(
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
