package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.relations.Relations;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 4/6/17.
 */
public class Postprocessor implements IPostprocessorPipelineElement {

    private IPostprocessorHandler postprocessorHandler;
    private int totalDocumentPostprocessed = 0;

    @Override
    public HashMap<File, Relations> execute(File file, Relations relations) throws Exception {
        return null;
    }

    public String toString() {
        if (getPostprocessorHandler().getAvailableConfigurations() != null) {
            String inputDirectory = getPostprocessorHandler().getAvailableConfigurations().get("Input Directory");
            String outputDirectory = getPostprocessorHandler().getAvailableConfigurations().get("Output Directory");

            if (inputDirectory != null) {
                return this.getPostprocessorHandler().getPluginName() + " : " + inputDirectory;
            } else if (outputDirectory != null) {
                return this.getPostprocessorHandler().getPluginName() + " : "  + outputDirectory;
            }
        }

        return this.getPostprocessorHandler().getPluginName();
    }

    public IPostprocessorHandler getPostprocessorHandler() {
        return postprocessorHandler;
    }

    public Postprocessor setPostprocessorHandler(IPostprocessorHandler postprocessorHandler) {
        this.postprocessorHandler = postprocessorHandler;
        return this;
    }

    public int getTotalDocumentPostprocessed() {
        return totalDocumentPostprocessed;
    }

    public Postprocessor setTotalDocumentPostprocessed(int totalDocumentPostprocessed) {
        this.totalDocumentPostprocessed = totalDocumentPostprocessed;
        return this;
    }
}
