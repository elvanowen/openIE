package id.ac.itb.openie.crawler;

/**
 * Created by elvanowen on 4/8/17.
 */
public interface ICrawlerPipelineHook {
    public void willExecute();
    public void didExecute();
}
