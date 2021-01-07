package com.ggar.webscraper.interfaces;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ExecutorService {

    <T> Future<T> execute(Callable<T> callable);

}
