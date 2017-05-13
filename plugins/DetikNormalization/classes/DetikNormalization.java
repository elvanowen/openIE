package classes;

import id.ac.itb.openie.preprocess.IPreprocessorExtensionHandler;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.HashMap;

/**
 * Created by elvanowen on 3/12/17.
 */
public class DetikNormalization extends Plugin {

    public DetikNormalization(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class DetikNormalizationHandler extends IPreprocessorExtensionHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        @Override
        public String getPluginName() {
            return "Detik Normalization";
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
        public String preprocess(String document, String payload) throws Exception {
            String preprocessedPayload = removeMetadata(payload);
            preprocessedPayload = removeLinkPhoto(preprocessedPayload);
            preprocessedPayload = removeLinkToAnotherArticle(preprocessedPayload);
            preprocessedPayload = removeEndingText(preprocessedPayload);

            return preprocessedPayload;
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
