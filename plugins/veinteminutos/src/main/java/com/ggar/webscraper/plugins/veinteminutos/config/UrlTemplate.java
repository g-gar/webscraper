package com.ggar.webscraper.plugins.veinteminutos.config;

import com.ggar.webscraper.core.PluginUrlTemplate;

public enum UrlTemplate implements PluginUrlTemplate {

	CATEGORY("https://www.20minutos.es/**/%s/"),
	MINUTECA("https://www.20minutos.es/minuteca/**/%s/"),
	KEYWORD("https://www.20minutos.es/busqueda/%s/?q=**&sort_field=&category=&publishedAt[from]=&publishedAt[until]=&articleTypes[0]=default"),
	KEYWORD_WITH_SECTION("https://www.20minutos.es/busqueda/%s/?q=**&sort_field=&category=**&publishedAt[from]=&publishedAt[until]=&articleTypes[]=default");
	
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
