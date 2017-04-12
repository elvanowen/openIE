package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.preprocess.IPreprocessorHandler;
import id.ac.itb.openie.utils.Utilities;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public class PreprocessorFileReader extends Plugin {

    public PreprocessorFileReader(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class PreprocessorFileReaderHandler implements IPreprocessorHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Preprocessor File Reader";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            availableConfigurations.putIfAbsent("Input Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("CRAWLER_OUTPUT_RELATIVE_PATH"));

            return availableConfigurations;
        }

        @Override
        public HashMap<String, String> setAvailableConfigurations(String key, String value) {
            availableConfigurations.put(key, value);
            return null;
        }

        @Override
        public HashMap<File, String> preprocess(File file, String payload) throws Exception {
            if (getAvailableConfigurations().get("Input Directory") == null) {
                throw new Exception("Read directory path must be specified");
            } else {
                HashMap<File, String> pipelineItems = new HashMap<File, String>();

                for (String inputDir: availableConfigurations.get("Input Directory").split(":")) {
                    ArrayList<File> files = Utilities.getDirectoryFiles(new File(inputDir));

                    for (File _file: files) {
                        pipelineItems.put(_file, Utilities.getFileContent(_file));
                    }
                }

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
