package com.ggar.webscraper.interfaces;

import java.util.function.Function;

public interface Interceptable<THIS extends Interceptable<THIS, T>, T> {

	THIS registerInterceptor(Function<T,T>... interceptors);
	T intercept(T entity);

}
