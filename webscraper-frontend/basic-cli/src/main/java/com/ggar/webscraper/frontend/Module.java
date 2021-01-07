package com.ggar.webscraper.frontend;

import com.ggar.webscraper.BaseModule;

public class Module extends BaseModule {
	@Override
	protected void configure() {
		super.configure();
//		Configure extensions

//		Configure plugins
		install(new com.ggar.webscraper.plugins.abc.Module(
			connection -> connection
		));
		install(new com.ggar.webscraper.plugins.elpais.Module(
			connection -> connection
		));
		install(new com.ggar.webscraper.plugins.veinteminutos.Module(
			connection -> connection
		));
	}
}
