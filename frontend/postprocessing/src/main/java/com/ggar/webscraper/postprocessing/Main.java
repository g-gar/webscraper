package com.ggar.webscraper.postprocessing;

import com.ggar.stepping.core.Step;
import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.webscraper.postprocessing.model.Article;
import com.ggar.webscraper.postprocessing.model.CsvStringToArticleConversor;
import com.ggar.webscraper.postprocessing.model.TxtArticleContentGenerator;
import com.ggar.webscraper.postprocessing.step.OutputJsonStep;
import com.ggar.webscraper.postprocessing.step.OutputTxtStep;
import com.ggar.webscraper.postprocessing.step.ParseCsvLinesStep;
import com.ggar.webscraper.postprocessing.step.ReadCsvFileStep;
import org.apache.commons.cli.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
            CommandLine cmd = parseArgs(configureArgsParser(), args);
            if (cmd.getOptionObject('i') != null) {
                builder.addRootNodes(Arrays.asList(cmd.getOptionValues('i')).stream()
                        .map(Paths::get)
                        .map(ReadCsvFileStep::new)
                        .collect(Collectors.toList())
                        .toArray(new Step[cmd.getOptionValues('i').length])
                );
                builder.addNode(new ParseCsvLinesStep<Article>(new CsvStringToArticleConversor()));
            } else if (cmd.getOptionObject('s') != null) {

            } else {
                throw new Exception("No option provided");
            }

            if (true) { // TODO: check if items are available to output
                if (cmd.hasOption('o')) {
                    if (cmd.hasOption('j')) {
                        builder.addNode(new OutputJsonStep(Paths.get(cmd.getOptionValue('j'))));
                    } else if (cmd.hasOption('t')) {
                        AtomicInteger counter = new AtomicInteger(0);
                        builder.addNode(new OutputTxtStep(
                                Paths.get(cmd.getOptionValue('t')),
                                new TxtArticleContentGenerator(),
                                article -> String.valueOf(counter.getAndIncrement())
                        ));
                    }
                }
            }

            App.getInstance().execute(builder.get());
        } catch (Exception e) {
            e.printStackTrace();
//			TODO: show help
        } finally {

        }
    }

    public static Options configureArgsParser() {
        Options options = new Options();

        OptionGroup group = new OptionGroup();
        group.addOption(Option.builder("i")
                .longOpt("input")
                .desc("Input file/s separated by space")
                .valueSeparator(' ')
                .hasArgs()
                .build());
        group.addOption(Option.builder("s")
                .longOpt("sqlite")
                .desc("path to a sqlite database")
                .valueSeparator(' ')
                .hasArg()
                .build());
        options.addOptionGroup(group);

        options.addOption(Option.builder("o")
                .longOpt("output")
                .desc("Output file where to store the processed objects")
                .valueSeparator(' ')
                .build());

        OptionGroup group1 = new OptionGroup();
        group1.addOption(Option.builder("j")
                .longOpt("json output")
                .desc("Dump everything as a single json file")
                .hasArg()
                .build());
        group1.addOption(Option.builder("t")
                .longOpt("txt output")
                .desc("Dump everything as multiple txt files at the desired location")
                .hasArg()
                .build());
        options.addOptionGroup(group1);

        return options;
    }

    public static CommandLine parseArgs(Options options, String... args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }
}
