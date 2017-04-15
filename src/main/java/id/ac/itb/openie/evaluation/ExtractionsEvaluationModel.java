package id.ac.itb.openie.evaluation;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.relation.Relation;
import id.ac.itb.openie.relation.Relations;
import id.ac.itb.openie.utils.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 4/15/17.
 */
public class ExtractionsEvaluationModel {

    private ArrayList<File> documents = new ArrayList<>();
    private HashMap<String, Relations> extractedRelationsByFilename = new HashMap<>();
    private HashMap<String, Relations> labelledRelationsByFilename = new HashMap<>();
    private Relations extractedRelations;
    private Relations labelledRelations;

    public ExtractionsEvaluationModel() {
        this(new File(System.getProperty("user.dir") + File.separator + new Config().getProperty("EXTRACTIONS_OUTPUT_RELATIVE_PATH")), new File(System.getProperty("user.dir") + File.separator + new Config().getProperty("EVALUATION_LABEL_OUTPUT_RELATIVE_PATH")));
    }

    public ExtractionsEvaluationModel(File extractionsDirectory, File labelsDirectory) {
        extractedRelations = new Relations();
        labelledRelations = new Relations();

        for (File file: Utilities.getDirectoryFiles(extractionsDirectory)) {
            extractedRelations.addRelations(new Relations(file));
        }

        for (File file: Utilities.getDirectoryFiles(labelsDirectory)) {
            labelledRelations.addRelations(new Relations(file));
        }

        HashSet<File> _files = new HashSet<>();

        for (Relation relation: extractedRelations.getRelations()) {
            File relationSource = new File(relation.getOriginFile());
            _files.add(relationSource);

            extractedRelationsByFilename.putIfAbsent(relationSource.getName(), new Relations());
            extractedRelationsByFilename.put(relationSource.getName(), extractedRelationsByFilename.get(relationSource.getName()).addRelation(relation));
        }

        for (Relation relation: labelledRelations.getRelations()) {
            File relationSource = new File(relation.getOriginFile());
            _files.add(relationSource);

            labelledRelationsByFilename.putIfAbsent(relationSource.getName(), new Relations());
            labelledRelationsByFilename.put(relationSource.getName(), labelledRelationsByFilename.get(relationSource.getName()).addRelation(relation));
        }

        documents = new ArrayList<>(_files);
        Collections.sort(documents);
    }

    public ArrayList<File> getDocuments() {
        return documents;
    }

    public HashMap<String, Relations> getLabelledRelationsByFilename() {
        return labelledRelationsByFilename;
    }

    public HashMap<String, Relations> getExtractedRelationsByFilename() {
        return extractedRelationsByFilename;
    }

    public Relations getExtractedRelations() {
        return extractedRelations;
    }

    public Relations getLabelledRelations() {
        return labelledRelations;
    }

}
