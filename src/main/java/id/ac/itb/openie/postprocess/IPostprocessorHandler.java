package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;
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
