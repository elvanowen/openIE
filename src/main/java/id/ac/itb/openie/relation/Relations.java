package id.ac.itb.openie.relation;

import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void removeRelation(int index) {
        relations.remove(index);
    }

    public Relations addRelations(Relations relations) {
        this.relations.addAll(relations.getRelations());
        return this;
    }

    public Relations(File file) {
        String relationsString = Utilities.getFileContent(file);

        Pattern p = Pattern.compile("Source:\\s(.*)\\nKalimat ke-(\\d*?):\\s(.*)\\nRelasi:\\s(.*?)\\((.*),\\s(.*)\\)\\n");
        Matcher m = p.matcher(relationsString);

        while (m.find()) relations.add(new Relation(m.group(5), m.group(4), m.group(6), m.group(1), Integer.valueOf(m.group(2)) - 1, m.group(3)));
    }

    public String toString() {
        return StringUtils.join(relations, "\n");
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public Relations intersect(Relations anotherRelations) {
        Relations intersectRelations = new Relations();

        if (anotherRelations == null) {
            return intersectRelations;
        }

        HashMap<String, Relations> relationsByFilename = new HashMap<>();
        HashMap<String, Relations> anotherRelationsByFilename = new HashMap<>();

        for (Relation relation: this.getRelations()) {
            File relationSource = new File(relation.getOriginFile());

            relationsByFilename.putIfAbsent(relationSource.getName(), new Relations());
            relationsByFilename.put(relationSource.getName(), relationsByFilename.get(relationSource.getName()).addRelation(relation));
        }

        for (Relation relation: anotherRelations.getRelations()) {
            File relationSource = new File(relation.getOriginFile());

            anotherRelationsByFilename.putIfAbsent(relationSource.getName(), new Relations());
            anotherRelationsByFilename.put(relationSource.getName(), anotherRelationsByFilename.get(relationSource.getName()).addRelation(relation));
        }

        for (String fileName : relationsByFilename.keySet()) {
            Relations _relations = relationsByFilename.get(fileName);

            for (String anotherFileName : anotherRelationsByFilename.keySet()) {
                Relations _anotherRelations = anotherRelationsByFilename.get(anotherFileName);

                for (Relation _relation: _relations.getRelations()) {
                    for (Relation _anotherRelation: _anotherRelations.getRelations()) {
                        ArrayList<Boolean> conditions = new ArrayList<>();

                        conditions.add(_relation.getSentenceIndex() == _anotherRelation.getSentenceIndex());
                        conditions.add(_relation.getRelationTriple().getLeft().toLowerCase().equalsIgnoreCase(_anotherRelation.getRelationTriple().getLeft().toLowerCase()));
                        conditions.add(_relation.getRelationTriple().getRight().toLowerCase().equalsIgnoreCase(_anotherRelation.getRelationTriple().getRight().toLowerCase()));
                        conditions.add(_relation.getRelationTriple().getMiddle().toLowerCase().equalsIgnoreCase(_anotherRelation.getRelationTriple().getMiddle().toLowerCase()));

                        if (conditions.indexOf(false) == -1) {
                            intersectRelations.addRelation(_relation);
                        }
                    }
                }
            }
        }

        return intersectRelations;
    }
}
