package com.ggar.webscraper.model;

import java.util.function.Function;

public interface Interceptor<T> extends Function<T, T> {
}
