package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.crawler.ICrawlerHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public class DetikCrawler extends Plugin {

    public DetikCrawler(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class DetikCrawlerHandler implements ICrawlerHandler {
        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Detik.com";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
//            availableConfigurations.putIfAbsent("Output Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("CRAWLER_OUTPUT_RELATIVE_PATH"));
            availableConfigurations.putIfAbsent("Max Pages to Fetch", "50");
            availableConfigurations.putIfAbsent("Max Depth of Crawling", "30");
            availableConfigurations.putIfAbsent("Regex Filter Pattern", ".*(\\.(css|js|gif|jpeg|jpg|png|mp3|mp3|zip|gz))$");
            availableConfigurations.putIfAbsent("User Agent String", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

            return availableConfigurations;
        }

        public HashSet<String> getCrawlerStartingUrls() {
            HashSet<String> urls = new HashSet<String>();

            urls.add("https://www.detik.com");
            urls.add("https://finance.detik.com");
            urls.add("https://news.detik.com");
            urls.add("https://wolipop.detik.com");
            urls.add("https://health.detik.com");
            urls.add("https://hot.detik.com");
            urls.add("http://travel.detik.com");

            return urls;
        }

        public Boolean shouldCrawlerFollowLink(String link) {
            return  link.contains("detik.com/read/") ||
                    link.contains("detik.com/berita/") ||
                    link.contains("detik.com/comment/") ||
                    link.contains("detik.com/sepakbola/") ||
                    link.contains("detik.com/celeb/") ||
                    link.contains("detik.com/berita-ekonomi-bisnis/");
        }

        public HashMap<String, String> extractContentFromHTML(String url, String html) {
            Document doc = Jsoup.parse(html);
            HashMap<String, String> output = new HashMap<>();

            Elements contents = doc.getElementsByClass("detail_text");
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
