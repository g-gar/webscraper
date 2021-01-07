package com.ggar.webscraper.plugins.test.model.interfaces;

import com.ggar.webscraper.plugins.abc.model.Keyword;
import com.ggar.webscraper.plugins.test.model.annotation.TestAnnotation;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Iterator;

public interface TestFactory {

    @Keyword
    Iterator<Document> create(String keyword);
    @TestAnnotation
    Iterator<Document> create(URL url);

}
