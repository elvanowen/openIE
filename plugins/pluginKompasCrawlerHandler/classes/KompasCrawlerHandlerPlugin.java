package classes;

import id.ac.itb.openie.crawler.ICrawlerHandler;
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
public class KompasCrawlerHandlerPlugin extends Plugin {

    public KompasCrawlerHandlerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class OkezoneCrawlerHandler implements ICrawlerHandler {

        public String getPluginName() {
            return "Kompas Crawler";
        }

        public HashSet<String> getSeedEndpoints() {
            HashSet<String> urls = new HashSet<String>();

            urls.add("http://www.kompas.com");

            return urls;
        }

        public Boolean shouldFollowLink(String link) {
            return link.contains("kompas.com/read");
        }

        public Pair<String, String> extractContent(String url, String html) {
            ArrayList<Pair<String, String>> output = new ArrayList<Pair<String, String>>();
            Document doc = Jsoup.parse(html);

            Elements contents = doc.getElementsByClass("kcm-read-text");
            for (Element content : contents) {
                String contentText = content.text();

                return Pair.of(url, contentText);
            }

            return null;
        }
    }
}
