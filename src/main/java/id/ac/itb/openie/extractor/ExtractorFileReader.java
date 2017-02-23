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
public class ExtractorFileReader implements IExtractorPipelineElement {
    private String readDirectoryPath = null;

    public ExtractorFileReader setReadDirectoryPath(String readDirectoryPath) {
        this.readDirectoryPath = readDirectoryPath;
        return this;
    }

    public HashMap<File, Pair<String, Relations>> execute(File file, String payload, Relations relations) throws Exception {
        if (readDirectoryPath == null) {
            throw new Exception("Read directory path must be specified");
        } else {
            ArrayList<File> files = Utilities.getDirectoryFiles(new File(readDirectoryPath));
            HashMap<File, Pair<String, Relations>> pipelineItems = new HashMap<File, Pair<String, Relations>>();

            for (File _file: files) {
                pipelineItems.put(_file, Pair.of(Utilities.getFileContent(_file).get(0), (Relations)null));
            }

            return pipelineItems;
        }
    }
}
