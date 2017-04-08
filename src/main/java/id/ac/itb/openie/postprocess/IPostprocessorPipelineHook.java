package id.ac.itb.openie.postprocess;

/**
 * Created by elvanowen on 4/8/17.
 */
public interface IPostprocessorPipelineHook {
    public void willExecute();
    public void didExecute();
}
