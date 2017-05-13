package id.ac.itb.openie.extractor;

import id.ac.itb.openie.relation.Relations;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public abstract class IExtractorExtensionHandler implements IExtractorHandler {

    public HashMap<File, Pair<String, Relations>> read() throws Exception {
        return null;
    }

    public void write(File file, Relations extracted) throws Exception {}

}
