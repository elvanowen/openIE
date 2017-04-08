package id.ac.itb.openie.relations;

import org.apache.commons.lang3.tuple.Triple;

/**
 * Created by elvanowen on 2/23/17.
 */
public class Relation {
    private String firstEntity = null;
    private String relation = null;
    private String secondEntity = null;
    private String originSentence = null;

    public Relation(String firstEntity, String relation, String secondEntity, String originSentence) {
        this.firstEntity = firstEntity;
        this.relation = relation;
        this.secondEntity = secondEntity;
        this.originSentence = originSentence;
    }

    public Relation(Triple<String, String, String> relation, String originSentence) {
        this.firstEntity = relation.getLeft();
        this.relation = relation.getMiddle();
        this.secondEntity = relation.getRight();
        this.originSentence = originSentence;
    }

    public String toString() {
        return String.format("Kalimat: %s\nRelasi: %s(%s, %s)\n", originSentence, relation, firstEntity, secondEntity);
    }

    public Triple<String, String, String> getRelationTriple() {
        return Triple.of(firstEntity, relation, secondEntity);
    }
}
