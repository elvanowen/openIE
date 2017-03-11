package id.ac.itb.openie.crawler;

import id.ac.itb.openie.config.Config;

import java.util.regex.Pattern;

/**
 * Created by elvanowen on 3/2/17.
 */
public class CrawlerConfig {
    private String crawlStorageDirectoryPath = new Config().getProperty("CRAWLER_STORAGE_DIRECTORY");
    private Pattern filterRegexPattern = Pattern.compile(".*(\\.(css|js|gif|jpeg|jpg|png|mp3|mp3|zip|gz))$");
    private String internalCrawlerStorageDirectory = new Config().getProperty("INTERNAL_CRAWLER_STORAGE_DIRECTORY");
    private int numberOfCrawlers = 1;
    private int maxDepthOfCrawling = 5;
    private int maxPagesToFetch = 200;
    private String userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    public CrawlerConfig setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
        return this;
    }

    public CrawlerConfig setMaxDepthOfCrawling(int maxDepthOfCrawling) {
        this.maxDepthOfCrawling = maxDepthOfCrawling;
        return this;
    }

    public CrawlerConfig setNumberOfCrawlers(int numberOfCrawlers) {
        this.numberOfCrawlers = numberOfCrawlers;
        return this;
    }

    public CrawlerConfig setMaxPagesToFetch(int maxPagesToFetch) {
        this.maxPagesToFetch = maxPagesToFetch;
        return this;
    }

    public CrawlerConfig setFilterRegexPattern(String filterRegexPattern) {
        this.filterRegexPattern = Pattern.compile(filterRegexPattern);
        return this;
    }

    public CrawlerConfig setCrawlStorageDirectoryPath(String crawlStorageDirectoryPath) {
        this.crawlStorageDirectoryPath = crawlStorageDirectoryPath;
        return this;
    }

    public String getCrawlStorageDirectoryPath() {
        return crawlStorageDirectoryPath;
    }

    public Pattern getFilterRegexPattern() {
        return filterRegexPattern;
    }

    public int getNumberOfCrawlers() {
        return numberOfCrawlers;
    }

    public int getMaxDepthOfCrawling() {
        return maxDepthOfCrawling;
    }

    public int getMaxPagesToFetch() {
        return maxPagesToFetch;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public String getInternalCrawlerStorageDirectory() {
        return internalCrawlerStorageDirectory;
    }
}
