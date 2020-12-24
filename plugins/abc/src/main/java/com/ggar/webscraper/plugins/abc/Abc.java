package com.ggar.webscraper.plugins.abc;

import com.ggar.webscraper.core.AbstractEntity;
import com.ggar.webscraper.core.Plugin;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.abc.command.*;
import com.ggar.webscraper.plugins.abc.config.Operations;
import com.ggar.webscraper.plugins.abc.config.UrlTemplate;
import com.ggar.webscraper.plugins.abc.util.UrlIterator;

import java.util.Arrays;
import java.util.List;

public class Abc implements Plugin<PluginParams<Operations>, AbstractEntity, Operations> {

	private UrlIterator iterator;

	//	TODO: perform checks for multiple operations
	public boolean canHandle(PluginParams<Operations> params) {
		return new CanProcessRequestCommand().execute(params);
	}

	public List<AbstractEntity> handle(PluginParams<Operations> param) {
		return param.getOperation().equals(Operations.SINGLE_ARTICLE)
				? Arrays.asList(new AbstractEntity[]{get(param)})
				: getMultiple(param);
	}

	public AbstractEntity get(PluginParams<Operations> param) {
		return new GetSingleArticleCommand().execute(param);

	}

	public List<AbstractEntity> getMultiple(PluginParams<Operations> param) {
		UrlTemplate template = new GetUrlTemplateCommand().execute(param);
		this.iterator = new GenerateIteratorCommand(param.getValue()).execute(template);
		return new GetMultipleArticleCommand().execute(this.iterator);
	}

	public UrlIterator getIterator() {
		return this.iterator;
	}
}
