package id.ac.itb.nlp;

import IndonesianNLP.IndonesianPOSTagger;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
public class POSTagger {

    public ArrayList<String[]> tag(String sentence) {
        ArrayList<String[]> tags = IndonesianPOSTagger.doPOSTag(sentence);

        return tags;
    }
}
