package com.ggar.webscraper.postprocessing.command;

public interface Command<T, R> {

    public R execute(T t);

}
