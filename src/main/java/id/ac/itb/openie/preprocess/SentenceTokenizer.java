package id.ac.itb.openie.preprocess;

import IndonesianNLP.IndonesianSentenceDetector;

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
        BufferedReader br = null;
        FileReader fr = null;
        ArrayList<String> content = new ArrayList<String>();

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                content.add(sCurrentLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (fr != null) fr.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return tokenizeSentences(content);
    }

    public ArrayList<String> tokenizeFiles(ArrayList<File> files) {
        ArrayList<String> sentenceList = new ArrayList<String>();

        for(File file: files) {
            sentenceList.addAll(tokenizeFile(file));
        }

        return sentenceList;
    }
}
