package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.preprocess.IPreprocessorPipelineElement;
import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.*;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PostprocessorPipeline implements IOpenIePipelineElement {

    private ArrayList<IPostprocessorPipelineElement> postprocessorPipelineElements = new ArrayList<IPostprocessorPipelineElement>();

    public PostprocessorPipeline addPipelineElement(IPostprocessorPipelineElement postprocessorPipelineElement) {
        postprocessorPipelineElements.add(postprocessorPipelineElement);
        return this;
    }

    public void execute() throws Exception {
        System.out.println("Running postprocessor pipeline...");

        HashMap<File, Relations> pipeQueue = null;
        HashMap<File, Relations> nextPipeQueue = null;

        for (IPostprocessorPipelineElement postprocessorPipelineElement: postprocessorPipelineElements) {
            if (pipeQueue == null) {
                pipeQueue = new HashMap<File, Relations>();
                nextPipeQueue = new HashMap<File, Relations>();

                HashMap<File, Relations> postprocessed = postprocessorPipelineElement.execute(null, null);
                pipeQueue.putAll(postprocessed);
            } else {
                Iterator<Map.Entry<File, Relations>> it = pipeQueue.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry<File, Relations> pair = it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());

                    HashMap<File, Relations> preprocessed = postprocessorPipelineElement.execute(pair.getKey(), pair.getValue());

                    nextPipeQueue.putAll(preprocessed);

                    it.remove(); // avoids a ConcurrentModificationException
                }

                pipeQueue = nextPipeQueue;
                nextPipeQueue = new HashMap<File, Relations>();
            }
        }
    }
}
