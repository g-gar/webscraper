package com.ggar.webscraper.plugins.abc;

import com.ggar.webscraper.interfaces.Plugin;
import com.ggar.webscraper.plugins.abc.command.CommandFactory;
import com.ggar.webscraper.plugins.abc.model.Article;
import com.ggar.webscraper.plugins.abc.model.FindArticleNodesFromListing;
import com.ggar.webscraper.plugins.abc.model.Templates;
import com.ggar.webscraper.plugins.common.command.GenerateIterator;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.google.inject.Inject;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Abc implements Plugin {

    public static final Integer INITIAL_INDEX = 1;

    private final CommandFactory commandFactory;

    @Inject
    public Abc(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public Article single(URL url) {
        Command<Document> command = commandFactory.getSingle(url);
        Document document = command.execute();
        return commandFactory.convert(document).execute();
    }

    public Collection<Article> keyword(String keyword, Function<GenerateIterator, Boolean>... stateCheckerFunctions) {
        Iterator<Document> iterator = commandFactory.getIterator(Templates.KEYWORD, Abc.INITIAL_INDEX, keyword).setStateCheckerFunctions(stateCheckerFunctions);
        return commandFactory.getMultiple(iterator, new FindArticleNodesFromListing())
            .execute()
            .stream()
            .map(document -> commandFactory.convert(document).execute())
            .collect(Collectors.toList());
    }
}
