package com.ggar.webscraper.postprocessing;

import com.ggar.stepping.core.Step;
import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.ArgsParser;
import com.ggar.webscraper.postprocessing.cli.CliOptions;
import com.ggar.webscraper.postprocessing.cli.condition.input.CsvInputCondition;
import com.ggar.webscraper.postprocessing.cli.condition.input.InputConditionGroup;
import com.ggar.webscraper.postprocessing.cli.condition.input.ScrapeInputCondition;
import com.ggar.webscraper.postprocessing.cli.condition.output.JsonOutputCondition;
import com.ggar.webscraper.postprocessing.cli.condition.output.OutputConditionGroup;
import com.ggar.webscraper.postprocessing.cli.condition.output.TxtOutputCondition;
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
                    .register(new InputConditionGroup(builder)
                            .addCondition(new CsvInputCondition(builder))
                    )
                    .register(new OutputConditionGroup(builder)
                            .addCondition(new JsonOutputCondition(builder))
                            .addCondition(new TxtOutputCondition(builder))
                    )
                    .register(new ScrapeInputCondition(builder));

            String[] names = new String[]{"input", "scrape", "output"};
            for (String name : names) {
                try {
                    parser.get(name);
                } catch (Exception e) {
                    App.log.info(String.format("Error: Condition with name [%s] not registered.\n Stacktrace: %s\n", name, e));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
//			TODO: show help
        } finally {
            App.getInstance().execute(builder.get());
        }
    }

}
