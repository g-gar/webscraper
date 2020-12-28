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
        /*group.addOption(Option.builder("s")
                .longOpt("scrape")
                .desc("Perform scraping at the beginning of execution")
                .build());*/
        addOptionGroup(group);

        /*OptionGroup sgroup = new OptionGroup();
        sgroup.addOption(Option.builder()
                .longOpt("elpais")
                .desc("Scrape from elpais.com")
                .build());
        sgroup.addOption(Option.builder()
                .longOpt("abc")
                .desc("Scrape from abc.es")
                .build());
        sgroup.addOption(Option.builder()
                .longOpt("20minutos")
                .desc("Scrape from 20minutos.es")
                .build());
        addOptionGroup(sgroup);*/

        addOption(Option.builder("s")
                .longOpt("scrape")
                .desc("Perform scraping at the beginning of execution")
                .build());

        addOption(Option.builder("ss")
                .longOpt("source")
                .desc("Scrape source")
                .hasArg()
                .valueSeparator(' ')
                .build());

        addOption(Option.builder("so")
                .longOpt("operation")
                .desc("Scrape operation type")
                .hasArg()
                .valueSeparator(' ')
                .build());

        addOption(Option.builder("sv")
                .longOpt("value")
                .desc("Scrape operation value")
                .hasArg()
                .valueSeparator(' ')
                .build());

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
