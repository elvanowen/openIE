package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PreprocessorFileWriter implements IPreprocessorPipelineElement {
    private String writeDirectoryPath = null;

    public PreprocessorFileWriter setWriteDirectoryPath(String writeDirectoryPath) {
        this.writeDirectoryPath = writeDirectoryPath;
        return this;
    }

    public HashMap<File, String> execute(File file, String payload) throws Exception {
        if (writeDirectoryPath == null) {
            throw new Exception("Write directory path must be specified");
        } else {
            Utilities.writeToFile(writeDirectoryPath, file.getName(), payload);

            HashMap<File, String> pipelineItems = new HashMap<File, String>();
            pipelineItems.put(file, payload);

            return pipelineItems;
        }
    }
}
