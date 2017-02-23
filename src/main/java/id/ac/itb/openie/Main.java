package id.ac.itb.openie;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.crawler.CrawlerPipeline;
import id.ac.itb.openie.crawler.KompasCrawler;
import id.ac.itb.openie.extractor.*;
import id.ac.itb.openie.pipeline.OpenIePipeline;
import id.ac.itb.openie.postprocess.PostprocessorFileReader;
import id.ac.itb.openie.postprocess.PostprocessorFileWriter;
import id.ac.itb.openie.postprocess.PostprocessorPipeline;
import id.ac.itb.openie.preprocess.*;
import id.ac.itb.openie.preprocess.PreprocessorFileWriter;

/**
 * Created by elvanowen on 2/9/17.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        new OpenIePipeline()
                .addPipelineElement(
                        new CrawlerPipeline()
                                .addCrawler(
                                        new KompasCrawler()
                                                .setMaxPagesToFetch(5)
                                                .setCrawlStorageDirectoryPath(new Config().getProperty("CRAWLER_STORAGE_DIRECTORY"))))
                .addPipelineElement(
                        new PreprocessorPipeline()
                                .addPipelineElement(
                                        new PreprocessorFileReader()
                                                .setReadDirectoryPath(
                                                        new Config()
                                                                .getProperty("CRAWLER_STORAGE_DIRECTORY")))
                                .addPipelineElement(
                                        new KompasDocumentPreprocessor())
//                                .addPipelineElement(
//                                        new DetikDocumentPreprocessor())
                                .addPipelineElement(
                                        new PreprocessorFileWriter()
                                                .setWriteDirectoryPath(
                                                        new Config()
                                                                .getProperty("PREPROCESSOR_STORAGE_DIRECTORY"))))
                .addPipelineElement(
                        new ExtractorPipeline()
                                .addPipelineElement(
                                        new ExtractorFileReader()
                                                .setReadDirectoryPath(
                                                        new Config()
                                                                .getProperty("PREPROCESSOR_STORAGE_DIRECTORY")))
                                .addPipelineElement(
                                        new ReverbExtractor()
                                )
                                .addPipelineElement(
                                        new ExtractorFileWriter()
                                                .setWriteDirectoryPath(
                                                        new Config()
                                                                .getProperty("EXTRACTIONS_STORAGE_DIRECTORY"))))
                .addPipelineElement(
                        new PostprocessorPipeline()
                                .addPipelineElement(
                                        new PostprocessorFileReader()
                                            .setReadDirectoryPath(
                                                    new Config()
                                                            .getProperty("EXTRACTIONS_STORAGE_DIRECTORY")))
                                .addPipelineElement(
                                        new PostprocessorFileWriter()
                                                .setWriteDirectoryPath(
                                                        new Config()
                                                                .getProperty("POSTPROCESSOR_STORAGE_DIRECTORY"))))
                .execute();


//        final File folder = new File(Crawler.CRAWL_SAVE_DIRECTORY_PATH);
//        ArrayList<File> files = getDirectoryFiles(folder);
//
//        BaseExtractor extractor = new BaseExtractor();
//
//        for(File file: files) {
//            extractor.extractFromFile(file);
//        }

//        KompasCrawler kompasCrawler = new KompasCrawler();
//        kompasCrawler.setShouldStoreCrawlData(true);
//        kompasCrawler.crawl();

//        DetikCrawler detikCrawler = new DetikCrawler();
//        detikCrawler.setShouldStoreCrawlData(true);
//        detikCrawler.crawl();



//        ArrayList<File> files = Utilities.getDirectoryFiles(new File(new Config().getProperty("PREPROCESSOR_STORAGE_DIRECTORY")));

//        ReverbExtractor reverbExtractor = new ReverbExtractor();
////        ArrayList<Triple<String, String, String>> relations = reverbExtractor.extractFromFile(files.get(10));
//        ArrayList<Triple<String, String, String>> relations = reverbExtractor.extractFromDirectory(new File(new Config().getProperty("PREPROCESSOR_STORAGE_DIRECTORY")));
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
