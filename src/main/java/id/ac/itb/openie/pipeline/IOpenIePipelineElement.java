package id.ac.itb.openie.pipeline;

import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by elvanowen on 2/23/17.
 */
public interface IOpenIePipelineElement {
    public void execute() throws Exception;
}
