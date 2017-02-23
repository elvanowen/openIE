package id.ac.itb.openie.extractor;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.postprocess.IPostprocessorPipelineElement;
import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.*;

/**
 * Created by elvanowen on 2/23/17.
 */
public class ExtractorPipeline implements IOpenIePipelineElement {

    private ArrayList<IExtractorPipelineElement> extractorPipelineElements = new ArrayList<IExtractorPipelineElement>();

    public ExtractorPipeline addPipelineElement(IExtractorPipelineElement extractorPipelineElement) {
        extractorPipelineElements.add(extractorPipelineElement);
        return this;
    }

    public void execute() throws Exception {
        System.out.println("Running extractor pipeline...");

        HashMap<File, Pair<String, Relations>> pipeQueue = null;
        HashMap<File, Pair<String, Relations>> nextPipeQueue = null;

        for (IExtractorPipelineElement extractorPipelineElement: extractorPipelineElements) {
            if (pipeQueue == null) {
                pipeQueue = new HashMap<File, Pair<String, Relations>>();
                nextPipeQueue = new HashMap<File, Pair<String, Relations>>();

                HashMap<File, Pair<String, Relations>> extractedRelations = extractorPipelineElement.execute(null, null, null);
                pipeQueue.putAll(extractedRelations);
            } else {
                Iterator<Map.Entry<File, Pair<String, Relations>>> it = pipeQueue.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry<File, Pair<String, Relations>> pair = it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());

                    HashMap<File, Pair<String, Relations>> preprocessed = extractorPipelineElement.execute(pair.getKey(), pair.getValue().getLeft(), pair.getValue().getRight());

                    nextPipeQueue.putAll(preprocessed);

                    it.remove(); // avoids a ConcurrentModificationException
                }

                pipeQueue = nextPipeQueue;
                nextPipeQueue = new HashMap<File, Pair<String, Relations>>();
            }
        }
    }
}
