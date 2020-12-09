package com.ggar.webscraper.plugins.abc.command;

public interface Command<T, R> {

	R execute(T t);
	
}
