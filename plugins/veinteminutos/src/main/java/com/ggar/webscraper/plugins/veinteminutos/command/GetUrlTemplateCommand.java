package com.ggar.webscraper.plugins.veinteminutos.command;

import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.veinteminutos.config.Operations;
import com.ggar.webscraper.plugins.veinteminutos.config.UrlTemplate;
import com.ggar.webscraper.plugins.veinteminutos.config.UrlTemplateMapping;

public class GetUrlTemplateCommand implements Command<PluginParams<Operations>, UrlTemplate> {

	@Override
	public UrlTemplate execute(PluginParams<Operations> param) {
		return UrlTemplateMapping.valueOf(param.getOperation().name()).get();
	}

}
