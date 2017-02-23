package id.ac.itb.openie.preprocess;

import org.apache.commons.lang3.tuple.Pair;
import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/23/17.
 */
public interface IPreprocessorPipelineElement extends ExtensionPoint {
    public HashMap<File, String> execute(File file, String payload) throws Exception;
}
