package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.relation.Relations;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/23/17.
 */
public interface IPostprocessorPipelineElement {
    public HashMap<File, Relations> execute(File file, Relations relations) throws Exception;
}
