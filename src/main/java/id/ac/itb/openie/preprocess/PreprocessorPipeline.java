package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.plugins.PluginLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PreprocessorPipeline implements IOpenIePipelineElement {

    private ArrayList<IPreprocessorPipelineElement> preprocessorPipelineElements = new ArrayList<IPreprocessorPipelineElement>();
    private int totalProcessedPreprocessor = 0;
    private int totalDocumentsToBePreprocessed = 0;
    private int currentlyPreprocessedDocuments = 0;
    private IPreprocessorPipelineElement currentlyRunningPreprocessor = null;
    private IPreprocessorPipelineHook preprocessorPipelineHook = null;

    public PreprocessorPipeline addPipelineElement(IPreprocessorPipelineElement preprocessorPipelineElement) {
        preprocessorPipelineElements.add(preprocessorPipelineElement);
        return this;
    }

    public ArrayList<IPreprocessorPipelineElement> getPreprocessorPipelineElements() {
        return this.preprocessorPipelineElements;
    }

    public int getNumberOfPreprocessors() {
        int n = 0;

        for (IPreprocessorPipelineElement preprocessorPipelineElement: preprocessorPipelineElements) {
            if (((Preprocessor)preprocessorPipelineElement).getPreprocessorHandler().getPluginName().equalsIgnoreCase("Preprocessor File Reader")) {
                continue;
            } else if (((Preprocessor)preprocessorPipelineElement).getPreprocessorHandler().getPluginName().equalsIgnoreCase("Preprocessor File Writer")) {
                continue;
            } else {
                n++;
            }
        }

        return n;
    }

    private void addDefaultReaderAndWriter() {
        if (preprocessorPipelineElements.size() > 0) {
            PluginLoader pluginLoader = new PluginLoader();
            pluginLoader.registerAvailableExtensions(IPreprocessorHandler.class);

            // Prepend preprocessor file reader if not exist
            for (Object iPreprocessorHandler: pluginLoader.getImplementedExtensions(IPreprocessorHandler.class)) {
                IPreprocessorHandler preprocessorHandler = (IPreprocessorHandler) iPreprocessorHandler;
                String pluginName = preprocessorHandler.getPluginName();

                if (pluginName.equalsIgnoreCase("Preprocessor File Reader")) {
                    Preprocessor preprocessor = new Preprocessor().setPreprocessorHandler(preprocessorHandler);
                    preprocessorPipelineElements.add(0, preprocessor);
                }
            }

            for (Object iPreprocessorHandler: pluginLoader.getImplementedExtensions(IPreprocessorHandler.class)) {
                IPreprocessorHandler preprocessorHandler = (IPreprocessorHandler) iPreprocessorHandler;
                String pluginName = preprocessorHandler.getPluginName();

                if (pluginName.equalsIgnoreCase("Preprocessor File Writer")) {
                    Preprocessor preprocessor = new Preprocessor().setPreprocessorHandler(preprocessorHandler);
                    preprocessorPipelineElements.add(preprocessor);
                }
            }
        }
    }

    @Override
    public void willExecute() {
        if (this.getNumberOfPreprocessors() > 0) {
            preprocessorPipelineHook.willExecute();
        }
    }

    public void execute() throws Exception {
        System.out.println("Running preprocessor pipeline...");

        HashMap<File, String> pipeQueue = new HashMap<>();
        HashMap<File, String> nextPipeQueue = new HashMap<>();

        addDefaultReaderAndWriter();

        for (IPreprocessorPipelineElement preprocessorPipelineElement: preprocessorPipelineElements) {
            this.currentlyRunningPreprocessor = preprocessorPipelineElement;

            if (((Preprocessor)preprocessorPipelineElement).getPreprocessorHandler().getPluginName().equalsIgnoreCase("Preprocessor File Reader")) {
                HashMap<File, String> preprocessed = preprocessorPipelineElement.execute(null, null);
                nextPipeQueue.putAll(preprocessed);
                totalDocumentsToBePreprocessed += preprocessed.size();
            } else if (((Preprocessor)preprocessorPipelineElement).getPreprocessorHandler().getPluginName().equalsIgnoreCase("Preprocessor File Writer")) {
                for (Map.Entry<File, String> pair : pipeQueue.entrySet()) {
                    HashMap<File, String> preprocessed = preprocessorPipelineElement.execute(pair.getKey(), pair.getValue());
                    nextPipeQueue.putAll(preprocessed);
                }
            } else {
                this.totalProcessedPreprocessor++;
                Iterator<Map.Entry<File, String>> it = pipeQueue.entrySet().iterator();

                currentlyPreprocessedDocuments = 0;

                while (it.hasNext()) {
                    Map.Entry<File, String> pair = it.next();
                    HashMap<File, String> preprocessed = preprocessorPipelineElement.execute(pair.getKey(), pair.getValue());

                    nextPipeQueue.putAll(preprocessed);
                    currentlyPreprocessedDocuments++;
                }
            }

            pipeQueue = nextPipeQueue;
            nextPipeQueue = new HashMap<>();
        }
    }

    @Override
    public void didExecute() {
        if (this.getNumberOfPreprocessors() > 0) {
            preprocessorPipelineHook.didExecute();
        }
    }

    public IPreprocessorPipelineElement getCurrentlyRunningPreprocessor() {
        return currentlyRunningPreprocessor;
    }

    public int getTotalProcessedPreprocessor() {
        return totalProcessedPreprocessor;
    }

    public int getTotalDocumentsToBePreprocessed() {
        return totalDocumentsToBePreprocessed;
    }

    public int getCurrentlyPreprocessedDocuments() {
        return currentlyPreprocessedDocuments;
    }

    public PreprocessorPipeline setPreprocessorPipelineHook(IPreprocessorPipelineHook preprocessorPipelineHook) {
        this.preprocessorPipelineHook = preprocessorPipelineHook;
        return this;
    }
}
