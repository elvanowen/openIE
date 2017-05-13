package classes;

import id.ac.itb.openie.preprocess.IPreprocessorExtensionHandler;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.HashMap;

public class Preprocessor extends Plugin {

    public Preprocessor(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class PreprocessorHandler extends IPreprocessorExtensionHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        @Override
        public String getPluginName() {
            /* Return crawler name e.g. `Sentence Formalization` */
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
        public String preprocess(String originalDocument, String preprocessed) throws Exception {
            /* TODO: Preprocess document */
            return preprocessed;
        }

        public String toString() {
            return this.getPluginName();
        }

        @Override
        public void preprocessorWillRun() {
            /* TODO: before preprocessor start crawling */
        }

        @Override
        public void preprocessorDidRun() {
            /* TODO: after preprocessor finish crawling */
        }
    }
}
