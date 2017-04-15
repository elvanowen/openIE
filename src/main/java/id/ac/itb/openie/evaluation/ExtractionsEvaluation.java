package id.ac.itb.openie.evaluation;

import id.ac.itb.openie.relation.Relations;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 4/15/17.
 */
public class ExtractionsEvaluation {

    private ExtractionsEvaluationResult extractionsEvaluationResult;

    public ExtractionsEvaluation(ExtractionsEvaluationModel extractionsEvaluationModel) {
        extractionsEvaluationResult = new ExtractionsEvaluationResult();
        extractionsEvaluationResult.setExtractionsEvaluationModel(extractionsEvaluationModel);
    }

    public void evaluate() {
        HashMap<String, Relations> correctRelationsByFilename = new HashMap<>();
        Relations correctRelations = new Relations();

        for (File file: extractionsEvaluationResult.getExtractionsEvaluationModel().getDocuments()) {
            Relations extractedRelations = extractionsEvaluationResult.getExtractionsEvaluationModel().getExtractedRelationsByFilename().get(file.getName());
            Relations labelledRelations = extractionsEvaluationResult.getExtractionsEvaluationModel().getLabelledRelationsByFilename().get(file.getName());
            Relations _correctRelations = extractedRelations.intersect(labelledRelations);

            if (_correctRelations.getRelations().size() > 0) {
                System.out.println("_correctRelations");
                System.out.println(_correctRelations);
            }

            correctRelationsByFilename.put(file.getName(), _correctRelations);
            correctRelations.addRelations(_correctRelations);
        }

        extractionsEvaluationResult.setCorrectRelationsByFilename(correctRelationsByFilename);
        extractionsEvaluationResult.setCorrectRelations(correctRelations);
    }

    public ExtractionsEvaluationResult getExtractionsEvaluationResult() {
        return extractionsEvaluationResult;
    }
}
