package id.ac.itb.openie.extractor;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.config.Config;
import id.ac.itb.nlp.SentenceTokenizer;
import id.ac.itb.openie.preprocess.IPreprocessorHandler;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/9/17.
 */
public class Extractor implements IExtractorPipelineElement {

    private IExtractorHandler extractorHandler;

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
                return "File Reader: " + inputDirectory;
            } else if (outputDirectory != null) {
                return "File Writer: " + outputDirectory;
            }
        }

        return this.getExtractorHandler().getPluginName();
    }

    @Override
    public HashMap<File, Pair<String, Relations>> execute(File file, String payload, Relations relations) throws Exception {
        return this.getExtractorHandler().extract(file, payload, relations);
    }
}
