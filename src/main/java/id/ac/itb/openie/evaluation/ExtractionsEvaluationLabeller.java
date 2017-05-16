package id.ac.itb.openie.evaluation;

import id.ac.itb.nlp.SentenceTokenizer;
import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.relation.Relations;
import id.ac.itb.openie.utils.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 4/15/17.
 */
public class ExtractionsEvaluationLabeller {

    private ArrayList<File> documents;
    private HashMap<File, Relations> relationsByFile = new HashMap<>();

    public ExtractionsEvaluationLabeller() {
        documents = Utilities.getDirectoryFiles(new File(System.getProperty("user.dir") + File.separator + new Config().getProperty("PREPROCESSES_OUTPUT_RELATIVE_PATH").replaceAll("\\.", File.separator)));

        String labelsDirectory = System.getProperty("user.dir") + File.separator + new Config().getProperty("EVALUATION_LABEL_OUTPUT_RELATIVE_PATH").replaceAll("\\.", File.separator);

        for (File document: documents) {
            File evaluationRelationsFile = new File(labelsDirectory + File.separator + document.getName());

            if (evaluationRelationsFile.exists()) {
                relationsByFile.put(document, new Relations(evaluationRelationsFile));
            } else {
                relationsByFile.put(document, new Relations());
            }
        }
    }

    public void persist(File document) {
        Relations relationsToBeSaved = relationsByFile.get(document);
        String evaluationDir = System.getProperty("user.dir") + File.separator + new Config().getProperty("EVALUATION_LABEL_OUTPUT_RELATIVE_PATH").replaceAll("\\.", File.separator);

        if (relationsToBeSaved.getRelations().size() == 0) {
            Utilities.removeFile(new File(evaluationDir + File.separator + document.getName()));
        } else {
            Utilities.writeToFile(evaluationDir, document.getName(), relationsToBeSaved.toString());
        }
    }

    public ArrayList<File> getDocuments() {
        return documents;
    }

    public ArrayList<String> getDocumentSentences(File document) {
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        return sentenceTokenizer.tokenizeSentence(Utilities.getFileContent(document));
    }

    public Relations getRelationsFromDocument(File document) {
        return relationsByFile.get(document);
    }
}
