package id.ac.itb.openie.pipeline;

/**
 * Created by elvanowen on 2/23/17.
 */
public interface IOpenIePipelineElement {
    public void willExecute();
    public void execute() throws Exception;
    public void didExecute();
}
