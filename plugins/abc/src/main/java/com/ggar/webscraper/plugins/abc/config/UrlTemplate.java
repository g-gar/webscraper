package com.ggar.webscraper.plugins.abc.config;

import com.ggar.webscraper.core.PluginUrlTemplate;

public enum UrlTemplate implements PluginUrlTemplate {

	KEYWORD("https://www.abc.es/hemeroteca/resultados-busqueda-avanzada/noticia/pagina-%s?tod=**&nres=20");
	
	private final String template;
	
	private UrlTemplate(String template) {
		this.template = template;
	}
	
	public String get(Object...replacements) {
		String result = this.template;
		for (Object replacement : replacements) {
			result = result.replaceFirst("\\*\\*", replacement.toString());
		}
		return result;
	}
	
}
