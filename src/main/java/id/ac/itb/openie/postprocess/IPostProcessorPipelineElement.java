package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/23/17.
 */
public interface IPostprocessorPipelineElement {
    public HashMap<File, Relations> execute(File file, Relations relations) throws Exception;
}
