package com.ggar.webscraper.plugins.elpais;

import com.ggar.webscraper.interfaces.Plugin;
import com.ggar.webscraper.plugins.common.command.GenerateIterator;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.ggar.webscraper.plugins.elpais.command.CommandFactory;
import com.ggar.webscraper.plugins.elpais.model.Article;
import com.ggar.webscraper.plugins.elpais.model.FindArticleNodesFromListing;
import com.ggar.webscraper.plugins.elpais.model.Templates;
import com.google.inject.Inject;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Collection;
import java.util.function.Function;
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

	public Collection<Article> section(String section, Function<GenerateIterator, Boolean>... stateCheckerFunctions) {
		return this.section(section, ElPais.INITIAL_INDEX, Integer.MAX_VALUE, stateCheckerFunctions);
	}

	public Collection<Article> section(String section, int initialOffset, int maxOffset, Function<GenerateIterator, Boolean>... stateCheckerFunctions) {
		GenerateIterator iterator = commandFactory.getIterator(Templates.SECTION, initialOffset, section)
			.setStateCheckerFunctions(stateCheckerFunctions)
			.setStateCheckerFunctions(it -> it.getCounter().get() <= maxOffset);
		return commandFactory.getMultiple(iterator, new FindArticleNodesFromListing())
			.execute()
			.stream()
			.map(document -> commandFactory.convert(document).execute())
			.collect(Collectors.toList());
	}
}
