package id.ac.itb.openie.extractor;

/**
 * Created by elvanowen on 4/8/17.
 */
public interface IExtractorPipelineHook {
    public void willExecute();
    public void didExecute();
}
