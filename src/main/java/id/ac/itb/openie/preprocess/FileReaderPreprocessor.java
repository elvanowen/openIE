package id.ac.itb.openie.preprocess;

import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by elvanowen on 2/23/17.
 */
public class FileReaderPreprocessor implements IPreProcessorPipelineElement {
    private String readDirectoryPath = null;

    public FileReaderPreprocessor setReadDirectoryPath(String readDirectoryPath) {
        this.readDirectoryPath = readDirectoryPath;
        return this;
    }

    public ArrayList<Pair<File, String>> execute(File file, String payload) throws Exception {
        if (readDirectoryPath == null) {
            throw new Exception("Read directory path must be specified");
        } else {
            ArrayList<File> files = Utilities.getDirectoryFiles(new File(readDirectoryPath));
            ArrayList<Pair<File, String>> pipelineItems = new ArrayList<Pair<File, String>>();

            for (File _file: files) {
                pipelineItems.add(Pair.of(_file, Utilities.getFileContent(_file).get(0)));
            }

            return pipelineItems;
        }
    }
}
