package com.ggar.webscraper.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;

import com.ggar.webscraper.core.Article;
import com.ggar.webscraper.core.Plugin;
import com.ggar.webscraper.core.PluginManager;
import com.ggar.webscraper.core.PluginParams;

public class App {
	
	private static App instance;
	private final PluginManager plugins;
	public final static Logger log = Logger.getGlobal();
	
	private App() {
		plugins = new PluginManager();
		
		ClassFinder finder = new ClassFinder();
        finder.addClassPath();
        
        ClassFilter filter = new ImplementInterfaceFilter("Plugin");
        Collection<ClassInfo> foundClasses = new ArrayList<ClassInfo>();
        finder.findClasses(foundClasses, filter);
		
		for (ClassInfo p : foundClasses) {
			try {
				Class<Plugin> plugin = (Class<Plugin>) Class.forName(p.getClassName());
				if (!plugins.containsKey( plugin )) {
					plugins.put(plugin, plugin.newInstance());
				} else {
					throw new Exception();
				}
				log.info(String.format("Plugin [%s] registered\n", plugin.getName()));
			} catch (Exception e) {
				log.info(String.format("Error registering plugin [%s]\n%s\n", p.getClassName(), e.toString()));
			}
		}
	}
	
	public static App getInstance() {
		if (instance == null) {
			instance = new App();
		}
		return instance;
	}
	
	public <T extends PluginParams, A extends Article> List<A> execute(T params) {
		List<A> result = new ArrayList<>();
		for (Plugin plugin : (List<Plugin>) this.plugins.getAvaliablePlugins(params)) {
			plugin.handle(params).forEach(e -> result.add((A) e));
		}
		return result;
	}
	
}
