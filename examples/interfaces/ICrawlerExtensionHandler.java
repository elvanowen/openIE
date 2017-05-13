/**
 * This is a simplified schema that shows each methods a user has to implement
 */
public abstract class ICrawlerExtensionHandler {
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
     * @return Set of main website urls to start crawling
     */
    public HashSet<String> getCrawlerStartingUrls();

    /**
     *
     * @param link The link to be visited
     * @return True if crawler should visit @param link
     */
    public Boolean shouldCrawlerFollowLink(String link);

    /**
     *
     * @param url URL of a visited link
     * @param response response from @url (could be text file or json depends on the crawler)
     * @return extracted content
     */
    public String extract(String url, String response);

    /**
     * Hook to be called before crawler will run
     */
    public void crawlerWillRun();

    /**
     * Hook to be called after crawler have run
     */
    public void crawlerDidRun();
}
