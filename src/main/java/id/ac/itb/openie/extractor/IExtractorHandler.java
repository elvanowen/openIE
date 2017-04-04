package id.ac.itb.openie.extractor;

import id.ac.itb.openie.preprocess.IPreprocessorPipelineElement;
import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;
import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface IExtractorHandler extends ExtensionPoint, Serializable {
    public String getPluginName();
    public HashMap<String, String> getAvailableConfigurations();
    public HashMap<File, Pair<String, Relations>> extract(File file, String payload, Relations relations) throws Exception;

    // Hooks
    public void extractorWillRun();
    public void extractorDidRun();
}
