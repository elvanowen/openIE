package id.ac.itb.openie.extractor;

import id.ac.itb.openie.pipeline.IOpenIePipelineElement;
import id.ac.itb.openie.plugins.PluginLoader;
import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by elvanowen on 2/23/17.
 */
public class ExtractorPipeline implements IOpenIePipelineElement {

    private ArrayList<IExtractorPipelineElement> extractorPipelineElements = new ArrayList<IExtractorPipelineElement>();
    private int totalProcessedExtractor = 0;
    private int totalDocumentsToBeExtracted = 0;
    private int currentlyExtractedDocuments = 0;
    private IExtractorPipelineElement currentlyRunningExtractor = null;
    private IExtractorPipelineHook extractorPipelineHook = null;

    public ExtractorPipeline addPipelineElement(IExtractorPipelineElement extractorPipelineElement) {
        extractorPipelineElements.add(extractorPipelineElement);
        return this;
    }

    public ArrayList<IExtractorPipelineElement> getExtractorPipelineElements() {
        return this.extractorPipelineElements;
    }

    public int getNumberOfExtractors() {
        int n = 0;

        for (IExtractorPipelineElement extractorPipelineElement: extractorPipelineElements) {
            if (((Extractor)extractorPipelineElement).getExtractorHandler().getPluginName().equalsIgnoreCase("Extractor File Reader")) {
                continue;
            } else if (((Extractor)extractorPipelineElement).getExtractorHandler().getPluginName().equalsIgnoreCase("Extractor File Writer")) {
                continue;
            } else {
                n++;
            }
        }

        return n;
    }

    private void addReaderAndWriterIfNotExist() {
        if (extractorPipelineElements.size() > 0) {
            PluginLoader pluginLoader = new PluginLoader();
            pluginLoader.registerAvailableExtensions(IExtractorHandler.class);

            // Prepend preprocessor file reader if not exist
            if (!((Extractor) extractorPipelineElements.get(0)).getExtractorHandler().getPluginName().equalsIgnoreCase("Extractor File Reader")) {
                for (Object iExtractorHandler: pluginLoader.getExtensions(IExtractorHandler.class)) {
                    IExtractorHandler extractorHandler = (IExtractorHandler) iExtractorHandler;
                    String pluginName = extractorHandler.getPluginName();

                    if (pluginName.equalsIgnoreCase("Extractor File Reader")) {
                        Extractor extractor = new Extractor().setExtractorHandler(extractorHandler);
                        extractorPipelineElements.add(0, extractor);
                    }
                }
            }

            if (!((Extractor) extractorPipelineElements.get(extractorPipelineElements.size() - 1)).getExtractorHandler().getPluginName().equalsIgnoreCase("Extractor File Writer")) {
                for (Object iExtractorHandler: pluginLoader.getExtensions(IExtractorHandler.class)) {
                    IExtractorHandler extractorHandler = (IExtractorHandler) iExtractorHandler;
                    String pluginName = extractorHandler.getPluginName();

                    if (pluginName.equalsIgnoreCase("Extractor File Writer")) {
                        Extractor extractor = new Extractor().setExtractorHandler(extractorHandler);
                        extractorPipelineElements.add(extractor);
                    }
                }
            }
        }
    }

    @Override
    public void willExecute() {
        if (this.getNumberOfExtractors() > 0) {
            extractorPipelineHook.willExecute();
        }
    }

    public void execute() throws Exception {
        System.out.println("Running extractor pipeline...");

        addReaderAndWriterIfNotExist();

        HashMap<File, Pair<String, Relations>> pipeQueue = new HashMap<>();
        HashMap<File, Pair<String, Relations>> nextPipeQueue = new HashMap<>();

        for (IExtractorPipelineElement extractorPipelineElement: extractorPipelineElements) {
            this.currentlyRunningExtractor = extractorPipelineElement;

            if (((Extractor)extractorPipelineElement).getExtractorHandler().getPluginName().equalsIgnoreCase("Extractor File Reader")) {
                HashMap<File, Pair<String, Relations>> extractedRelations = extractorPipelineElement.execute(null, null, null);
                nextPipeQueue.putAll(extractedRelations);
                totalDocumentsToBeExtracted += extractedRelations.size();
            } else if (((Extractor)extractorPipelineElement).getExtractorHandler().getPluginName().equalsIgnoreCase("Extractor File Writer")) {
                for (Map.Entry<File, Pair<String, Relations>> pair : pipeQueue.entrySet()) {
                    HashMap<File, Pair<String, Relations>> extractedRelations = extractorPipelineElement.execute(pair.getKey(), pair.getValue().getLeft(), pair.getValue().getRight());
                    nextPipeQueue.putAll(extractedRelations);
                }
            } else {
                this.totalProcessedExtractor++;

                Iterator<Map.Entry<File, Pair<String, Relations>>> it = pipeQueue.entrySet().iterator();

                currentlyExtractedDocuments = 0;

                while (it.hasNext()) {
                    Map.Entry<File, Pair<String, Relations>> pair = it.next();
                    HashMap<File, Pair<String, Relations>> preprocessed = extractorPipelineElement.execute(pair.getKey(), pair.getValue().getLeft(), pair.getValue().getRight());

                    nextPipeQueue.putAll(preprocessed);
                    currentlyExtractedDocuments++;
                }
            }

            pipeQueue = nextPipeQueue;
            nextPipeQueue = new HashMap<>();
        }
    }

    @Override
    public void didExecute() {
        if (this.getNumberOfExtractors() > 0) {
            extractorPipelineHook.didExecute();
        }
    }

    public int getTotalProcessedExtractor() {
        return totalProcessedExtractor;
    }

    public int getTotalDocumentsToBeExtracted() {
        return totalDocumentsToBeExtracted;
    }

    public int getCurrentlyExtractedDocuments() {
        return currentlyExtractedDocuments;
    }

    public IExtractorPipelineElement getCurrentlyRunningExtractor() {
        return currentlyRunningExtractor;
    }

    public ExtractorPipeline setExtractorPipelineHook(IExtractorPipelineHook extractorPipelineHook) {
        this.extractorPipelineHook = extractorPipelineHook;
        return this;
    }
}
