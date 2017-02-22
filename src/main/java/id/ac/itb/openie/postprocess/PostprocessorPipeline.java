package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PostprocessorPipeline implements IOpenIePipelineElement {

    private ArrayList<IPostProcessorPipelineElement> postprocessorPipelineElements = new ArrayList<IPostProcessorPipelineElement>();

    public PostprocessorPipeline addPipelineElement(IPostProcessorPipelineElement postprocessorPipelineElement) {
        postprocessorPipelineElements.add(postprocessorPipelineElement);
        return this;
    }

    public void execute() throws Exception {
        System.out.println("Running postprocessor pipeline...");

        Queue<Pair<File, Relations>> pipeQueue = new LinkedList<Pair<File, Relations>>();
        Queue<Pair<File, Relations>> nextPipeQueue = new LinkedList<Pair<File, Relations>>();
        pipeQueue.add(Pair.of((File) null, (Relations) null));

        Iterator<Pair<File, Relations>> iterator = pipeQueue.iterator();

        for (IPostProcessorPipelineElement postprocessorPipelineElement: postprocessorPipelineElements) {
            while(iterator.hasNext()){
                Pair<File, Relations> pipelineItem = iterator.next();

                ArrayList<Pair<File, Relations>> postprocessed = postprocessorPipelineElement.execute(pipelineItem.getLeft(), pipelineItem.getRight());

                nextPipeQueue.addAll(postprocessed);
            }

            pipeQueue = nextPipeQueue;
            iterator = pipeQueue.iterator();
            nextPipeQueue = new LinkedList<Pair<File, Relations>>();
        }
    }
}
