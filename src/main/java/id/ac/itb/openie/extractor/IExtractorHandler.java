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
     * @param document String containing original preprocessed file content
     * @param extracted List of relation extracted from previous extractor
     * @return extracted relations
     * @throws Exception
     */
    public Relations extract(File file, String document, Relations extracted) throws Exception;

    /**
     *
     * @return List of file and its content
     * @throws Exception
     */
    public HashMap<File, Pair<String, Relations>> read() throws Exception;

    /**
     *
     * @param extracted String containing original crawled content
     * @throws Exception
     */
    public void write(File file, Relations extracted) throws Exception;

    /**
     * Hook to be called before extractor will run
     */
    public void extractorWillRun();

    /**
     * Hook to be called before extractor have run
     */
    public void extractorDidRun();
}
