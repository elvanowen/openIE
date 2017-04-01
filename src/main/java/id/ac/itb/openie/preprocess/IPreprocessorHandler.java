package id.ac.itb.openie.preprocess;

import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface IPreprocessorHandler extends ExtensionPoint, Serializable {
    public String getPluginName();
    public HashMap<String, String> getAvailableConfigurations();
    public HashMap<File, String> preprocess(File file, String payload) throws Exception;

    // Hooks
    public void preprocessorWillRun();
    public void preprocessorDidRun();
}
