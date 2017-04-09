package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.relations.Relations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PostprocessorPipeline implements IOpenIePipelineElement {

    private ArrayList<IPostprocessorPipelineElement> postprocessorPipelineElements = new ArrayList<IPostprocessorPipelineElement>();
    private int totalProcessedPostprocessor = 0;
    private int totalDocumentsToBePostprocessed = 0;
    private int currentlyPostprocessedDocuments = 0;
    private IPostprocessorPipelineElement currentlyRunningPostprocessor = null;
    private IPostprocessorPipelineHook postprocessorPipelineHook = null;

    public PostprocessorPipeline addPipelineElement(IPostprocessorPipelineElement postprocessorPipelineElement) {
        postprocessorPipelineElements.add(postprocessorPipelineElement);
        return this;
    }

    public int getNumberOfPostprocessors() {
        int n = 0;

        for (IPostprocessorPipelineElement postprocessorPipelineElement: postprocessorPipelineElements) {
            if (((Postprocessor)postprocessorPipelineElement).getPostprocessorHandler().getPluginName().equalsIgnoreCase("Postprocessor File Reader")) {
                continue;
            } else if (((Postprocessor)postprocessorPipelineElement).getPostprocessorHandler().getPluginName().equalsIgnoreCase("Postprocessor File Writer")) {
                continue;
            } else {
                n++;
            }
        }

        return n;
    }

    @Override
    public void willExecute() {
        if (this.getNumberOfPostprocessors() > 0) {
            postprocessorPipelineHook.willExecute();
        }
    }

    public void execute() throws Exception {
        System.out.println("Running postprocessor pipeline...");

        HashMap<File, Relations> pipeQueue = null;
        HashMap<File, Relations> nextPipeQueue = null;

        for (IPostprocessorPipelineElement postprocessorPipelineElement: postprocessorPipelineElements) {
            this.currentlyRunningPostprocessor = postprocessorPipelineElement;

            if (((Postprocessor)postprocessorPipelineElement).getPostprocessorHandler().getPluginName().equalsIgnoreCase("Postprocessor File Reader")) {
                HashMap<File, Relations> postprocessed = postprocessorPipelineElement.execute(null, null);
                pipeQueue.putAll(postprocessed);
                totalDocumentsToBePostprocessed += postprocessed.size();
            } else if (((Postprocessor)postprocessorPipelineElement).getPostprocessorHandler().getPluginName().equalsIgnoreCase("Postprocessor File Writer")) {
                for (Map.Entry<File, Relations> pair : pipeQueue.entrySet()) {
                    postprocessorPipelineElement.execute(pair.getKey(), pair.getValue());
                }
            } else {
                this.totalProcessedPostprocessor++;
                Iterator<Map.Entry<File, Relations>> it = pipeQueue.entrySet().iterator();

                currentlyPostprocessedDocuments = 0;

                while (it.hasNext()) {
                    Map.Entry<File, Relations> pair = it.next();
                    HashMap<File, Relations> preprocessed = postprocessorPipelineElement.execute(pair.getKey(), pair.getValue());

                    nextPipeQueue.putAll(preprocessed);
                    currentlyPostprocessedDocuments++;
                }

                pipeQueue = nextPipeQueue;
                nextPipeQueue = new HashMap<>();
            }
        }
    }

    @Override
    public void didExecute() {
        if (this.getNumberOfPostprocessors() > 0) {
            postprocessorPipelineHook.didExecute();
        }
    }

    public int getTotalProcessedPostprocessor() {
        return totalProcessedPostprocessor;
    }

    public int getTotalDocumentsToBePostprocessed() {
        return totalDocumentsToBePostprocessed;
    }

    public int getCurrentlyPostprocessedDocuments() {
        return currentlyPostprocessedDocuments;
    }

    public IPostprocessorPipelineElement getCurrentlyRunningPostprocessor() {
        return currentlyRunningPostprocessor;
    }

    public PostprocessorPipeline setPostprocessorPipelineHook(IPostprocessorPipelineHook postprocessorPipelineHook) {
        this.postprocessorPipelineHook = postprocessorPipelineHook;
        return this;
    }
}
