package classes;

import id.ac.itb.openie.crawler.ICrawlerHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public class OkezoneCrawlerHandlerPlugin extends Plugin {

    public OkezoneCrawlerHandlerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class OkezoneCrawlerHandler implements ICrawlerHandler {

        public String getPluginName() {
            return "Okezone Crawler";
        }

        public HashSet<String> getSeedEndpoints() {
            HashSet<String> urls = new HashSet<String>();

            urls.add("http://www.okezone.com");

            return urls;
        }

        public Boolean shouldFollowLink(String link) {
            return link.contains("okezone.com/read");
        }

        public Pair<String, String> extractContent(String url, String html) {
            Document doc = Jsoup.parse(html);

            ArrayList<String> paragraphs = new ArrayList<String>();

            Elements contents = doc.select("#contentx p");
            for (Element content : contents) {
                paragraphs.add(content.text());
            }

            if (paragraphs.size() > 0) {
                return Pair.of(url, StringUtils.join(paragraphs, " "));
            } else {
                return null;
            }
        }
    }
}
