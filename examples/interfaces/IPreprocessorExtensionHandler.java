/**
 * This is a simplified schema that shows each methods a user has to implement
 */
public abstract class IPreprocessorExtensionHandler implements IPreprocessorHandler {
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
     * @param key Configuration name
     * @param value Configuration value
     */
    public void setAvailableConfigurations(String key, String value);

    /**
     *
     * @param body String containing original crawled content
     * @param preprocessed String containing preprocessed content from previous preprocessor
     * @return preprocessed body
     * @throws Exception
     */
    public String preprocess(String body, String preprocessed) throws Exception;

    /**
     * Hook to be called before preprocessor will run
     */
    public void preprocessorWillRun();

    /**
     * Hook to be called before preprocessor have run
     */
    public void preprocessorDidRun();
}
