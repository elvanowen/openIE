package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.crawler.ICrawlerHandler;
import id.ac.itb.openie.preprocess.IPreprocessorHandler;
import id.ac.itb.openie.utils.Utilities;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public class CrawlerFileWriter extends Plugin {

    public CrawlerFileWriter(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class CrawlerFileWriterHandler implements ICrawlerHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "File Writer";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            availableConfigurations.putIfAbsent("Output Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("CRAWLER_OUTPUT_RELATIVE_PATH"));

            return availableConfigurations;
        }

        @Override
        public HashSet<String> getCrawlerStartingUrls() {
            return null;
        }

        @Override
        public Boolean shouldCrawlerFollowLink(String link) {
            return null;
        }

        @Override
        public HashMap<String, String> extractContentFromHTML(String url, String html) {
            return null;
        }

        @Override
        public void crawlerWillRun() {

        }

        @Override
        public void crawlerDidRun() {

        }

        public String toString() {
            return this.getPluginName();
        }
    }
}
