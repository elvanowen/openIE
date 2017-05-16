package id.ac.itb.openie.preprocess;

import ro.fortsoft.pf4j.ExtensionPoint;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/23/17.
 */
public interface IPreprocessorPipelineElement extends ExtensionPoint {
    public HashMap<File, String> execute(File file, String payload) throws Exception;
    public HashMap<File, String> read() throws Exception;
    public void write(File file, String preprocessed) throws Exception;
}
