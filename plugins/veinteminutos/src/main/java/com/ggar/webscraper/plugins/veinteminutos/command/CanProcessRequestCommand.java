package com.ggar.webscraper.plugins.veinteminutos.command;

import java.net.MalformedURLException;
import java.net.URL;

import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.plugins.veinteminutos.config.Operations;

public class CanProcessRequestCommand implements Command<PluginParams<Operations>, Boolean> {

//	TODO: perform checks for multiple article operations
	@Override
	public Boolean execute(PluginParams<Operations> params) {
		boolean result = true;
		try {
			if (params.getOperation().equals(Operations.SINGLE_ARTICLE)) {
				result = new URL(params.getValue()).getHost().equals("www.20minutos.es");
			} else {
				result = true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			result = false;
		}
		return params.getOperation() instanceof Operations && result;
	}

}
