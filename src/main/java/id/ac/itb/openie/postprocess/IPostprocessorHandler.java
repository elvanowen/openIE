package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.relation.Relations;
import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface IPostprocessorHandler extends ExtensionPoint, Serializable {
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
     * @param relations List of originally extracted relation from extractor
     * @param postprocessed List of relation postprocessed from previous postprocessor
     * @return postprocessed relations
     * @throws Exception
     */
    public Relations postprocess(Relations relations, Relations postprocessed) throws Exception;

    /**
     *
     * @return List of file and its content
     * @throws Exception
     */
    public HashMap<File, Relations> read() throws Exception;

    /**
     *
     * @param postprocessed String containing original crawled content
     * @throws Exception
     */
    public void write(File file, Relations postprocessed) throws Exception;

    /**
     * Hook to be called before postprocessor will run
     */
    public void postprocessorWillRun();

    /**
     * Hook to be called before postprocessor have run
     */
    public void postprocessorDidRun();
}
