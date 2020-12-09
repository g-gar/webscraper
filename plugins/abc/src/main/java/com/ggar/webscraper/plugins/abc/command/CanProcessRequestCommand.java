package com.ggar.webscraper.plugins.abc.command;

import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.abc.config.Operations;

public class CanProcessRequestCommand implements Command<PluginParams<Operations>, Boolean> {

	@Override
	public Boolean execute(PluginParams<Operations> params) {
		return params.getOperation() instanceof Operations;
	}

}
