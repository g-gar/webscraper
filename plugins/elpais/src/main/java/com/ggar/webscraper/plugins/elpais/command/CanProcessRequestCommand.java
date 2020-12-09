package com.ggar.webscraper.plugins.elpais.command;

import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.elpais.config.Operations;

public class CanProcessRequestCommand implements Command<PluginParams<Operations>, Boolean> {

	@Override
	public Boolean execute(PluginParams<Operations> params) {
		return params.getOperation() instanceof Operations;
	}

}
