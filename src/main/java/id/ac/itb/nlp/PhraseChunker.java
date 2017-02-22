package id.ac.itb.nlp;

import IndonesianNLP.IndonesianPhraseChunker;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
public class PhraseChunker {

    public ArrayList<String[]> chunk(String sentence){
        IndonesianPhraseChunker chunker = new IndonesianPhraseChunker();

        chunker.setSentence(sentence);
        chunker.extractPhrase();
        ArrayList<String[]> phraseChunk = chunker.getBasicPhraseList();

//        for(int i = 0; i < phraseChunk.size(); i++){
//            System.out.println(phraseChunk.get(i)[0] + " - " + phraseChunk.get(i)[1]);
//        }

        return phraseChunk;
    }
}
