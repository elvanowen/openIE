package classes;

import id.ac.itb.openie.crawler.ICrawlerHandler;
import id.ac.itb.openie.extractor.IExtractorHandler;
import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/24/17.
 */
public class ReverbExtractorHandler extends Plugin {

    public ReverbExtractorHandler(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class ReverbExtractorHandlerPlugin implements IExtractorHandler {

        public String getPluginName() {
            return "Reverb Extractor";
        }

        @Override
        public HashMap<File, Pair<String, Relations>> execute(File file, String payload, Relations relations) throws Exception {
            return new ReverbExtractor().execute(file, payload, relations);
        }

        public String toString() {
            return this.getPluginName();
        }

        public void extractorWillRun() {
            System.out.println(this.getPluginName() + " will run..");
        }

        public void extractorDidRun() {
            System.out.println(this.getPluginName() + " did run..");
        }
    }
}
