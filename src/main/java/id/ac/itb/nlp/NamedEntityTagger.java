package id.ac.itb.nlp;

import IndonesianNLP.IndonesianNETagger;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
public class NamedEntityTagger {

    public void tag(String sentence) {

        IndonesianNETagger inner = new IndonesianNETagger();

        ArrayList<String> NETag = inner.extractNamedEntity(sentence);
        System.out.println("NE length:" + NETag.size());
        for(int i = 0; i < NETag.size(); i++){
            System.out.println(NETag.get(i));
        }
    }
}
