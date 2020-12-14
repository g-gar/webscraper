package com.ggar.scraper.posprocessing;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.junit.Assert;
import org.junit.Test;

import com.ggar.webscraper.postprocessing.Main;

public class MainTest {

    @Test
    public void parseFileListArgs() throws Exception {
        String[] args = "-i \"C:/Users/ggarf/Desktop/eclipse-jee-2020-06-R-win32-x86_64/workspace_pc1/run/output2.csv\" \"C:/Users/ggarf/Desktop/eclipse-jee-2020-06-R-win32-x86_64/workspace_pc1/run/output3.csv\"".split(" ");
        File[] expected = new File[]{
                new File("C:/Users/ggarf/Desktop/eclipse-jee-2020-06-R-win32-x86_64/workspace_pc1/run/output2.csv"),
                new File("C:/Users/ggarf/Desktop/eclipse-jee-2020-06-R-win32-x86_64/workspace_pc1/run/output3.csv")
        };

        Main main = new Main();
        Options options = main.configureArgsParser();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("i")) {
            File[] paths = (File[]) cmd.getParsedOptionValue("i");
            System.out.println(paths);
            Assert.assertTrue(Arrays.asList(cmd.getOptionValues("i")).toString(), Arrays.equals(cmd.getOptionValues("i"), expected));
        } else {
            throw new Exception("No input found");
        }
    }
}
