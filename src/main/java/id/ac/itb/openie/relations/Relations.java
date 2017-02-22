package id.ac.itb.openie.relations;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/23/17.
 */
public class Relations {
    private ArrayList<Relation> relations = new ArrayList<Relation>();

    public Relations() {}

    public Relations(Relation relation) {
        addRelation(relation);
    }

    public Relations(Relations relations) {
        addRelations(relations);
    }

    public Relations addRelation(Relation relation) {
        relations.add(relation);

        return this;
    }

    public Relations addRelations(Relations relations) {
        this.relations.addAll(relations.getRelations());

        return this;
    }


    public ArrayList<Relation> getRelations() {
        return relations;
    }
}
