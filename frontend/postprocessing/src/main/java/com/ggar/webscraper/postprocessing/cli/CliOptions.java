package com.ggar.webscraper.postprocessing.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

public class CliOptions extends Options {

    public CliOptions() {

        addOption(Option.builder("i")
                .longOpt("input")
                .desc("Set input enabled flag")
                .build());

        OptionGroup group = new OptionGroup();
        group.addOption(Option.builder("c")
                .longOpt("csv")
                .desc("Input CSV file/s separated by space")
                .valueSeparator(' ')
                .hasArgs()
                .build());
        group.addOption(Option.builder("s")
                .longOpt("sqlite")
                .desc("path to a sqlite database")
                .valueSeparator(' ')
                .hasArg()
                .build());
        addOptionGroup(group);

        addOption(Option.builder("o")
                .longOpt("output")
                .desc("set output enabled flag")
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
        addOptionGroup(group1);
    }

}
