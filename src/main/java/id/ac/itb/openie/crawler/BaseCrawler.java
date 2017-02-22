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
import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * Created by elvanowen on 2/22/17.
 */
public abstract class BaseCrawler extends WebCrawler {

    private static String crawlStorageDirectoryPath = null;
    private static Pattern filterRegexPattern = Pattern.compile(".*(\\.(css|js|gif|jpeg|jpg|png|mp3|mp3|zip|gz))$");
    private static String internalCrawlerStorageDirectory = new Config().getProperty("INTERNAL_CRAWLER_STORAGE_DIRECTORY");
    private static int numberOfCrawlers = 1;
    private static int maxDepthOfCrawling = 2;
    private static int maxPagesToFetch = 200;
    private static String userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    public BaseCrawler setUserAgentString(String userAgentString) {
        BaseCrawler.userAgentString = userAgentString;
        return this;
    }

    public BaseCrawler setMaxDepthOfCrawling(int maxDepthOfCrawling) {
        BaseCrawler.maxDepthOfCrawling = maxDepthOfCrawling;
        return this;
    }

    public BaseCrawler setNumberOfCrawlers(int numberOfCrawlers) {
        BaseCrawler.numberOfCrawlers = numberOfCrawlers;
        return this;
    }

    public BaseCrawler setMaxPagesToFetch(int maxPagesToFetch) {
        BaseCrawler.maxPagesToFetch = maxPagesToFetch;
        return this;
    }

    public BaseCrawler setFilterRegexPattern(String filterRegexPattern) {
        BaseCrawler.filterRegexPattern = Pattern.compile(filterRegexPattern);
        return this;
    }

    public BaseCrawler setCrawlStorageDirectoryPath(String crawlStorageDirectoryPath) {
        BaseCrawler.crawlStorageDirectoryPath = crawlStorageDirectoryPath;
        return this;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String targetHref = url.getURL().toLowerCase();
        String referringHref = referringPage.getWebURL().getURL().toLowerCase();

//        System.out.println(referringHref + " -> " + targetHref);

        // If url is seed then allow
        for (String seedURL: getSeedEndpoints()) {
            if (targetHref.equalsIgnoreCase(seedURL)) {
                return true;
            }
        }

        if (filterRegexPattern.matcher(targetHref).matches()) {
            return false;
        }

        return shouldFollowLink(targetHref);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println(url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();

            ArrayList<Pair<String, String>> outputs = extractContent(url, html);

            if (BaseCrawler.crawlStorageDirectoryPath != null) {
                for(Pair<String, String> pair: outputs) {
                    Utilities.writeToFile(BaseCrawler.crawlStorageDirectoryPath, pair.getLeft(), pair.getRight());
                }
            }
        }
    }

    public void execute() {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(BaseCrawler.internalCrawlerStorageDirectory);
        config.setMaxDepthOfCrawling(BaseCrawler.maxDepthOfCrawling);
        config.setMaxPagesToFetch(BaseCrawler.maxPagesToFetch);
        config.setUserAgentString(BaseCrawler.userAgentString);

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
            for (String seed: getSeedEndpoints()) {
                controller.addSeed(seed);
            }

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(this.getClass(), BaseCrawler.numberOfCrawlers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract HashSet<String> getSeedEndpoints();

    protected abstract Boolean shouldFollowLink(String link);

    protected abstract ArrayList<Pair<String, String>> extractContent(String url, String html);
}
