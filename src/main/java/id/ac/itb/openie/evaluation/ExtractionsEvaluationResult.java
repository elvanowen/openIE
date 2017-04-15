package id.ac.itb.openie.evaluation;

import id.ac.itb.nlp.SentenceTokenizer;
import id.ac.itb.openie.relation.Relation;
import id.ac.itb.openie.relation.Relations;
import id.ac.itb.openie.utils.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by elvanowen on 4/15/17.
 */
public class ExtractionsEvaluationResult {
    private HashMap<String, Relations> correctRelationsByFilename = new HashMap<>();
    private Relations correctRelations = new Relations();
    private ExtractionsEvaluationModel extractionsEvaluationModel;
    private String recallFormula = "#Correct / #Relations";
    private String precisionFormula = "#Correct / #Extractions";
    private String fMeasureFormula = "2PR / (P + R)";

    public double getRecall() {
        return (double) getnCorrect() / (double) getnRelations();
    }

    public double getPrecision() {
        return (double) getnCorrect() / (double) getnExtractions();
    }

    public double getfMeasure() {
        return 2 * getPrecision() * getRecall() / (getPrecision() + getRecall());
    }

    public int getnCorrect() {
        return correctRelations.getRelations().size();
    }

    public int getnExtractions() {
        return extractionsEvaluationModel.getExtractedRelations().getRelations().size();
    }

    public int getnRelations() {
        return extractionsEvaluationModel.getLabelledRelations().getRelations().size();
    }

    public String getRecallFormula() {
        return recallFormula;
    }

    public String getPrecisionFormula() {
        return precisionFormula;
    }

    public String getfMeasureFormula() {
        return fMeasureFormula;
    }

    public String getRecallFormulaValue() {
        return String.format("%1$d / %2$d", getnCorrect(), getnRelations());
    }

    public String getPrecisionFormulaValue() {
        return String.format("%1$d / %2$d", getnCorrect(), getnExtractions());
    }

    public String getfMeasureFormulaValue() {
        return String.format("2 * %1$.2f * %2$.2f / (%1$.2f + %2$.2f)", getPrecision(), getRecall());
    }

    public int getnExtractedSentences() {
        HashSet<String> counter = new HashSet<>();

        for(Relation relation: extractionsEvaluationModel.getExtractedRelations().getRelations()) {
            counter.add(String.format("%s - %d", relation.getOriginSentence(), relation.getSentenceIndex()));
        }

        return counter.size();
    }

    public int getnLabelledSentences() {
        HashSet<String> counter = new HashSet<>();

        for(Relation relation: extractionsEvaluationModel.getLabelledRelations().getRelations()) {
            counter.add(String.format("%s - %d", relation.getOriginSentence(), relation.getSentenceIndex()));
        }

        return counter.size();
    }

    public int getnSentences() {
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        HashSet<String> visited = new HashSet<>();
        int counter = 0;

        for(Relation relation: extractionsEvaluationModel.getExtractedRelations().getRelations()) {
            String item = String.format("%s - %d", relation.getOriginSentence(), relation.getSentenceIndex());

            if (!visited.contains(item)) {
                ArrayList<String> sentences = sentenceTokenizer.tokenizeSentence(Utilities.getFileContent(new File(relation.getOriginFile())));
                counter += sentences.size();
            }

            visited.add(item);
        }

        return counter;
    }

    public HashMap<String, Relations> getCorrectRelationsByFilename() {
        return correctRelationsByFilename;
    }

    public void setCorrectRelationsByFilename(HashMap<String, Relations> correctRelationsByFilename) {
        this.correctRelationsByFilename = correctRelationsByFilename;
    }

    public Relations getCorrectRelations() {
        return correctRelations;
    }

    public void setCorrectRelations(Relations correctRelations) {
        this.correctRelations = correctRelations;
    }

    public ExtractionsEvaluationModel getExtractionsEvaluationModel() {
        return extractionsEvaluationModel;
    }

    public void setExtractionsEvaluationModel(ExtractionsEvaluationModel extractionsEvaluationModel) {
        this.extractionsEvaluationModel = extractionsEvaluationModel;
    }
}
