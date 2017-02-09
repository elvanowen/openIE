package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.crawler.Crawler;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
public class Main {

    private static ArrayList<File> getDirectoryFiles(final File folder) {

        ArrayList<File> files = new ArrayList<File>();

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                files.addAll(getDirectoryFiles(fileEntry));
            } else {
                files.add(new File(Crawler.CRAWL_SAVE_DIRECTORY_PATH + fileEntry.getName()));
            }
        }

        return files;
    }

    public static void main(String[] args) throws Exception {

        final File folder = new File(Crawler.CRAWL_SAVE_DIRECTORY_PATH);
        ArrayList<File> files = getDirectoryFiles(folder);

        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        ArrayList<String> sentences = sentenceTokenizer.tokenizeFiles(files);

        System.out.println(sentences);
    }
}
