package classes;

import IndonesianNLP.IndonesianSentenceFormalization;
import id.ac.itb.nlp.SentenceTokenizer;
import id.ac.itb.openie.preprocess.IPreprocessorExtensionHandler;
import org.apache.commons.lang3.StringUtils;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 3/12/17.
 */
public class SentenceFormalization extends Plugin {

    public SentenceFormalization(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class SentenceFormalizationHandler extends IPreprocessorExtensionHandler {

        @Override
        public String getPluginName() {
            return "Sentence Formalization";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            return null;
        }

        @Override
        public HashMap<String, String> setAvailableConfigurations(String key, String value) {
            return null;
        }

        /*
        Input: kata2nya 4ku donk loecoe bangedh gt
        Output: kata-katanya aku dong lucu banget begitu
        */
        @Override
        public String preprocess(String body, String preprocessed) throws Exception {
            SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();

            ArrayList<String> sentences = sentenceTokenizer.tokenizeSentence(preprocessed);
            ArrayList<String> preprocessedSentences = new ArrayList<>();
            IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();

            for (String sentence: sentences) {
                String preprocessedPayload = formalizer.formalizeSentence(sentence);

                formalizer.initStopword();
                System.out.println(formalizer.deleteStopword(preprocessedPayload));

                preprocessedSentences.add(preprocessedPayload);
            }

            return StringUtils.join(preprocessedSentences, "");
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
