package id.ac.itb.openie.preprocess;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public abstract class IPreprocessorExtensionHandler implements IPreprocessorHandler {

    public HashMap<File, String> read() throws Exception {
        return null;
    }

    public void write(File file, String preprocessed) throws Exception {}
}
