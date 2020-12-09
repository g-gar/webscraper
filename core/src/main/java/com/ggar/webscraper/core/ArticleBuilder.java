package com.ggar.webscraper.core;

public interface ArticleBuilder<T extends Article> {

	T build();
	
}
