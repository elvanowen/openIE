package id.ac.itb.openie;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.crawler.Crawler;
import id.ac.itb.openie.crawler.CrawlerPipeline;
import id.ac.itb.openie.crawler.ICrawlerHandler;
import id.ac.itb.openie.extractor.ExtractorFileReader;
import id.ac.itb.openie.extractor.ExtractorFileWriter;
import id.ac.itb.openie.extractor.ExtractorPipeline;
import id.ac.itb.openie.extractor.ReverbExtractor;
import id.ac.itb.openie.pipeline.OpenIePipeline;
import id.ac.itb.openie.postprocess.PostprocessorFileReader;
import id.ac.itb.openie.postprocess.PostprocessorFileWriter;
import id.ac.itb.openie.postprocess.PostprocessorPipeline;
import id.ac.itb.openie.preprocess.KompasDocumentPreprocessor;
import id.ac.itb.openie.preprocess.PreprocessorFileReader;
import id.ac.itb.openie.preprocess.PreprocessorFileWriter;
import id.ac.itb.openie.preprocess.PreprocessorPipeline;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by elvanowen on 2/9/17.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // Load plugins
        PluginManager pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

//        PluginLoader<ICrawlerHandler> pluginLoader = new PluginLoader<>(ICrawlerHandler.class);
//        List<ICrawlerHandler> crawlerHandlers = pluginLoader.getExtensions();

        System.out.println(System.getProperty("pf4j.pluginsDir", "plugins"));

        HashMap<String, ICrawlerHandler> crawlerPlugin = new HashMap<String, ICrawlerHandler>();
//
        List<ICrawlerHandler> crawlerHandlers = pluginManager.getExtensions(ICrawlerHandler.class);
        for (ICrawlerHandler crawlerHandler : crawlerHandlers) {
            crawlerPlugin.put(crawlerHandler.getPluginName(), crawlerHandler);
        }

        new OpenIePipeline()
                .addPipelineElement(
                        new CrawlerPipeline()
                                .addCrawler(
                                        new Crawler()
                                                .setCrawlerhandler(crawlerPlugin.get("Okezone Crawler"))))
                .addPipelineElement(
                        new PreprocessorPipeline()
                                .addPipelineElement(
                                        new PreprocessorFileReader()
                                                .setReadDirectoryPath(
                                                        new Config()
                                                                .getProperty("CRAWLER_OUTPUT_RELATIVE_PATH")))
                                .addPipelineElement(
                                        new KompasDocumentPreprocessor())
//                                .addPipelineElement(
//                                        new DetikDocumentPreprocessor())
                                .addPipelineElement(
                                        new PreprocessorFileWriter()
                                                .setWriteDirectoryPath(
                                                        new Config()
                                                                .getProperty("PREPROCESSES_OUTPUT_RELATIVE_PATH"))))
                .addPipelineElement(
                        new ExtractorPipeline()
                                .addPipelineElement(
                                        new ExtractorFileReader()
                                                .setReadDirectoryPath(
                                                        new Config()
                                                                .getProperty("PREPROCESSES_OUTPUT_RELATIVE_PATH")))
                                .addPipelineElement(
                                        new ReverbExtractor()
                                )
                                .addPipelineElement(
                                        new ExtractorFileWriter()
                                                .setWriteDirectoryPath(
                                                        new Config()
                                                                .getProperty("EXTRACTIONS_OUTPUT_RELATIVE_PATH"))))
                .addPipelineElement(
                        new PostprocessorPipeline()
                                .addPipelineElement(
                                        new PostprocessorFileReader()
                                            .setReadDirectoryPath(
                                                    new Config()
                                                            .getProperty("EXTRACTIONS_OUTPUT_RELATIVE_PATH")))
                                .addPipelineElement(
                                        new PostprocessorFileWriter()
                                                .setWriteDirectoryPath(
                                                        new Config()
                                                                .getProperty("POSTPROCESSES_OUTPUT_RELATIVE_PATH"))))
                .execute();


//        final File folder = new File(Crawler.CRAWL_SAVE_DIRECTORY_PATH);
//        ArrayList<File> files = getDirectoryFiles(folder);
//
//        BaseExtractor extractor = new BaseExtractor();
//
//        for(File file: files) {
//            extractor.extractFromFile(file);
//        }

//        KompasCrawlerHandler kompasCrawler = new KompasCrawlerHandler();
//        kompasCrawler.setShouldStoreCrawlData(true);
//        kompasCrawler.crawl();

//        DetikCrawlerHandler detikCrawler = new DetikCrawlerHandler();
//        detikCrawler.setShouldStoreCrawlData(true);
//        detikCrawler.crawl();



//        ArrayList<File> files = Utilities.getDirectoryFiles(new File(new ConfigDialog().getProperty("PREPROCESSOR_STORAGE_DIRECTORY")));

//        ReverbExtractor reverbExtractor = new ReverbExtractor();
////        ArrayList<Triple<String, String, String>> relations = reverbExtractor.extractFromFile(files.get(10));
//        ArrayList<Triple<String, String, String>> relations = reverbExtractor.extractFromDirectory(new File(new ConfigDialog().getProperty("PREPROCESSOR_STORAGE_DIRECTORY")));
//
//        for (Triple<String, String, String> relation: relations) {
//            if (relation != null) {
//                System.out.format("Relation: <%s | %s | %s>\n", relation.getLeft(), relation.getMiddle(), relation.getRight());
//            }
//        }

//        for(File file: files) {
//            if (file.getName().contains("detik-com")) {
//                DetikDocumentPreprocessor detikDocumentPreprocessor = new DetikDocumentPreprocessor();
//                detikDocumentPreprocessor.setPreprocessFile(file);
//                detikDocumentPreprocessor.execute();
//            } else if (file.getName().contains("kompas-com")) {
//                KompasDocumentPreprocessor kompasDocumentPreprocessor = new KompasDocumentPreprocessor();
//                kompasDocumentPreprocessor.setPreprocessFile(file);
//                kompasDocumentPreprocessor.execute();
//            }
//        }
    }
}
