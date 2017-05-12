package id.ac.itb.openie.preprocess;

import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface IPreprocessorHandler extends ExtensionPoint, Serializable {
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

    public HashMap<String, String> setAvailableConfigurations(String key, String value);
    /**
     *
     * @param file File pointing to the original crawled document
     * @param payload String containing preprocessed content from previous preprocessor
     * @return List of file with its preprocessed payload
     * @throws Exception
     */
    public HashMap<File, String> preprocess(File file, String payload) throws Exception;

    /**
     * Hook to be called before preprocessor will run
     */
    public void preprocessorWillRun();

    /**
     * Hook to be called before preprocessor have run
     */
    public void preprocessorDidRun();
}
