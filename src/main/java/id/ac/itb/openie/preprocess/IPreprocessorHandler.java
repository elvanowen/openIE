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
     * @param body String containing original crawled content
     * @param preprocessed String containing preprocessed content from previous preprocessor
     * @return preprocessed body
     * @throws Exception
     */
    public String preprocess(String body, String preprocessed) throws Exception;

    /**
     *
     * @return List of file and its content
     * @throws Exception
     */
    public HashMap<File, String> read() throws Exception;

    /**
     *
     * @param preprocessed String containing original crawled content
     * @throws Exception
     */
    public void write(File file, String preprocessed) throws Exception;

    /**
     * Hook to be called before preprocessor will run
     */
    public void preprocessorWillRun();

    /**
     * Hook to be called before preprocessor have run
     */
    public void preprocessorDidRun();
}
