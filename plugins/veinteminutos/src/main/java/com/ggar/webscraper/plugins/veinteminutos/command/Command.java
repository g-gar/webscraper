package com.ggar.webscraper.plugins.veinteminutos.command;

public interface Command<T, R> {

	R execute (T t);
	
}
