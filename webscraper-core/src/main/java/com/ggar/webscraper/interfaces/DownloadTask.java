package com.ggar.webscraper.interfaces;

import java.util.concurrent.Callable;

public interface DownloadTask<T, R> extends Callable<T> {

}
