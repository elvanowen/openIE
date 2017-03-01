package id.ac.itb.openie.crawler;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/23/17.
 */
public class CrawlerPipeline implements IOpenIePipelineElement {

    private ArrayList<Crawler> crawlers = new ArrayList<Crawler>();

    public CrawlerPipeline addCrawler(Crawler crawler) {
        this.crawlers.add(crawler);
        return this;
    }

    public ArrayList<Crawler> getCrawlers() {
        System.out.println("Accessing getCrawlers");
        return this.crawlers;
    }

    public void execute() throws Exception {
        System.out.println("Running crawler pipeline...");

        for (Crawler crawler: crawlers) {
            crawler.execute();
        }
    }
}
