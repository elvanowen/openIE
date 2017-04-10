package id.ac.itb.nlp;

import IndonesianNLP.IndonesianSentenceDetector;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
public class SentenceTokenizer {

    public ArrayList<String> tokenizeSentence(String sentence) {
        System.out.println("tokenize sentence");
        System.out.println(sentence);
        IndonesianSentenceDetector detector = new IndonesianSentenceDetector();

        System.out.println(detector.splitSentence(sentence));

        return detector.splitSentence(sentence);
    }

}
