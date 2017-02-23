package id.ac.itb.openie.extractor;

import id.ac.itb.openie.preprocess.IPreprocessorPipelineElement;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/23/17.
 */
public class ExtractorFileWriter implements IExtractorPipelineElement {
    private String writeDirectoryPath = null;

    public ExtractorFileWriter setWriteDirectoryPath(String writeDirectoryPath) {
        this.writeDirectoryPath = writeDirectoryPath;
        return this;
    }

    public HashMap<File, Pair<String, Relations>> execute(File file, String payload, Relations relations) throws Exception {
        if (writeDirectoryPath == null) {
            throw new Exception("Write directory path must be specified");
        } else {
            Utilities.writeToFile(writeDirectoryPath, file.getName(), relations.toString());

            HashMap<File, Pair<String, Relations>> pipelineItems = new HashMap<File, Pair<String, Relations>>();
            pipelineItems.put(file, Pair.of(payload, relations));

            return pipelineItems;
        }
    }
}
