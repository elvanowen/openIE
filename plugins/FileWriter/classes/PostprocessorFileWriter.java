package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.postprocess.IPostprocessorHandler;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public class PostprocessorFileWriter extends Plugin {

    public PostprocessorFileWriter(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class PostprocessorFileWriterHandler implements IPostprocessorHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Postprocessor File Writer";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            availableConfigurations.putIfAbsent("Output Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("POSTPROCESSES_OUTPUT_RELATIVE_PATH"));

            return availableConfigurations;
        }

        @Override
        public void postprocessorWillRun() {

        }

        @Override
        public void postprocessorDidRun() {

        }

        @Override
        public HashMap<File, Relations> postprocess(File file, Relations relations) throws Exception {
            if (getAvailableConfigurations().get("Output Directory") == null) {
                throw new Exception("Write directory path must be specified");
            } else {
                if (relations != null) {
                    Utilities.writeToFile(availableConfigurations.get("Output Directory"), file.getName(), relations.toString());
                } else {
                    Utilities.writeToFile(availableConfigurations.get("Output Directory"), file.getName(), "");
                }

                HashMap<File, Relations> pipelineItems = new HashMap<>();
                pipelineItems.put(file, relations);

                return pipelineItems;
            }
        }

        public String toString() {
            return this.getPluginName();
        }

    }
}
