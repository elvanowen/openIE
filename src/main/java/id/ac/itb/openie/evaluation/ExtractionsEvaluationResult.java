package id.ac.itb.openie.evaluation;

/**
 * Created by elvanowen on 4/15/17.
 */
public class ExtractionsEvaluationResult {
    private int nCorrect;
    private int nExtractions;
    private int nRelations;
    private String recallFormula = "#Correct / #Extractions";
    private String precisionFormula = "#Correct / #Relations";
    private String fMeasureFormula = "2PR / (P + R)";

    public double getRecall() {
        return getnCorrect() / getnExtractions();
    }

    public double getPrecision() {
        return getnCorrect() / getnRelations();
    }

    public double getfMeasure() {
        return 2 * getPrecision() * getRecall() / ( getPrecision() + getRecall() );
    }

    public int getnCorrect() {
        return nCorrect;
    }

    public void setnCorrect(int nCorrect) {
        this.nCorrect = nCorrect;
    }

    public int getnExtractions() {
        return nExtractions;
    }

    public void setnExtractions(int nExtractions) {
        this.nExtractions = nExtractions;
    }

    public int getnRelations() {
        return nRelations;
    }

    public void setnRelations(int nRelations) {
        this.nRelations = nRelations;
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
        return String.format("%1$d / %2$d", getnCorrect(), getnExtractions());
    }

    public String getPrecisionFormulaValue() {
        return String.format("%1$d / %2$d", getnCorrect(), getnRelations());
    }

    public String getfMeasureFormulaValue() {
        return String.format("2 * %1$f * %2$f / (%1$f + %2$f)", getPrecision(), getRecall());
    }
}
