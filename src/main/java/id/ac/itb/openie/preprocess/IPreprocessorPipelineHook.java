package id.ac.itb.openie.preprocess;

/**
 * Created by elvanowen on 4/8/17.
 */
public interface IPreprocessorPipelineHook {
    public void willExecute();
    public void didExecute();
}
