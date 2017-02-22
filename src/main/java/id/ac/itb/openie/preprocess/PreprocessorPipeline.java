package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PreprocessorPipeline implements IOpenIePipelineElement {

    private ArrayList<IPreProcessorPipelineElement> preprocessorPipelineElements = new ArrayList<IPreProcessorPipelineElement>();

    public PreprocessorPipeline addPipelineElement(IPreProcessorPipelineElement preprocessorPipelineElement) {
        preprocessorPipelineElements.add(preprocessorPipelineElement);
        return this;
    }

    public void execute() throws Exception {
        System.out.println("Running preprocessor pipeline...");

        Queue<Pair<File, String>> pipeQueue = new LinkedList<Pair<File, String>>();
        Queue<Pair<File, String>> nextPipeQueue = new LinkedList<Pair<File, String>>();
        pipeQueue.add(Pair.of((File) null, (String) null));

        Iterator<Pair<File, String>> iterator = pipeQueue.iterator();

        for (IPreProcessorPipelineElement preprocessorPipelineElement: preprocessorPipelineElements) {
            while(iterator.hasNext()){
                Pair<File, String> pipelineItem = iterator.next();

                ArrayList<Pair<File, String>> preprocessed = preprocessorPipelineElement.execute(pipelineItem.getLeft(), pipelineItem.getRight());

                nextPipeQueue.addAll(preprocessed);
            }

            pipeQueue = nextPipeQueue;
            iterator = pipeQueue.iterator();
            nextPipeQueue = new LinkedList<Pair<File, String>>();
        }
    }
}
