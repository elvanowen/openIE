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
     * @param file File pointing to the original extracted relations document
     * @param relations List of relation postprocessed from previous postprocessor
     * @return List of file with its postprocessed relations
     * @throws Exception
     */
    public HashMap<File, Relations> postprocess(File file, Relations relations) throws Exception;

    /**
     * Hook to be called before postprocessor will run
     */
    public void postprocessorWillRun();

    /**
     * Hook to be called before postprocessor have run
     */
    public void postprocessorDidRun();
}
