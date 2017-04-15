package id.ac.itb.openie.evaluation;

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
public class ExtractionsEvaluation {

    private ArrayList<File> documents = new ArrayList<>();
    private HashMap<String, Relations> extractedRelationsByFilename = new HashMap<>();
    private HashMap<String, Relations> labelledRelationsByFilename = new HashMap<>();
    private HashMap<String, Relations> correctRelationsByFilename = new HashMap<>();
    private Relations extractedRelations = new Relations();
    private Relations labelledRelations = new Relations();
    private Relations correctRelations = new Relations();
    private ExtractionsEvaluationResult extractionsEvaluationResult = new ExtractionsEvaluationResult();

    public void evaluate(File extractionsDirectory, File labelsDirectory) {
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

        calculateCorrectRelations();

        extractionsEvaluationResult = new ExtractionsEvaluationResult();

        extractionsEvaluationResult.setnExtractions(extractedRelations.getRelations().size());
        extractionsEvaluationResult.setnRelations(labelledRelations.getRelations().size());
        extractionsEvaluationResult.setnCorrect(correctRelations.getRelations().size());
    }

    private void calculateCorrectRelations() {
        for (File file: documents) {
            Relations extractedRelations = extractedRelationsByFilename.get(file.getName());
            Relations labelledRelations = labelledRelationsByFilename.get(file.getName());
            Relations _correctRelations = extractedRelations.intersect(labelledRelations);

            correctRelationsByFilename.put(file.getName(), _correctRelations);
            correctRelations.addRelations(_correctRelations);
        }
    }

    public Relations getExtractedRelations() {
        return extractedRelations;
    }

    public Relations getLabelledRelations() {
        return labelledRelations;
    }

    public Relations getCorrectRelations() {
        return correctRelations;
    }

    public HashMap<String, Relations> getLabelledRelationsByFilename() {
        return labelledRelationsByFilename;
    }

    public HashMap<String, Relations> getExtractedRelationsByFilename() {
        return extractedRelationsByFilename;
    }

    public HashMap<String, Relations> getCorrectRelationsByFilename() {
        return correctRelationsByFilename;
    }

    public ArrayList<File> getDocuments() {
        return documents;
    }

    public ExtractionsEvaluationResult getExtractionsEvaluationResult() {
        return extractionsEvaluationResult;
    }
}
