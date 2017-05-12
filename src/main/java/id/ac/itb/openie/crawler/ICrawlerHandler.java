package id.ac.itb.openie.crawler;

import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface ICrawlerHandler extends ExtensionPoint, Serializable {
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
     * @param url Page of a visited link
     * @param html Content of the page
     * @return List of filename with its extracted content
     */
    public HashMap<String, String> extract(String url, String html);

    /**
     * Hook to be called before crawler will run
     */
    public void crawlerWillRun();

    /**
     * Hook to be called after crawler have run
     */
    public void crawlerDidRun();
}
