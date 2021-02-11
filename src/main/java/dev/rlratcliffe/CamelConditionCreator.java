package dev.rlratcliffe;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.processor.Pipeline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelConditionCreator {

    private Processor currentProcessor;
    private Exchange exchange;

    public CamelConditionCreator(Exchange exchange, Processor currentProcessor) {
        this.exchange = exchange;
        this.currentProcessor = currentProcessor;
    }

    public boolean whenAtIndex(String route, int index) {

        boolean matched = false;

        boolean isNotPipeline = !(this.currentProcessor instanceof Pipeline);
        if (isNotPipeline) {
            String requestedProcessor = exchange.getContext().getRoute(route).getEventDrivenProcessors().get(index).toString();

            String idOfRequestedProcessor = parseIdFromProcessorString(requestedProcessor);

            String currentProcessorId = parseIdFromProcessorString(this.currentProcessor.toString());
            matched = currentProcessorId.equals(idOfRequestedProcessor);
        }

        return matched;
    }

    public boolean whenAtId(String id) {
        boolean matched = false;

        boolean isNotPipeline = !(this.currentProcessor instanceof Pipeline);
        if (isNotPipeline) {
            String currentProcessorId = parseIdFromProcessorString(this.currentProcessor.toString());
            matched = currentProcessorId.equals(id);
        }

        return matched;
    }

    String parseIdFromProcessorString(String processorString) {
            String id = "";
            Pattern bracketRegex = Pattern.compile("\\[(.*?)\\]");

            Matcher bracketsInProcessor = bracketRegex.matcher(processorString);

            if (bracketsInProcessor.find()) {
                id = bracketsInProcessor.group(1)
                        .replace("[", "")
                        .replace("]", "");
            }

            return id;
    }

}

