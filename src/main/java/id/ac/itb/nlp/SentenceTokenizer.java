package id.ac.itb.nlp;

import IndonesianNLP.IndonesianSentenceDetector;
import id.ac.itb.openie.utils.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
public class SentenceTokenizer {

    public ArrayList<String> tokenizeSentence(String sentence) {
        IndonesianSentenceDetector detector = new IndonesianSentenceDetector();

        return detector.splitSentence(sentence);
    }

    public ArrayList<String> tokenizeSentences(ArrayList<String> sentences) {
        ArrayList<String> sentenceList = new ArrayList<String>();

        for (String sentence: sentences) {
            sentenceList.addAll(tokenizeSentence(sentence));
        }

        return sentenceList;
    }

    public ArrayList<String> tokenizeFile(File file) {
        return tokenizeSentences(Utilities.getFileContent(file));
    }

    public ArrayList<String> tokenizeFiles(ArrayList<File> files) {
        ArrayList<String> sentenceList = new ArrayList<String>();

        for(File file: files) {
            sentenceList.addAll(tokenizeFile(file));
        }

        return sentenceList;
    }
}
