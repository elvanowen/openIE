package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.preprocess.IPreprocessorPipelineElement;
import id.ac.itb.openie.relations.Relation;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PostprocessorFileWriter implements IPostprocessorPipelineElement {
    private String writeDirectoryPath = null;

    public PostprocessorFileWriter setWriteDirectoryPath(String writeDirectoryPath) {
        this.writeDirectoryPath = writeDirectoryPath;
        return this;
    }

    public HashMap<File, Relations> execute(File file, Relations relations) throws Exception {
        if (writeDirectoryPath == null) {
            throw new Exception("Write directory path must be specified");
        } else {
            HashMap<File, Relations> pipelineItems = new HashMap<File, Relations>();

            Utilities.writeToFile(writeDirectoryPath, file.getName(), relations.toString());

            pipelineItems.put(file, relations);

            return pipelineItems;
        }
    }
}
