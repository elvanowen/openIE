package id.ac.itb.openie.relations;

import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
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

    public Relations addRelations(Relations relations) {
        this.relations.addAll(relations.getRelations());

        return this;
    }

    public Relations(File file) {
        String relationsString = Utilities.getFileContent(file).get(0);
        Relations relations = new Relations();

        Pattern p = Pattern.compile("(.*)\\((.*),(.*)\\)");
        Matcher m = p.matcher(relationsString);

        if (m.matches()) relations.addRelation(new Relation(m.group(2), m.group(1), m.group(3)));
    }

    public String toString() {
        return StringUtils.join(relations, "\n");
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }
}
