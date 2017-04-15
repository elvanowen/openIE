package id.ac.itb.openie.crawler;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.plugins.PluginLoader;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/23/17.
 */
public class CrawlerPipeline implements IOpenIePipelineElement {

    private ArrayList<ICrawlerPipelineElement> crawlerPipelineElements = new ArrayList<>();
    private int totalProcessedCrawler = 0;
    private ICrawlerPipelineElement currentlyRunningCrawler = null;
    private ICrawlerPipelineHook crawlerPipelineHook = null;

    public CrawlerPipeline addPipelineElement(ICrawlerPipelineElement crawlerPipelineElement) {
        this.crawlerPipelineElements.add(crawlerPipelineElement);
        return this;
    }

    public ArrayList<ICrawlerPipelineElement> getCrawlerPipelineElements() {
        return this.crawlerPipelineElements;
    }

    public int getNumberOfCrawlers() {
        int n = 0;

        for (ICrawlerPipelineElement crawlerPipelineElement: crawlerPipelineElements) {
            if (((Crawler)crawlerPipelineElement).getCrawlerhandler().getPluginName().equalsIgnoreCase("Crawler File Writer")) {
                continue;
            } else {
                n++;
            }
        }

        return n;
    }

    private void addDefaultWriter() {
        if (crawlerPipelineElements.size() > 0) {
            PluginLoader pluginLoader = new PluginLoader();
            pluginLoader.registerAvailableExtensions(ICrawlerHandler.class);

            for (Object iCrawlerHandler: pluginLoader.getExtensions(ICrawlerHandler.class)) {
                ICrawlerHandler crawlerHandler = (ICrawlerHandler) iCrawlerHandler;
                String pluginName = crawlerHandler.getPluginName();

                if (pluginName.equalsIgnoreCase("Crawler File Writer")) {
                    Crawler crawler = new Crawler().setCrawlerhandler(crawlerHandler);
                    crawlerPipelineElements.add(crawler);
                }
            }
        }
    }

    private void setOutputDirectoriesToAllCrawlers() {

        ArrayList<String> outputDirs = new ArrayList<>();
        ArrayList<Object> toBeRemoved = new ArrayList<>();

        // Keep output directories
        for (ICrawlerPipelineElement crawlerPipelineElement: crawlerPipelineElements) {
            if (((Crawler) crawlerPipelineElement).getCrawlerhandler().getPluginName().equalsIgnoreCase("Crawler File Writer")) {
                outputDirs.add(((Crawler) crawlerPipelineElement).getCrawlerhandler().getAvailableConfigurations().get("Output Directory"));
                toBeRemoved.add(crawlerPipelineElement);
            }
        }

        // Remove File Writer
        for (Object crawlerPipelineElement: toBeRemoved) {
            crawlerPipelineElements.remove(crawlerPipelineElement);
        }

        // Set output directories to all crawlers
        outputDirs.forEach(Crawler::addOutputDirectory);
    }

    @Override
    public void willExecute() {
        if (this.getNumberOfCrawlers() > 0) {
            crawlerPipelineHook.willExecute();
        }
    }

    public void execute() throws Exception {
        System.out.println("Running crawler pipeline...");

        // Make sure previous output directories are cleared
        Crawler.clearOutputDirectory();

        addDefaultWriter();
        setOutputDirectoriesToAllCrawlers();

        for (ICrawlerPipelineElement crawlerPipelineElement: crawlerPipelineElements) {
            this.totalProcessedCrawler++;
            currentlyRunningCrawler = crawlerPipelineElement;
            crawlerPipelineElement.execute();
        }
    }

    @Override
    public void didExecute() {
        if (this.getNumberOfCrawlers() > 0) {
            crawlerPipelineHook.didExecute();
        }
    }

    public int getTotalProcessedCrawler() {
        return this.totalProcessedCrawler;
    }

    public ICrawlerPipelineElement getCurrentlyRunningCrawler() {
        return this.currentlyRunningCrawler;
    }

    public CrawlerPipeline setCrawlerPipelineHook(ICrawlerPipelineHook crawlerPipelineHook) {
        this.crawlerPipelineHook = crawlerPipelineHook;
        return this;
    }
}
