package id.ac.itb.openie.pipeline;

import java.util.ArrayList;

/**
 * Created by elvanowen on 2/23/17.
 */
public class OpenIePipeline {

    private ArrayList<IOpenIePipelineElement> openIePipelineElements = new ArrayList<IOpenIePipelineElement>();

    public OpenIePipeline addPipelineElement(IOpenIePipelineElement openIePipelineElements) {
        this.openIePipelineElements.add(openIePipelineElements);
        return this;
    }

    public void clear() {
        this.openIePipelineElements = new ArrayList<>();
    }

    public void execute() throws Exception {
        System.out.println("Running OpenIE pipeline...");

        for (IOpenIePipelineElement openIePipelineElement: openIePipelineElements) {
            openIePipelineElement.execute();
        }
    }
}
