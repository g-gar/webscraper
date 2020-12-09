package com.ggar.webscraper.plugins.elpais.command;

public interface Command<T, R> {

	R execute(T t);
	
}
