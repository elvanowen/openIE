package classes;

import id.ac.itb.nlp.POSTagger;
import id.ac.itb.nlp.SentenceTokenizer;
import id.ac.itb.openie.relations.Relation;
import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/9/17.
 */

public class ReverbExtractor {

    private enum EXTRACT_VERB_STATE {
        V, W, P
    }

    private enum EXTRACT_ARGUMENT_STATE {
        IN, OUT
    }

    /* Reverb extraction rule */
    /* Extract verb then find two nearest noun phrase */
    /* Extract verb follows below rules: */
    /* Pattern : V | VP | VW*P */
    /* V : kata kerja (verb) */
    /* W : kata benda (noun) | kata sifat (adj) | kata keterangan (adv) | kata ganti (pronoun) | determiners */
    /* P : preposisi */
    private ArrayList<Triple<String, Integer, Integer>> extractVerbs(String sentence) {
        EXTRACT_VERB_STATE currentState = null;

        POSTagger posTagger = new POSTagger();
        ArrayList<String[]> tags = posTagger.tag(sentence);

        ArrayList<Pair<Integer, String[]>> v = new ArrayList<Pair<Integer, String[]>>();
        ArrayList<Pair<Integer, String[]>> w = new ArrayList<Pair<Integer, String[]>>();
        ArrayList<Pair<Integer, String[]>> p = new ArrayList<Pair<Integer, String[]>>();

        for(int i = 0; i < tags.size(); i++) {
            System.out.println(tags.get(i)[0] + " / " + tags.get(i)[1]);
        }

        System.out.println("Verbs from : " + sentence);

        ArrayList<Triple<String, Integer, Integer>> relations = new ArrayList<Triple<String, Integer, Integer>>();

        for(int i = 0; i < tags.size(); i++){
            if (tags.get(i)[1].startsWith("VB") || tags.get(i)[1].startsWith("NEG")) {
                if (currentState == null || currentState == EXTRACT_VERB_STATE.V) {
                    v.add(Pair.of(i, tags.get(i)));
                    System.out.println(tags.get(i)[0] + " / " + tags.get(i)[1]);

                    currentState = EXTRACT_VERB_STATE.V;
                } else {
                    ArrayList<Pair<Integer, String[]>> longestVerb = new ArrayList<Pair<Integer, String[]>>();

                    longestVerb.addAll(v);

                    if (p.size() > 0) {
                        if (w.size() > 0) {
                            longestVerb.addAll(w);
                        }

                        longestVerb.addAll(p);
                    }

                    Integer startIdx = Integer.MAX_VALUE;
                    Integer endIdx = Integer.MIN_VALUE;
                    ArrayList<String> verbs = new ArrayList<String>();

                    for (Pair<Integer, String[]> verb: longestVerb) {
                        if (startIdx > verb.getLeft()) {
                            startIdx = verb.getLeft();
                        }

                        if (endIdx < verb.getLeft()) {
                            endIdx = verb.getLeft();
                        }

                        verbs.add(verb.getRight()[0]);
                    }

                    relations.add(Triple.of(StringUtils.join(verbs, " "), startIdx, endIdx));
                    System.out.println("VERB:" + StringUtils.join(verbs, " "));

                    // Reset state
                    v = new ArrayList<Pair<Integer, String[]>>();
                    w = new ArrayList<Pair<Integer, String[]>>();
                    p = new ArrayList<Pair<Integer, String[]>>();
                    currentState = null;

                    // Return back to current loop
                    i = i - 1;
                    continue;
                }
            } else if (tags.get(i)[1].startsWith("CDP") || tags.get(i)[1].startsWith("NN") || tags.get(i)[1].startsWith("DT")) {
                if (currentState == EXTRACT_VERB_STATE.V || currentState == EXTRACT_VERB_STATE.W) {
                    w.add(Pair.of(i, tags.get(i)));
                    System.out.println(tags.get(i)[0] + " / " + tags.get(i)[1]);

                    currentState = EXTRACT_VERB_STATE.W;
                }
            } else if (tags.get(i)[1].equalsIgnoreCase("IN")) {
                if (currentState != null) {
                    p.add(Pair.of(i, tags.get(i)));
                    System.out.println(tags.get(i)[0] + " / " + tags.get(i)[1]);

                    currentState = EXTRACT_VERB_STATE.P;
                }
            }
        }

        ArrayList<Pair<Integer, String[]>> longestVerb = new ArrayList<Pair<Integer, String[]>>();

        longestVerb.addAll(v);

        if (p.size() > 0) {
            if (w.size() > 0) {
                longestVerb.addAll(w);
            }

            longestVerb.addAll(p);
        }

        Integer startIdx = Integer.MAX_VALUE;
        Integer endIdx = Integer.MIN_VALUE;
        ArrayList<String> verbs = new ArrayList<String>();

        for (Pair<Integer, String[]> verb: longestVerb) {
            if (startIdx > verb.getLeft()) {
                startIdx = verb.getLeft();
            }

            if (endIdx < verb.getLeft()) {
                endIdx = verb.getLeft();
            }

            verbs.add(verb.getRight()[0]);
        }

        relations.add(Triple.of(StringUtils.join(verbs, " "), startIdx, endIdx));
        System.out.println("VERB:" + StringUtils.join(verbs, " "));

//        ArrayList<Pair<Integer, String[]>> longestVerb = new ArrayList<Pair<Integer, String[]>>();
//
//        longestVerb.addAll(v);
//
//        if (p.size() > 0) {
//            if (w.size() > 0) {
//                longestVerb.addAll(w);
//            }
//
//            longestVerb.addAll(p);
//        }
//
//        Integer startIdx = Integer.MAX_VALUE;
//        Integer endIdx = Integer.MIN_VALUE;
//        ArrayList<String> verbs = new ArrayList<String>();
//
//        for (Pair<Integer, String[]> verb: longestVerb) {
//            if (startIdx > verb.getLeft()) {
//                startIdx = verb.getLeft();
//            }
//
//            if (endIdx < verb.getLeft()) {
//                endIdx = verb.getLeft();
//            }
//
//            verbs.add(verb.getRight()[0]);
//        }
//
//        return Triple.of(StringUtils.join(verbs, " "), startIdx, endIdx);

        return relations;
    }

