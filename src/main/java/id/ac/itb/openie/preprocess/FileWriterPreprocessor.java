package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by elvanowen on 2/23/17.
 */
public class FileWriterPreprocessor implements IPreProcessorPipelineElement {
    private String writeDirectoryPath = null;

    public FileWriterPreprocessor setWriteDirectoryPath(String writeDirectoryPath) {
        this.writeDirectoryPath = writeDirectoryPath;
        return this;
    }

    public ArrayList<Pair<File, String>> execute(File file, String payload) throws Exception {
        if (writeDirectoryPath == null) {
            throw new Exception("Write directory path must be specified");
        } else {
            Utilities.writeToFile(writeDirectoryPath, file.getName(), payload);

            ArrayList<Pair<File, String>> pipelineItems = new ArrayList<Pair<File, String>>();
            pipelineItems.add(Pair.of(file, payload));

            return pipelineItems;
        }
    }
}
