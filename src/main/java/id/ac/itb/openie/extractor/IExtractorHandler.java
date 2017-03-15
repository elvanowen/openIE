package id.ac.itb.openie.extractor;

import id.ac.itb.openie.preprocess.IPreprocessorPipelineElement;
import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;
import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface IExtractorHandler extends IExtractorPipelineElement, ExtensionPoint {
    public String getPluginName();
    public HashMap<File, Pair<String, Relations>> execute(File file, String payload, Relations relations) throws Exception;
    public String toString();

    // Hooks
    public void extractorWillRun();
    public void extractorDidRun();
}