    private Pair<String, String> extractClosestArguments(String sentence, int startVerbIdx, int endVerbIdx) {

        POSTagger posTagger = new POSTagger();
        ArrayList<String[]> tags = posTagger.tag(sentence);

        String firstArgument = "", secondArgument = "";
        EXTRACT_ARGUMENT_STATE currentState = null;

        for(int i = 0; i < startVerbIdx; i++){
            if (tags.get(i)[1].startsWith("CDP") || tags.get(i)[1].startsWith("NN")) {
                if (currentState == null || currentState == EXTRACT_ARGUMENT_STATE.IN) {
                    firstArgument += " " + tags.get(i)[0];
                } else if (currentState == EXTRACT_ARGUMENT_STATE.OUT) {
                    firstArgument = tags.get(i)[0];
                }

                currentState = EXTRACT_ARGUMENT_STATE.IN;
            } else if (!firstArgument.equalsIgnoreCase("")){
                currentState = EXTRACT_ARGUMENT_STATE.OUT;
            }
        }

        for(int i = endVerbIdx; i < tags.size(); i++){
            if (tags.get(i)[1].startsWith("CDP") || tags.get(i)[1].startsWith("NN")) {
                secondArgument += " " + tags.get(i)[0];
            } else if (!secondArgument.equalsIgnoreCase("")){
                break;
            }
        }

        firstArgument = firstArgument.trim();
        secondArgument = secondArgument.trim();

//        System.out.println("before argument");
//        System.out.println(firstArgument);
//        System.out.println("after argument");
//        System.out.println(secondArgument);

//        System.out.println("first");
//        System.out.println(StringUtils.join(ArrayUtils.subarray(sentence.split(" "), 0, startVerbIdx), " "));
//        System.out.println("second");
//        System.out.println(StringUtils.join(ArrayUtils.subarray(sentence.split(" "), endVerbIdx + 1, sentence.split(" ").length), " "));

//        POSTagger posTagger = new POSTagger();
//        ArrayList<String[]> beforeVerbTags = posTagger.tag(beforeVerbSentence);
//
//        System.out.println("beforeVerbTags");
//        System.out.println(beforeVerbTags);
//
//        posTagger = new POSTagger();
//        ArrayList<String[]> afterVerbTags = posTagger.tag(afterVerbSentence);

        return Pair.of(firstArgument, secondArgument);
    }

    protected Relations extractRelationFromSentence(String sentence){

        Pair<String, String> arguments = Pair.of("", "");
        String extractedVerb = "";

        System.out.println("origin sentence : " + sentence);

        Relations relations = new Relations();
        ArrayList<Triple<String, Integer, Integer>> triples = extractVerbs(sentence);

        for (Triple<String, Integer, Integer> triple: triples) {
            extractedVerb = triple.getLeft();
            Integer verbStartIdx = triple.getMiddle();
            Integer verbEndIdx = triple.getRight();

            if (!extractedVerb.equalsIgnoreCase("")) {
                arguments = extractClosestArguments(sentence, verbStartIdx, verbEndIdx);
            }

            if (!arguments.getLeft().equalsIgnoreCase("") && !extractedVerb.equalsIgnoreCase("") && !arguments.getRight().equalsIgnoreCase("")) {
                relations.addRelation(new Relation(arguments.getLeft(), extractedVerb, arguments.getRight(), sentence));
            }
        }

        return relations;
    }

    public HashMap<File, Pair<String, Relations>> extract(File file, String payload, Relations relations) throws Exception {

        HashMap<File, Pair<String, Relations>> pipelineItems = new HashMap<>();

        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();

        Relations extractedRelations = new Relations();

        for (String sentence: sentenceTokenizer.tokenizeSentence(payload)) {
            Relations _extractedRelations = extractRelationFromSentence(sentence);
            extractedRelations.addRelations(_extractedRelations);
        }

        pipelineItems.put(file, Pair.of(payload, extractedRelations));

        System.out.println("reverb pipelineItems");
        System.out.println(pipelineItems);

        return pipelineItems;
    }
}
