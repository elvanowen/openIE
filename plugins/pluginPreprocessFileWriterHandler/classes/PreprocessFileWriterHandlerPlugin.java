package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.preprocess.IPreprocessorHandler;
import id.ac.itb.openie.utils.Utilities;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public class PreprocessFileWriterHandlerPlugin extends Plugin {

    public PreprocessFileWriterHandlerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class PreprocessorFileWriterHandler implements IPreprocessorHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "File Writer";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            availableConfigurations.putIfAbsent("Output Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("PREPROCESSES_OUTPUT_RELATIVE_PATH"));

            return availableConfigurations;
        }

        @Override
        public HashMap<File, String> preprocess(File file, String payload) throws Exception {
            if (getAvailableConfigurations().get("Output Directory") == null) {
                throw new Exception("Write directory path must be specified");
            } else {
                Utilities.writeToFile(availableConfigurations.get("Output Directory"), file.getName(), payload);

                HashMap<File, String> pipelineItems = new HashMap<File, String>();
                pipelineItems.put(file, payload);

                return pipelineItems;
            }
        }

        public String toString() {
            return this.getPluginName();
        }

        @Override
        public void preprocessorWillRun() {

        }

        @Override
        public void preprocessorDidRun() {

        }
    }
}
