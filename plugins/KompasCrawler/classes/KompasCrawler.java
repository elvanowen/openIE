package classes;

import id.ac.itb.openie.crawler.ICrawlerHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public class KompasCrawler extends Plugin {

    public KompasCrawler(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class KompasCrawlerHandler implements ICrawlerHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Kompas.com";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
//            availableConfigurations.putIfAbsent("Output Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("CRAWLER_OUTPUT_RELATIVE_PATH"));
            availableConfigurations.putIfAbsent("Max Pages to Fetch", "50");
            availableConfigurations.putIfAbsent("Max Depth of Crawling", "100");
            availableConfigurations.putIfAbsent("Regex Filter Pattern", ".*(\\.(css|js|gif|jpeg|jpg|png|mp3|mp3|zip|gz))$");
            availableConfigurations.putIfAbsent("User Agent String", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

            return availableConfigurations;
        }

        public HashSet<String> getCrawlerStartingUrls() {
            HashSet<String> urls = new HashSet<String>();

            urls.add("http://www.kompas.com");

            return urls;
        }

        public Boolean shouldCrawlerFollowLink(String link) {
            return link.contains("kompas.com/read");
        }

        public HashMap<String, String> extract(String url, String html) {
            HashMap<String, String> output = new HashMap<>();
            Document doc = Jsoup.parse(html);

            Elements contents = doc.getElementsByClass("read__content");
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
