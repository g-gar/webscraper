package com.ggar.webscraper.plugins.elpais;

import java.util.Arrays;
import java.util.List;

import com.ggar.webscraper.core.AbstractArticle;
import com.ggar.webscraper.core.Plugin;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.elpais.command.CanProcessRequestCommand;
import com.ggar.webscraper.plugins.elpais.command.GenerateIteratorCommand;
import com.ggar.webscraper.plugins.elpais.command.GetMultipleArticleCommand;
import com.ggar.webscraper.plugins.elpais.command.GetSingleArticleCommand;
import com.ggar.webscraper.plugins.elpais.command.GetUrlTemplateCommand;
import com.ggar.webscraper.plugins.elpais.config.Operations;
import com.ggar.webscraper.plugins.elpais.config.UrlTemplate;
import com.ggar.webscraper.plugins.elpais.util.UrlIterator;

public class ElPais implements Plugin<PluginParams<Operations>, AbstractArticle, Operations> {
	
	private UrlIterator iterator;
	
//	TODO: perform checks for multiple operations
	@Override
	public boolean canHandle(PluginParams<Operations> params) {
		return new CanProcessRequestCommand().execute(params);
	}

	public List<AbstractArticle> handle(PluginParams<Operations> param) {
		return param.getOperation().equals(Operations.SINGLE_ARTICLE)
			? Arrays.asList(new AbstractArticle[] { new GetSingleArticleCommand().execute(param) })
			: getMultiple(param);
	}

	public AbstractArticle get(PluginParams<Operations> param) {
		return new GetSingleArticleCommand().execute(param);
	}

	public List<AbstractArticle> getMultiple(PluginParams<Operations> param) {
		UrlTemplate template = new GetUrlTemplateCommand().execute(param);
		this.iterator = new GenerateIteratorCommand(param.getValue()).execute(template);
		List<AbstractArticle> as = new GetMultipleArticleCommand().execute(this.iterator);
		return as;
	}
	
	public UrlIterator getIterator() {
		return this.iterator;
	}
}
