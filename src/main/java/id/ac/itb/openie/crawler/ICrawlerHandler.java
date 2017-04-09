package id.ac.itb.openie.crawler;

import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface ICrawlerHandler extends ExtensionPoint, Serializable {
    public String getPluginName();
    public HashMap<String, String> getAvailableConfigurations();
    public HashSet<String> getCrawlerStartingUrls();
    public Boolean shouldCrawlerFollowLink(String link);
    public HashMap<String, String> extract(String url, String html);

    // Hooks
    public void crawlerWillRun();
    public void crawlerDidRun();
}
