package classes;

import id.ac.itb.openie.extractor.IExtractorExtensionHandler;
import id.ac.itb.openie.relation.Relations;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;

public class Extractor extends Plugin {

    public Extractor(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class ExtractorHandler extends IExtractorExtensionHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            /* Return extractor name e.g. `Reverb Rule Extractor` */
            return "";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            return availableConfigurations;
        }

        @Override
        public void setAvailableConfigurations(String key, String value) {
            availableConfigurations.put(key, value);
        }

        @Override
        public Relations extract(File file, String document, Relations extracted) throws Exception {
            /* TODO: Extract relations from document */
            return extracted;
        }

        public String toString() {
            return this.getPluginName();
        }

        public void extractorWillRun() {
            /* TODO: before extractor start crawling */
        }

        public void extractorDidRun() {
            /* TODO: after extractor finish crawling */
        }

    }
}
