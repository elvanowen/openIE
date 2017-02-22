package id.ac.itb.openie.relations;

import org.apache.commons.lang3.tuple.Triple;

/**
 * Created by elvanowen on 2/23/17.
 */
public class Relation {
    private String firstEntity = null;
    private String relation = null;
    private String secondEntity = null;

    public Relation(String firstEntity, String relation, String secondEntity) {
        this.firstEntity = firstEntity;
        this.relation = relation;
        this.secondEntity = secondEntity;
    }

    public Relation(Triple<String, String, String> relation) {
        this.firstEntity = relation.getLeft();
        this.relation = relation.getMiddle();
        this.secondEntity = relation.getRight();
    }

    public Triple<String, String, String> getRelationTriple() {
        return Triple.of(firstEntity, relation, secondEntity);
    }
}
