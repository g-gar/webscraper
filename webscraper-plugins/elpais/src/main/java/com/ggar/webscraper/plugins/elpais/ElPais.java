package com.ggar.webscraper.plugins.elpais;

import com.ggar.webscraper.interfaces.Plugin;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.ggar.webscraper.plugins.elpais.command.CommandFactory;
import com.ggar.webscraper.plugins.elpais.model.Article;
import com.ggar.webscraper.plugins.elpais.model.FindArticleNodesFromListing;
import com.ggar.webscraper.plugins.elpais.model.Templates;
import com.google.inject.Inject;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

public class ElPais implements Plugin {

	public static final Integer INITIAL_INDEX = 0;

	private final CommandFactory commandFactory;

	@Inject
	public ElPais(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	public Article single(URL url) {
		Command<Document> command = commandFactory.getSingle(url);
		Document document = command.execute();
		return commandFactory.convert(document).execute();
	}

	public Collection<Article> section(String section) {
		return commandFactory.getMultiple(commandFactory.getIterator(Templates.SECTION, ElPais.INITIAL_INDEX, section), new FindArticleNodesFromListing())
			.execute()
			.stream()
			.map(document -> commandFactory.convert(document).execute())
			.collect(Collectors.toList());
	}
}
