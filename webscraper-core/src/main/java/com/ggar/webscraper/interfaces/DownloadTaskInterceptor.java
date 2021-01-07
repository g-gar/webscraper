package com.ggar.webscraper.interfaces;

import java.util.function.Function;

public interface DownloadTaskInterceptor<T extends DownloadTask, R> {

	T registerConnectionInterceptors(Function<R,R>... interceptors);
	R intercept(R entity);

}
