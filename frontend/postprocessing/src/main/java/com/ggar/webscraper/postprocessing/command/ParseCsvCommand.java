package com.ggar.webscraper.postprocessing.command;

import java.nio.file.Path;
import java.util.List;

import com.ggar.webscraper.core.Article;

public class ParseCsvCommand implements Command<Path[], List<Article>> {

    @Override
    public List<Article> execute(Path[] t) {
        return null;
    }

}
