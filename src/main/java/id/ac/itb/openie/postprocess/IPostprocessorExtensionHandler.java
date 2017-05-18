package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.relation.Relations;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public abstract class IPostprocessorExtensionHandler implements IPostprocessorHandler {

    public HashMap<File, Relations> read() throws Exception {
        return null;
    }

    public void write(File file, Relations postprocessed) throws Exception { }

    public String toString() {
        return this.getPluginName();
    }

}
