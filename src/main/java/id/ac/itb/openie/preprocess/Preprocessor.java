package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.utils.Utilities;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 4/1/17.
 */
public class Preprocessor implements IPreprocessorPipelineElement {

    private IPreprocessorHandler preprocessorHandler;
    private int totalDocumentPreprocessed = 0;

    @Override
    public HashMap<File, String> execute(File file, String payload) throws Exception {
        HashMap<File, String> output = new HashMap<>();
        output.put(file, this.getPreprocessorHandler().preprocess(Utilities.getFileContent(file), payload));

        return output;
    }

    @Override
    public HashMap<File, String> read() throws Exception {
        return this.getPreprocessorHandler().read();
    }

    @Override
    public void write(File file, String preprocessed) throws Exception {
        this.getPreprocessorHandler().write(file, preprocessed);
    }

    public IPreprocessorHandler getPreprocessorHandler() {
        return preprocessorHandler;
    }

    public Preprocessor setPreprocessorHandler(IPreprocessorHandler preprocessorHandler) {
        this.preprocessorHandler = preprocessorHandler;
        return this;
    }

    public String toString() {
        if (getPreprocessorHandler().getAvailableConfigurations() != null) {
            String inputDirectory = getPreprocessorHandler().getAvailableConfigurations().get("Input Directory");
            String outputDirectory = getPreprocessorHandler().getAvailableConfigurations().get("Output Directory");

            if (inputDirectory != null) {
                return this.getPreprocessorHandler().getPluginName() + " : " + inputDirectory;
            } else if (outputDirectory != null) {
                return this.getPreprocessorHandler().getPluginName() + " : "  + outputDirectory;
            }
        }

        return this.getPreprocessorHandler().getPluginName();
    }

    public int getTotalDocumentPreprocessed() {
        return totalDocumentPreprocessed;
    }

    public Preprocessor setTotalDocumentPreprocessed(int totalDocumentPreprocessed) {
        this.totalDocumentPreprocessed = totalDocumentPreprocessed;
        return this;
    }
}
