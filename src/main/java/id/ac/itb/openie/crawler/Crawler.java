package id.ac.itb.openie.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by elvanowen on 2/22/17.
 */
public class Crawler extends WebCrawler {

    private static String crawlStorageDirectoryPath = null;
    private static Pattern filterRegexPattern = Pattern.compile(".*(\\.(css|js|gif|jpeg|jpg|png|mp3|mp3|zip|gz))$");
    private static String internalCrawlerStorageDirectory = new Config().getProperty("INTERNAL_CRAWLER_STORAGE_DIRECTORY");
    private static int numberOfCrawlers = 1;
    private static int maxDepthOfCrawling = 2;
    private static int maxPagesToFetch = 200;
    private static String userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
    private static ICrawlerHandler crawlerHandler = null;
    private static int totalDocumentCrawled = 0;

    public Crawler setUserAgentString(String userAgentString) {
        Crawler.userAgentString = userAgentString;
        return this;
    }

    public Crawler setMaxDepthOfCrawling(int maxDepthOfCrawling) {
        Crawler.maxDepthOfCrawling = maxDepthOfCrawling;
        return this;
    }

    public Crawler setNumberOfCrawlers(int numberOfCrawlers) {
        Crawler.numberOfCrawlers = numberOfCrawlers;
        return this;
    }

    public Crawler setMaxPagesToFetch(int maxPagesToFetch) {
        Crawler.maxPagesToFetch = maxPagesToFetch;
        return this;
    }

    public Crawler setFilterRegexPattern(String filterRegexPattern) {
        Crawler.filterRegexPattern = Pattern.compile(filterRegexPattern);
        return this;
    }

    public Crawler setCrawlStorageDirectoryPath(String crawlStorageDirectoryPath) {
        Crawler.crawlStorageDirectoryPath = crawlStorageDirectoryPath;
        return this;
    }

    public Crawler setCrawlerhandler(ICrawlerHandler crawlerhandler) {
        Crawler.crawlerHandler = crawlerhandler;
        return this;
    }

    public int getTotalDocumentCrawled() {
        return totalDocumentCrawled;
    }

    @Override
    public void onStart() {
        super.onStart();
        crawlerHandler.crawlerWillRun();
    }

    @Override
    public void onBeforeExit() {
        super.onBeforeExit();
        crawlerHandler.crawlerDidRun();
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String targetHref = url.getURL().toLowerCase();
        String referringHref = referringPage.getWebURL().getURL().toLowerCase();

//        System.out.println(referringHref + " -> " + targetHref);

        // If url is seed then allow
        for (String seedURL: crawlerHandler.getCrawlerStartingUrls()) {
            if (targetHref.equalsIgnoreCase(seedURL)) {
                return true;
            }
        }

        if (filterRegexPattern.matcher(targetHref).matches()) {
            return false;
        }

        return crawlerHandler.shouldCrawlerFollowLink(targetHref);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println(url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();

            HashMap<String, String> fileContentMappings = crawlerHandler.extractContentFromHTML(url, html);

            Iterator<Map.Entry<String, String>> it = fileContentMappings.entrySet().iterator();

            if (it.hasNext()) totalDocumentCrawled++;

            while (it.hasNext()) {
                Map.Entry<String, String> pair = it.next();

                writeToFile(pair.getKey(), pair.getValue());

                it.remove(); // avoids a ConcurrentModificationException
            }
        }
    }

    protected void writeToFile(String url, String content) {
        if (Crawler.crawlStorageDirectoryPath != null) {
            Utilities.writeToFile(Crawler.crawlStorageDirectoryPath, url, content);
        }
    }

    public void execute() throws Exception {

        if (crawlerHandler == null) {
            throw new Exception("No Crawler Handler specified");
        }

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(Crawler.internalCrawlerStorageDirectory);
        config.setMaxDepthOfCrawling(Crawler.maxDepthOfCrawling);
        config.setMaxPagesToFetch(Crawler.maxPagesToFetch);
        config.setUserAgentString(Crawler.userAgentString);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = null;

        try {
            controller = new CrawlController(config, pageFetcher, robotstxtServer);

            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
            for (String seed: crawlerHandler.getCrawlerStartingUrls()) {
                controller.addSeed(seed);
            }

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(this.getClass(), Crawler.numberOfCrawlers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
