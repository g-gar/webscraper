package com.ggar.webscraper.postprocessing;

import com.ggar.stepping.core.executors.GraphExecutor;
import com.ggar.stepping.core.strategies.DefaultGraphExecutorStrategy;
import org.jgrapht.Graph;

import java.util.logging.Logger;

public class App {

    private static App instance;
    private final GraphExecutor graphExecutor;
    public final static Logger log = Logger.getGlobal();

    public App() {
        this.graphExecutor = new GraphExecutor(new DefaultGraphExecutorStrategy());
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public <T, R> Graph<T, R> execute(Graph<T, R> graph) {
        return this.graphExecutor.execute(graph);
    }

}
