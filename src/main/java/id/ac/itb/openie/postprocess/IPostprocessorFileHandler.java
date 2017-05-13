package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.relation.Relations;

/**
 * Created by elvanowen on 2/24/17.
 */
public abstract class IPostprocessorFileHandler implements IPostprocessorHandler {

    public Relations postprocess(Relations relations, Relations postprocessed) throws Exception {
        return null;
    }

}
