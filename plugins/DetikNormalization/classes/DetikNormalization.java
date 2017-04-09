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
public class DetikNormalization extends Plugin {

    public DetikNormalization(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class DetikNormalizationHandler implements IPreprocessorHandler {

        @Override
        public String getPluginName() {
            return "Detik Normalization";
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
        /* Input     : (Foto: Dewi Irmasari/detikcom)Dalam... */
        /* Output    : Dalam... */
        protected String removeLinkPhoto(String fileContent) {
            return fileContent.replaceAll("(\\..*\\(Foto:.*?\\))",".");
        }

        /* Function  : Remove document metadata */
        /* Input     : (Baca juga: Ada Usul ... Tahan Diri) Fraksi Partai Demokrat... */
        /* Output    : Fraksi Partai Demokrat... */
        protected String removeLinkToAnotherArticle(String fileContent) {
            return fileContent.replaceAll("(\\(Baca.*?\\))","");
        }

        /* Function  : Remove document metadata */
        /* Input     : terjadi kemarin ini. (dnu/dnu)] */
        /* Output    : terjadi kemarin ini. */
        protected String removeEndingText(String fileContent) {
            return fileContent.replaceAll("(\\.\\s+\\(.*?\\)$)",".");
        }

        /* Function  : Remove document metadata */
        /* Input     : Semarang - Zebra Ujian SIM... */
        /* Output    : Zebra Ujian SIM... */
        private String removeMetadata(String fileContent) {
            return fileContent.replaceFirst("(^.*?\\s[—-]+\\s)","").trim();
        }

        @Override
        public HashMap<File, String> preprocess(File file, String payload) throws Exception {
            String preprocessedPayload = removeMetadata(payload);
            preprocessedPayload = removeLinkPhoto(preprocessedPayload);
            preprocessedPayload = removeLinkToAnotherArticle(preprocessedPayload);
            preprocessedPayload = removeEndingText(preprocessedPayload);

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
