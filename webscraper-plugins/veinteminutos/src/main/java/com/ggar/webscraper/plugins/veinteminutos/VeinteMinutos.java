package com.ggar.webscraper.plugins.veinteminutos;

import com.ggar.webscraper.interfaces.Plugin;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.ggar.webscraper.plugins.veinteminutos.command.CommandFactory;
import com.ggar.webscraper.plugins.veinteminutos.model.Article;
import com.ggar.webscraper.plugins.veinteminutos.model.FindArticleNodesFromListing;
import com.ggar.webscraper.plugins.veinteminutos.model.Templates;
import com.google.inject.Inject;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

public class VeinteMinutos implements Plugin {

    public static final Integer INITIAL_INDEX = 1;

    private final CommandFactory commandFactory;

    @Inject
    public VeinteMinutos(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public Article single(URL url) {
        Command<Document> command = commandFactory.getSingle(url);
        Document document = command.execute();
        Article article = commandFactory.convert(document).execute();
        return article;
    }

    public Collection<Article> category(String category) {
        return commandFactory.getMultiple(commandFactory.getIterator(Templates.CATEGORY, VeinteMinutos.INITIAL_INDEX, category), new FindArticleNodesFromListing())
                .execute()
                .stream()
                .map(document -> commandFactory.convert(document).execute())
                .collect(Collectors.toList());
    }

    public Collection<Article> minuteca(String minuteca) {
        return commandFactory.getMultiple(commandFactory.getIterator(Templates.CATEGORY, VeinteMinutos.INITIAL_INDEX, minuteca), new FindArticleNodesFromListing())
                .execute()
                .stream()
                .map(document -> commandFactory.convert(document).execute())
                .collect(Collectors.toList());
    }

    public Collection<Article> keyword(String keyword) {
        return commandFactory.getMultiple(commandFactory.getIterator(Templates.KEYWORD, VeinteMinutos.INITIAL_INDEX, keyword), new FindArticleNodesFromListing())
                .execute()
                .stream()
                .map(document -> commandFactory.convert(document).execute())
                .collect(Collectors.toList());
    }

    public Collection<Article> keywordWithSection(String keyword, String section) {
        return commandFactory.getMultiple(commandFactory.getIterator(Templates.KEYWORD_WITH_SECTION, VeinteMinutos.INITIAL_INDEX, keyword, section), new FindArticleNodesFromListing())
                .execute()
                .stream()
                .map(document -> commandFactory.convert(document).execute())
                .collect(Collectors.toList());
    }

}
