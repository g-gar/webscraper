package com.ggar.webscraper.interfaces;

import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public interface DownloaderService<T> {

    T get(URL url);
    Collection<T> get(Collection<URL> urls);

}
