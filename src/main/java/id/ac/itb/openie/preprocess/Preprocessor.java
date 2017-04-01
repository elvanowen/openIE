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
        return this.getPreprocessorHandler().getPluginName();
    }
}
