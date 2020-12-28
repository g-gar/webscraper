package com.ggar.webscraper.postprocessing.cli.condition.input;

import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.condition.SingleCondition;
import com.ggar.webscraper.postprocessing.model.PluginAdapter;
import com.ggar.webscraper.postprocessing.step.ScrapeStep;
import org.apache.commons.cli.CommandLine;

public class PluginCondition extends SingleCondition {

    private final GraphBuilder builder;
    private final SingleCondition operationType, operationValue;

    public PluginCondition(GraphBuilder builder, SingleCondition operationType, SingleCondition operationValue) {
        super("ss");
        this.operationType = operationType;
        this.operationValue = operationValue;
        this.builder = builder;
    }

    @Override
    public <T> T get(CommandLine commandLine) {
        try {
            PluginAdapter adapter = null;
            switch (commandLine.getOptionValue(this.option).toLowerCase()) {
                case "elpais":
                    adapter = PluginAdapter.ELPAIS;
                    break;
                case "veinteminutos":
                    adapter = PluginAdapter.VEINTEMINUTOS;
                    break;
                case "abc":
                    adapter = PluginAdapter.ABC;
                    break;
                default:
                    //do nothing
            }
            this.builder.addNode(new ScrapeStep(adapter, operationType.get(commandLine), operationValue.get(commandLine)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) this.builder;
    }
}
