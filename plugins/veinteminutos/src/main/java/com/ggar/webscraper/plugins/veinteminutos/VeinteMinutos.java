package com.ggar.webscraper.plugins.veinteminutos;

import java.util.Arrays;
import java.util.List;

import com.ggar.webscraper.core.Plugin;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.veinteminutos.command.CanProcessRequestCommand;
import com.ggar.webscraper.plugins.veinteminutos.command.GenerateIteratorCommand;
import com.ggar.webscraper.plugins.veinteminutos.command.GetMultipleArticlesCommand;
import com.ggar.webscraper.plugins.veinteminutos.command.GetSingleArticleCommand;
import com.ggar.webscraper.plugins.veinteminutos.command.GetUrlTemplateCommand;
import com.ggar.webscraper.plugins.veinteminutos.config.Operations;
import com.ggar.webscraper.plugins.veinteminutos.config.UrlTemplate;
import com.ggar.webscraper.plugins.veinteminutos.model.Article;
import com.ggar.webscraper.plugins.veinteminutos.util.UrlIterator;

public class VeinteMinutos implements Plugin<PluginParams<Operations>, Article, Operations> {
	
	private UrlIterator iterator;
	
	@Override
	public boolean canHandle(PluginParams<Operations> params) {
		return new CanProcessRequestCommand().execute(params);
	}
	
	@Override
	public List<Article> handle(PluginParams<Operations> param) {
		return param.getOperation().equals(Operations.SINGLE_ARTICLE)
			? Arrays.asList(new Article[] { new GetSingleArticleCommand().execute(param) })
			: getMultiple(param);
	}

	public List<Article> getMultiple(PluginParams<Operations> param) {
		UrlTemplate template = new GetUrlTemplateCommand().execute(param);
		this.iterator = new GenerateIteratorCommand(param.getValue().split(";")).execute(template);
		return new GetMultipleArticlesCommand().execute(this.iterator);
	}
	
	public UrlIterator getIterator() {
		return this.iterator;
	}
}
