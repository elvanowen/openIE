/**
 * This is a simplified schema that shows each methods a user has to implement
 */
public abstract class IPostprocessorExtensionHandler implements IPostprocessorHandler {
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
     * @param relations List of originally extracted relation from extractor
     * @param postprocessed List of relation postprocessed from previous postprocessor
     * @return postprocessed relations
     * @throws Exception
     */
    public Relations postprocess(Relations relations, Relations postprocessed) throws Exception;

    /**
     * Hook to be called before postprocessor will run
     */
    public void postprocessorWillRun();

    /**
     * Hook to be called before postprocessor have run
     */
    public void postprocessorDidRun();
}
