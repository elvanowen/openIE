package id.ac.itb.openie.extractor;

import id.ac.itb.openie.relation.Relations;

import java.io.File;

/**
 * Created by elvanowen on 2/24/17.
 */
public abstract class IExtractorFileHandler implements IExtractorHandler {

    public Relations extract(File file, String document, Relations extracted) throws Exception {
        return null;
    }

}
