package classes;

import IndonesianNLP.IndonesianSentenceFormalization;
import id.ac.itb.nlp.SentenceTokenizer;
import id.ac.itb.openie.preprocess.IPreprocessorHandler;
import org.apache.commons.lang3.StringUtils;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
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
    public static class SentenceFormalizationHandler implements IPreprocessorHandler {

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
        public HashMap<File, String> preprocess(File file, String payload) throws Exception {
            SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();

            ArrayList<String> sentences = sentenceTokenizer.tokenizeSentence(payload);
            ArrayList<String> preprocessedSentences = new ArrayList<>();
            IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();

            for (String sentence: sentences) {
                String preprocessedPayload = formalizer.formalizeSentence(sentence);

                formalizer.initStopword();
                System.out.println(formalizer.deleteStopword(preprocessedPayload));

                preprocessedSentences.add(preprocessedPayload);
            }

            HashMap<File, String> pipelineItems = new HashMap<>();
            pipelineItems.put(file, StringUtils.join(preprocessedSentences, ""));

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
