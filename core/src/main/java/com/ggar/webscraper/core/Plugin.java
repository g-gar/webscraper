package com.ggar.webscraper.core;

import java.util.List;

public interface Plugin<P extends PluginParams<C>, A extends Entity, C extends PluginOperations> {

    boolean canHandle(P params);

    List<A> handle(P param);

}
