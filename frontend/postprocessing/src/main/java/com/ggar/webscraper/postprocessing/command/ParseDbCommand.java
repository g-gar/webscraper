package com.ggar.webscraper.postprocessing.command;

import java.sql.Connection;
import java.util.List;

import com.ggar.webscraper.core.Article;

public class ParseDbCommand implements Command<Connection, List<Article>> {

    @Override
    public List<Article> execute(Connection t) {
        return null;
    }

}
