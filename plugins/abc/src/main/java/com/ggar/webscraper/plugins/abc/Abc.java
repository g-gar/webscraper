package com.ggar.webscraper.plugins.abc;

import java.util.Arrays;
import java.util.List;

import com.ggar.webscraper.core.AbstractArticle;
import com.ggar.webscraper.core.Plugin;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.abc.command.CanProcessRequestCommand;
import com.ggar.webscraper.plugins.abc.command.GenerateIteratorCommand;
import com.ggar.webscraper.plugins.abc.command.GetMultipleArticleCommand;
import com.ggar.webscraper.plugins.abc.command.GetSingleArticleCommand;
import com.ggar.webscraper.plugins.abc.command.GetUrlTemplateCommand;
import com.ggar.webscraper.plugins.abc.config.Operations;
import com.ggar.webscraper.plugins.abc.config.UrlTemplate;
import com.ggar.webscraper.plugins.abc.util.UrlIterator;

public class Abc implements Plugin<PluginParams<Operations>, AbstractArticle, Operations> {
	
	private UrlIterator iterator;
	
//	TODO: perform checks for multiple operations
	public boolean canHandle(PluginParams<Operations> params) {
		return new CanProcessRequestCommand().execute(params);
	}
	
	public List<AbstractArticle> handle(PluginParams<Operations> param) {
		return param.getOperation().equals(Operations.SINGLE_ARTICLE)
			? Arrays.asList(new AbstractArticle[] {get(param)})
			: getMultiple(param);
	}

	public AbstractArticle get(PluginParams<Operations> param) {
		return new GetSingleArticleCommand().execute(param);
		
	}

	public List<AbstractArticle> getMultiple(PluginParams<Operations> param) {
		UrlTemplate template = new GetUrlTemplateCommand().execute(param);
		this.iterator = new GenerateIteratorCommand(param.getValue()).execute(template);
		return new GetMultipleArticleCommand().execute(this.iterator);
	}

	public UrlIterator getIterator() {
		return this.iterator;
	}
}
