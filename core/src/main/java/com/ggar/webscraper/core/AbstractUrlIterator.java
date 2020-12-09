package com.ggar.webscraper.core;

import java.util.function.Function;

public class AbstractUrlIterator<T> implements PluginUrlIterator<T> {

    protected final Function<Integer, T> fn;
    protected Integer index;
    private boolean stop;

    public AbstractUrlIterator(Function<Integer, T> fn, Integer initialIndex) {
        this.fn = fn;
        this.index = initialIndex;
        this.stop = false;
    }

    @Override
    public boolean hasNext() {
        return !this.stop;
    }

    @Override
    public T next() {
        return this.hasNext() ? this.fn.apply(this.index++) : null;
    }

    public void stopIteration() {
        this.stop = true;
    }
}
