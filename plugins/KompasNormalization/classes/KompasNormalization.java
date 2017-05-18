package classes;

import id.ac.itb.openie.preprocess.IPreprocessorExtensionHandler;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.HashMap;

/**
 * Created by elvanowen on 3/12/17.
 */
public class KompasNormalization extends Plugin {

    public KompasNormalization(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class KompasNormalizationHandler extends IPreprocessorExtensionHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        @Override
        public String getPluginName() {
            return "Kompas Normalization";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            return availableConfigurations;
        }

        @Override
        public void setAvailableConfigurations(String key, String value) {
            availableConfigurations.put(key, value);
        }

        /* Function  : Remove document metadata */
        /* Input     : (Baca juga: Ada Usul ... Tahan Diri) Fraksi Partai Demokrat... */
        /* Output    : Fraksi Partai Demokrat... */
        protected String removeLinkToAnotherArticle(String fileContent) {
            return fileContent.replaceAll("(\\(Baca.*?\\))","").replaceAll("(\\(BACA.*?\\))","");
        }

        /* Function  : Remove document metadata */
        /* Input     : Jakarta, KOMPAS.com - PT Bank Mandiri (Persero) Tbk... */
        /* Output    : PT Bank Mandiri (Persero) Tbk... */
        private String removeMetadata(String fileContent) {
            return fileContent.replaceFirst("(^.*?KOMPAS(\\.com)?...)","").trim();
        }

        @Override
        public String preprocess(String body, String preprocessed) throws Exception {
            String preprocessedPayload = removeMetadata(preprocessed);
            preprocessedPayload = removeLinkToAnotherArticle(preprocessedPayload);

            return preprocessedPayload;
        }

        @Override
        public void preprocessorWillRun() {

        }

        @Override
        public void preprocessorDidRun() {

        }
    }
}
