package com.ggar.webscraper.frontend;

import com.ggar.webscraper.BaseModule;
import com.ggar.webscraper.extensions.jsoup.JsoupModule;

public class Module extends BaseModule {
	@Override
	protected void configure() {
		super.configure();
//		Configure extensions
		install(new JsoupModule());

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
