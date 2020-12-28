package com.ggar.webscraper.postprocessing.cli.condition.input;

import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.condition.MutuallyInclusiveConditionGroup;
import com.ggar.tools.argsparser.condition.SingleCondition;
import com.ggar.webscraper.postprocessing.model.PluginAdapter;
import com.ggar.webscraper.postprocessing.step.ScrapeStep;
import org.apache.commons.cli.CommandLine;

public class ScrapeInputCondition extends MutuallyInclusiveConditionGroup {

    private final GraphBuilder builder;

    public ScrapeInputCondition(GraphBuilder builder) {
        super("scrape");
        this.builder = builder;
        addCondition(new SingleCondition("so") {
        });
        addCondition(new SingleCondition("so") {
        });
        addCondition(new SingleCondition("sv") {
        });
    }

    @Override
    public <T> T get(CommandLine commandLine) {
        try {
            SingleCondition operationType = (SingleCondition) super.conditions.get("so"),
                    operationValue = (SingleCondition) super.conditions.get("sv");
            PluginAdapter adapter = null;
            switch (commandLine.getOptionValue("ss").toLowerCase()) {
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
