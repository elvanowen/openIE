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
public class DetikPreprocessorHandlerPlugin extends Plugin {

    public DetikPreprocessorHandlerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class DetikPreprocessorHandler implements IPreprocessorHandler {

        @Override
        public String getPluginName() {
            return "Detik Preprocessor";
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
            return fileContent.replaceFirst("(^.*?[—-]+)","").trim();
        }

        @Override
        public HashMap<File, String> execute(File file, String payload) throws Exception {
            String preprocessedPayload = removeMetadata(payload);
            preprocessedPayload = removeLinkPhoto(preprocessedPayload);
            preprocessedPayload = removeLinkToAnotherArticle(preprocessedPayload);
            preprocessedPayload = removeEndingText(preprocessedPayload);

            HashMap<File, String> pipelineItems = new HashMap<File, String>();
            pipelineItems.put(file, preprocessedPayload);

            return pipelineItems;
        }

        @Override
        public void preprocessorWillRun() {

        }

        @Override
        public void preprocessorDidRun() {

        }
    }
}
