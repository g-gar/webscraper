package com.ggar.webscraper.plugins.test.model;

import com.ggar.webscraper.plugins.abc.model.Keyword;
import com.ggar.webscraper.plugins.test.model.annotation.TestAnnotation;
import com.ggar.webscraper.plugins.test.model.interfaces.TestFactory;
import com.google.inject.Inject;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Iterator;

public class FactoryHolder {

    @Inject private TestFactory factory;

    @Keyword
    public Iterator<Document> getKeywordIterator(String keyword) {
        return factory.create(keyword);
    }

    @TestAnnotation
    public Iterator<Document> getTestIterator(URL url) {
        return factory.create(url);
    }

}
