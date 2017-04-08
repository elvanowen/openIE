package id.ac.itb.nlp;

import IndonesianNLP.IndonesianSentenceDetector;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
public class SentenceTokenizer {

    public ArrayList<String> tokenizeSentence(String sentence) {
        IndonesianSentenceDetector detector = new IndonesianSentenceDetector();

        return detector.splitSentence(sentence);
    }

}
