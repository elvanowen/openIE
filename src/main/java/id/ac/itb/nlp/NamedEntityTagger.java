package id.ac.itb.nlp;

import IndonesianNLP.IndonesianNETagger;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
public class NamedEntityTagger {

    public ArrayList<String> tag(String sentence) {

        IndonesianNETagger inner = new IndonesianNETagger();

//        System.out.println(inner.extractNamedEntityList(sentence));
//        System.out.println(inner.extractNamedEntity2(sentence));
        System.out.println(sentence);
        System.out.println(inner.extractNamedEntity(sentence));
        System.out.println(inner.getToken());
        ArrayList<String> NETag = inner.extractNamedEntity(sentence);

        System.out.println("NE length:" + NETag.size());
//        for(int i = 0; i < NETag.size(); i++){
//            System.out.println(NETag.get(i));
//        }

        return NETag;
    }
}
