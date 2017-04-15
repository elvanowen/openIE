package id.ac.itb.openie.relation;

import org.apache.commons.lang3.tuple.Triple;

/**
 * Created by elvanowen on 2/23/17.
 */
public class Relation {
    private String firstEntity = null;
    private String relation = null;
    private String secondEntity = null;
    private String originSentence = null;
    private int idxSentence;
    private String originFile = null;

    public Relation(String firstEntity, String relation, String secondEntity, String originFile, int idxSentence, String originSentence) {
        this.firstEntity = firstEntity;
        this.relation = relation;
        this.secondEntity = secondEntity;
        this.idxSentence = idxSentence;
        this.originSentence = originSentence;
        this.originFile = originFile;
    }

    public Relation(Triple<String, String, String> relation, String originFile, int idxSentence, String originSentence) {
        this.firstEntity = relation.getLeft();
        this.relation = relation.getMiddle();
        this.secondEntity = relation.getRight();
        this.idxSentence = idxSentence;
        this.originSentence = originSentence;
        this.originFile = originFile;
    }

    public String toString() {
        return String.format("Source: %s\nKalimat ke-%s: %s\nRelasi: %s(%s, %s)\n", originFile, (idxSentence + 1), originSentence, relation, firstEntity, secondEntity);
    }

    public Triple<String, String, String> getRelationTriple() {
        return Triple.of(firstEntity, relation, secondEntity);
    }

    public String getOriginFile() {
        return this.originFile;
    }

    public String getOriginSentence() {
        return this.originSentence;
    }

    public int getSentenceIndex() {
        return this.idxSentence;
    }
}
