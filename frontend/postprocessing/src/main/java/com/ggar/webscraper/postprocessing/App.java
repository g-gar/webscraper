package com.ggar.webscraper.postprocessing;

import java.nio.file.Path;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import com.ggar.webscraper.core.Article;
import com.ggar.webscraper.postprocessing.command.ParseCsvCommand;
import com.ggar.webscraper.postprocessing.command.ParseDbCommand;

public class App {

    private static App instance;
    public final static Logger log = Logger.getGlobal();

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public List<Article> parse(Path... paths) {
        return new ParseCsvCommand().execute(paths);
    }

    public List<Article> parse(Connection connection) {
        return new ParseDbCommand().execute(connection);
    }
}
