package id.ac.itb.openie.preprocess;

import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/22/17.
 */
public class KompasDocumentPreprocessor implements IPreprocessorPipelineElement {

    /* Function  : Remove document metadata */
    /* Input     : (Baca juga: Ada Usul ... Tahan Diri) Fraksi Partai Demokrat... */
    /* Output    : Fraksi Partai Demokrat... */
    protected String removeLinkToAnotherArticle(String fileContent) {
        return fileContent.replaceAll("(\\(Baca.*?\\))","").replaceAll("(\\(BACA.*?\\))","");
    }

    /* Function  : Remove document metadata */
    /* Input     : Jakarta, KOMPAS.com - PT Bank Mandiri (Persero) Tbk... */
    /* Output    : PT Bank Mandiri (Persero) Tbk... */
    private String removeMetadata(String fileContent) {
        return fileContent.replaceFirst("(^.*?KOMPAS(\\.com)?...)","").trim();
//        return fileContent.replaceFirst("(^.*?[—-]+)","").trim();
    }

    public HashMap<File, String> execute(File file, String payload) throws Exception {
        String preprocessedPayload = removeMetadata(payload);
        preprocessedPayload = removeLinkToAnotherArticle(preprocessedPayload);

        HashMap<File, String> pipelineItems = new HashMap<File, String>();
        pipelineItems.put(file, preprocessedPayload);

        return pipelineItems;
    }
}
