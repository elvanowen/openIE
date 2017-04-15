package id.ac.itb.openie.extractor;

import id.ac.itb.openie.relation.Relations;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/9/17.
 */
public class Extractor implements IExtractorPipelineElement {

    private IExtractorHandler extractorHandler;
    private int totalDocumentExtracted = 0;

    public IExtractorHandler getExtractorHandler() {
        return extractorHandler;
    }

    public Extractor setExtractorHandler(IExtractorHandler extractorHandler) {
        this.extractorHandler = extractorHandler;
        return this;
    }

    public String toString() {
        if (getExtractorHandler().getAvailableConfigurations() != null) {
            String inputDirectory = getExtractorHandler().getAvailableConfigurations().get("Input Directory");
            String outputDirectory = getExtractorHandler().getAvailableConfigurations().get("Output Directory");

            if (inputDirectory != null) {
                return this.getExtractorHandler().getPluginName() + " : " + inputDirectory;
            } else if (outputDirectory != null) {
                return this.getExtractorHandler().getPluginName() + " : "  + outputDirectory;
            }
        }

        return this.getExtractorHandler().getPluginName();
    }

    @Override
    public HashMap<File, Pair<String, Relations>> execute(File file, String payload, Relations relations) throws Exception {
        return this.getExtractorHandler().extract(file, payload, relations);
    }

    public int getTotalDocumentExtracted() {
        return totalDocumentExtracted;
    }

    public Extractor setTotalDocumentExtracted(int totalDocumentExtracted) {
        this.totalDocumentExtracted = totalDocumentExtracted;
        return this;
    }
}
