package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.postprocess.IPostprocessorFileHandler;
import id.ac.itb.openie.relation.Relations;
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
    public static class PostprocessorFileWriterHandler extends IPostprocessorFileHandler {

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
        public void setAvailableConfigurations(String key, String value) {
            availableConfigurations.put(key, value);
        }

        @Override
        public HashMap<File, Relations> read() throws Exception {
            return null;
        }

        @Override
        public void write(File file, Relations postprocessed) throws Exception {
            if (getAvailableConfigurations().get("Output Directory") == null) {
                throw new Exception("Write directory path must be specified");
            } else {
                if (postprocessed != null) {
                    Utilities.writeToFile(availableConfigurations.get("Output Directory"), file.getName(), postprocessed.toString());
                } else {
                    Utilities.writeToFile(availableConfigurations.get("Output Directory"), file.getName(), "");
                }
            }
        }

        @Override
        public void postprocessorWillRun() {
            
        }

        @Override
        public void postprocessorDidRun() {

        }

        public String toString() {
            return this.getPluginName();
        }

    }
}
