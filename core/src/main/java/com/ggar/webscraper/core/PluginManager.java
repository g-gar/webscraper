package com.ggar.webscraper.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PluginManager extends HashMap<Class<Plugin>, Plugin> {

	private static final long serialVersionUID = 1L;
	
	public <T extends PluginParams<C>, R extends Article, C extends PluginOperations, U extends PluginUrlTemplate> List<Plugin> getAvaliablePlugins(T params) {		
		List<Plugin> result = new ArrayList();
		for (Plugin plugin : this.values()) {
			try {
				if (plugin.canHandle(params)) {
					result.add(plugin);
				}
			} catch (Exception e) {
				// do nothing
			}
		}
		return result;
	}

}
