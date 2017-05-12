package id.ac.itb.openie.extractor;

import id.ac.itb.openie.relation.Relations;
import org.apache.commons.lang3.tuple.Pair;
import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface IExtractorHandler extends ExtensionPoint, Serializable {
    /**
     *
     * @return Plugin name
     */
    public String getPluginName();

    /**
     *
     * @return List of configuration name with its default value
     */
    public HashMap<String, String> getAvailableConfigurations();

    /**
     *
     * @param file File pointing to the original preprocessed document
     * @param payload String containing the content of the file
     * @param relations List of relation extracted from previous extractor
     * @return List of file with its preprocessed payload and the extracted relations
     * @throws Exception
     */
    public HashMap<File, Pair<String, Relations>> extract(File file, String payload, Relations relations) throws Exception;

    /**
     * Hook to be called before extractor will run
     */
    public void extractorWillRun();

    /**
     * Hook to be called before extractor have run
     */
    public void extractorDidRun();
}
