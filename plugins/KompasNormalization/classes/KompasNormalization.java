package classes;

import id.ac.itb.openie.preprocess.IPreprocessorHandler;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 3/12/17.
 */
public class KompasNormalization extends Plugin {

    public KompasNormalization(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class KompasNormalizationHandler implements IPreprocessorHandler {

        @Override
        public String getPluginName() {
            return "Kompas Normalization";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            return null;
        }

        @Override
        public HashMap<String, String> setAvailableConfigurations(String key, String value) {
            return null;
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
        public HashMap<File, String> preprocess(File file, String payload) throws Exception {
            String preprocessedPayload = removeMetadata(payload);
            preprocessedPayload = removeLinkToAnotherArticle(preprocessedPayload);

            HashMap<File, String> pipelineItems = new HashMap<File, String>();
            pipelineItems.put(file, preprocessedPayload);

            return pipelineItems;
        }

        public String toString() {
            return this.getPluginName();
        }

        @Override
        public void preprocessorWillRun() {

        }

        @Override
        public void preprocessorDidRun() {

        }
    }
}
