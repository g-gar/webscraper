package com.ggar.webscraper.plugins.common.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {

    public static URL create(String url) {
        URL result = null;
        try {
            result = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
