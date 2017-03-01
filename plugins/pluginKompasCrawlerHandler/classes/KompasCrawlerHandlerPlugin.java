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
import java.util.HashMap;
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

        public HashSet<String> getCrawlerStartingUrls() {
            HashSet<String> urls = new HashSet<String>();

            urls.add("http://www.kompas.com");

            return urls;
        }

        public Boolean shouldCrawlerFollowLink(String link) {
            return link.contains("kompas.com/read");
        }

        public HashMap<String, String> extractContentFromHTML(String url, String html) {
//            ArrayList<Pair<String, String>> output = new ArrayList<Pair<String, String>>();
            HashMap<String, String> output = new HashMap<>();
            Document doc = Jsoup.parse(html);

            Elements contents = doc.getElementsByClass("kcm-read-text");
            for (Element content : contents) {
                String contentText = content.text();

                output.put(url, contentText);
            }

            return output;
        }

        public String toString() {
            return this.getPluginName();
        }

        public void crawlerWillRun() {
            System.out.println(this.getPluginName() + " will run..");
        }

        public void crawlerDidRun() {
            System.out.println(this.getPluginName() + " did run..");
        }
    }
}
