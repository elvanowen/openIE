package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PreprocessorFileReader implements IPreprocessorPipelineElement {
    private String readDirectoryPath = null;

    public PreprocessorFileReader setReadDirectoryPath(String readDirectoryPath) {
        this.readDirectoryPath = readDirectoryPath;
        return this;
    }

    public HashMap<File, String> execute(File file, String payload) throws Exception {
        if (readDirectoryPath == null) {
            throw new Exception("Read directory path must be specified");
        } else {
            ArrayList<File> files = Utilities.getDirectoryFiles(new File(readDirectoryPath));
            HashMap<File, String> pipelineItems = new HashMap<File, String>();

            for (File _file: files) {
                pipelineItems.put(_file, Utilities.getFileContent(_file).get(0));
            }

            return pipelineItems;
        }
    }
}
