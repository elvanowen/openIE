package id.ac.itb.openie.extractor;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.config.Config;
import id.ac.itb.nlp.SentenceTokenizer;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by elvanowen on 2/9/17.
 */
abstract class BaseExtractor implements IOpenIePipelineElement {

    private Boolean shouldStoreExtractions = true;
    private String extractionsStorageDirectoryPath = new Config().getProperty("EXTRACTIONS_STORAGE_DIRECTORY");
    private File fileToBeExtracted = null;

    public void setShouldStoreExtractions(Boolean shouldStoreExtractions) {
        this.shouldStoreExtractions = shouldStoreExtractions;
    }

    public void setFileToBeExtracted(File fileToBeExtracted) {
        this.fileToBeExtracted = fileToBeExtracted;
    }

    public Relations extractFromFile(File file) {
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        ArrayList<String> sentences = sentenceTokenizer.tokenizeFile(file);

        Relations relations = new Relations();

        for (String sentence: sentences) {
            relations.addRelations(extractRelationFromSentence(sentence));
            System.out.println("---------------------------------");
        }

        if (shouldStoreExtractions) {
            Utilities.writeToFile(extractionsStorageDirectoryPath, file.getName(), "Document :\n" + StringUtils.join(sentences, "\n") + "\n\n" + StringUtils.join(relations, "\n"));
        }

        return relations;
    }

    public Relations extractFromDirectory(File directory) {
        ArrayList<File> files = Utilities.getDirectoryFiles(directory);
        Relations relations = new Relations();

        for (File file: files) {
            relations.addRelations(extractFromFile(file));
        }

        return relations;
    }

    public void execute() throws Exception {
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        ArrayList<String> sentences = sentenceTokenizer.tokenizeFile(fileToBeExtracted);

        Relations relations = new Relations();

        for (String sentence: sentences) {
            relations.addRelations(extractRelationFromSentence(sentence));
            System.out.println("---------------------------------");
        }

        if (shouldStoreExtractions) {
            Utilities.writeToFile(extractionsStorageDirectoryPath, fileToBeExtracted.getName(), "Document :\n" + StringUtils.join(sentences, "\n") + "\n\n" + StringUtils.join(relations, "\n"));
        }
    }

    protected abstract Relations extractRelationFromSentence(String sentence);
}
