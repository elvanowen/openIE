package id.ac.itb.openie.preprocess;

/**
 * Created by elvanowen on 2/24/17.
 */
public abstract class IPreprocessorFileHandler implements IPreprocessorHandler {

    /**
     *
     * @param body String containing original crawled content
     * @param preprocessed String containing preprocessed content from previous preprocessor
     * @return preprocessed body
     * @throws Exception
     */
    public String preprocess(String body, String preprocessed) throws Exception {
        return "";
    }

}
