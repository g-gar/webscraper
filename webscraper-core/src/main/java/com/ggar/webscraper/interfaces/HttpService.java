package com.ggar.webscraper.interfaces;

import com.ggar.webscraper.model.Interceptor;

import java.net.URL;
import java.util.function.Function;

public interface HttpService<T> {

    T execute(URL url);

}
