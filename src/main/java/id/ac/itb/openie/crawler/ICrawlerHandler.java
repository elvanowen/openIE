package id.ac.itb.openie.crawler;

import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.fortsoft.pf4j.ExtensionPoint;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public interface ICrawlerHandler extends ExtensionPoint {
    public String getPluginName();
    public HashSet<String> getSeedEndpoints();
    public Boolean shouldFollowLink(String link);
    public Pair<String, String> extractContent(String url, String html);
}
