package com.ggar.webscraper.postprocessing.model;

import com.ggar.webscraper.core.Plugin;
import com.ggar.webscraper.core.PluginOperations;
import com.ggar.webscraper.core.PluginParams;

import java.lang.reflect.Constructor;

public enum PluginAdapter {

    VEINTEMINUTOS(com.ggar.webscraper.plugins.veinteminutos.VeinteMinutos.class, com.ggar.webscraper.plugins.veinteminutos.config.Operations.SINGLE_ARTICLE),
    ELPAIS(com.ggar.webscraper.plugins.elpais.ElPais.class, com.ggar.webscraper.plugins.elpais.config.Operations.SINGLE_ARTICLE),
    ABC(com.ggar.webscraper.plugins.abc.Abc.class, com.ggar.webscraper.plugins.abc.config.Operations.SINGLE_ARTICLE);

    private final Class<? extends Plugin> pluginClass;
    private final Enum<? extends PluginOperations> operation;

    PluginAdapter(Class<? extends Plugin> pluginClass, Enum<? extends PluginOperations> operation) {
        this.pluginClass = pluginClass;
        this.operation = operation;
    }

    public <P extends Plugin> P getPlugin() throws Exception {
        Constructor<P> constructor = (Constructor<P>) this.pluginClass.getConstructor();
        return constructor.newInstance();
    }

    public PluginParams getPluginParams(String operation, String value) throws Exception {
        Enum<? extends PluginOperations> opt = Enum.valueOf(this.operation.getClass(), operation.toUpperCase());
        return new PluginParams((PluginOperations) opt, value) {
        };
    }
}
