package id.ac.itb.openie.extractor;

import id.ac.itb.openie.relations.Relations;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/23/17.
 */
public interface IExtractorPipelineElement {
    public HashMap<File, Pair<String, Relations>> execute(File file, String payload, Relations relations) throws Exception;
}
