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
    public String getPluginName();
    public HashMap<String, String> getAvailableConfigurations();
    public HashMap<File, Relations> postprocess(File file, Relations relations) throws Exception;

    // Hooks
    public void postprocessorWillRun();
    public void postprocessorDidRun();
}
