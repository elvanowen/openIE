package id.ac.itb.openie.preprocess;

import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface IPreprocessorHandler extends ExtensionPoint {
    public String getPluginName();
    public HashMap<File, String> execute(File file, String payload) throws Exception;
    public String toString();

    // Hooks
    public void preprocessorWillRun();
    public void preprocessorDidRun();
}
