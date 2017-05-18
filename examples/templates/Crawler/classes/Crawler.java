package classes;

import id.ac.itb.openie.crawler.ICrawlerExtensionHandler;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.HashMap;
import java.util.HashSet;

public class Crawler extends Plugin {

    public Crawler(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class CrawlerHandler extends ICrawlerExtensionHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            /* Return crawler name e.g. `Kompas.com` */
            return "";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            /* List of configurations available in UI interface */
            availableConfigurations.putIfAbsent("Max Pages to Fetch", "50");
            availableConfigurations.putIfAbsent("Max Depth of Crawling", "100");
            availableConfigurations.putIfAbsent("Regex Filter Pattern", ".*(\\.(css|js|gif|jpeg|jpg|png|mp3|mp3|zip|gz))$");
            availableConfigurations.putIfAbsent("User Agent String", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

            return availableConfigurations;
        }

        @Override
        public void setAvailableConfigurations(String key, String value) {
            availableConfigurations.put(key, value);
        }

        public HashSet<String> getCrawlerStartingUrls() {
            HashSet<String> urls = new HashSet<String>();

            // urls.add("https://www.example.com");
            // urls.add("https://subdomain.example.com");

            return urls;
        }

        public Boolean shouldCrawlerFollowLink(String link) {
            // return  link.contains("example.com/valid/path/") ||
            //         link.contains("example.com/another/valid/path");

            return false;
        }

        public String extract(String url, String response) {
            /* TODO: Extract document from response */
            /* Parse HTML response */
            // Document doc = Jsoup.parse(response);

            /* Extract content using by className */
            // Elements contents = doc.getElementsByClass("read__content");
            // return contents.get(0).text();

            return "";
        }

        public void crawlerWillRun() {
            /* TODO: before crawler start crawling */
        }

        public void crawlerDidRun() {
            /* TODO: after crawler finish crawling */
        }
    }
}
