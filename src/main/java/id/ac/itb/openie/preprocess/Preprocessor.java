package id.ac.itb.openie.preprocess;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 4/1/17.
 */
public class Preprocessor implements IPreprocessorPipelineElement {

    private IPreprocessorHandler preprocessorHandler;

    @Override
    public HashMap<File, String> execute(File file, String payload) throws Exception {
        return this.getPreprocessorHandler().preprocess(file, payload);
    }

    public IPreprocessorHandler getPreprocessorHandler() {
        return preprocessorHandler;
    }

    public Preprocessor setPreprocessorHandler(IPreprocessorHandler preprocessorHandler) {
        this.preprocessorHandler = preprocessorHandler;
        return this;
    }

    public String toString() {
        String inputDirectory = getPreprocessorHandler().getAvailableConfigurations().get("Input Directory");
        String outputDirectory = getPreprocessorHandler().getAvailableConfigurations().get("Output Directory");

        if (inputDirectory != null) {
            return "File Reader: " + inputDirectory;
        } else if (outputDirectory != null) {
            return "File Writer: " + outputDirectory;
        } else {
            return this.getPreprocessorHandler().getPluginName();
        }
    }
}
