package com.ggar.webscraper.postprocessing;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.ggar.webscraper.core.Article;

public class Main {

    public static void main(String[] args) {
        List<Article> items = null;
        try {
            CommandLine cmd = parseArgs(configureArgsParser(), args);
            ;
            if (cmd.getOptionObject('i') != null) {
                items = App.getInstance().parse(Arrays.asList(cmd.getOptionValues('i')).stream().map(Paths::get).toArray(Path[]::new));
            } else if (cmd.getOptionObject('s') != null) {

            } else {
                throw new Exception("No option provided");
            }

            if (items != null) {
                if (cmd.getOptionObject('o') != null) {

                }
            }
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
                .type(File[].class)
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
                .hasArg()
                .build());

        return options;
    }

    public static CommandLine parseArgs(Options options, String... args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }
}
