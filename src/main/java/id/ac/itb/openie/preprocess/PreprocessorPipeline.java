package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;

import java.io.File;
import java.util.*;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PreprocessorPipeline implements IOpenIePipelineElement {

    private ArrayList<IPreprocessorPipelineElement> preprocessorPipelineElements = new ArrayList<IPreprocessorPipelineElement>();

    public PreprocessorPipeline addPipelineElement(IPreprocessorPipelineElement preprocessorPipelineElement) {
        preprocessorPipelineElements.add(preprocessorPipelineElement);
        return this;
    }

    public void execute() throws Exception {
        System.out.println("Running preprocessor pipeline...");

        HashMap<File, String> pipeQueue = null;
        HashMap<File, String> nextPipeQueue = null;

        for (IPreprocessorPipelineElement preprocessorPipelineElement: preprocessorPipelineElements) {
            if (pipeQueue == null) {
                pipeQueue = new HashMap<File, String>();
                nextPipeQueue = new HashMap<File, String>();

                HashMap<File, String> preprocessed = preprocessorPipelineElement.execute(null, null);
                pipeQueue.putAll(preprocessed);
            } else {
                Iterator<Map.Entry<File, String>> it = pipeQueue.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry<File, String> pair = it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());

                    HashMap<File, String> preprocessed = preprocessorPipelineElement.execute(pair.getKey(), pair.getValue());

                    nextPipeQueue.putAll(preprocessed);

                    it.remove(); // avoids a ConcurrentModificationException
                }

                pipeQueue = nextPipeQueue;
                nextPipeQueue = new HashMap<File, String>();
            }
        }
    }
}
