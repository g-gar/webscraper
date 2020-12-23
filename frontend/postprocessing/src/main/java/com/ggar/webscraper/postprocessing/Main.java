package com.ggar.webscraper.postprocessing;

import com.ggar.stepping.core.Step;
import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.ArgsParser;
import com.ggar.webscraper.postprocessing.cli.CliOptions;
import com.ggar.webscraper.postprocessing.cli.condition.input.InputConditionGroup;
import com.ggar.webscraper.postprocessing.cli.condition.output.OutputConditionGroup;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

public class Main {

    public static void main(String[] args) {
        Graph<Step, DefaultEdge> graph = GraphTypeBuilder
                .<Step, DefaultEdge>directed()
                .allowingMultipleEdges(true)
                .allowingSelfLoops(false)
                .edgeClass(DefaultEdge.class)
                .weighted(false)
                .buildGraph();
        GraphBuilder<Graph<Step, DefaultEdge>, Step, DefaultEdge> builder = new GraphBuilder<>(graph);

        try {
            ArgsParser parser = ArgsParser.getInstance()
                    .parse(new CliOptions(), args)
                    .register(new InputConditionGroup(builder))
                    .register(new OutputConditionGroup(builder));

            parser.get('i');
            parser.get('o');

        } catch (Exception e) {
            e.printStackTrace();
//			TODO: show help
        } finally {
            App.getInstance().execute(builder.get());
        }
    }

    public void main2() {
        Graph<Step, DefaultEdge> graph = GraphTypeBuilder
                .<Step, DefaultEdge>directed()
                .allowingMultipleEdges(true)
                .allowingSelfLoops(false)
                .edgeClass(DefaultEdge.class)
                .weighted(false)
                .buildGraph();
        GraphBuilder<Graph<Step, DefaultEdge>, Step, DefaultEdge> builder = new GraphBuilder<>(graph);
        ArgsParser parser = new ArgsParser();
        //parser.register('i', )
    }

}
