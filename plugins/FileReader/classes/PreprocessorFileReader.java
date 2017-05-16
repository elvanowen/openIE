package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.preprocess.IPreprocessorFileHandler;
import id.ac.itb.openie.utils.Utilities;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Created by elvanowen on 2/24/17.
 */
public class PreprocessorFileReader extends Plugin {

    public PreprocessorFileReader(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class PreprocessorFileReaderHandler extends IPreprocessorFileHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Preprocessor File Reader";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            System.out.println("PreprocessorFileReader Input Directory");
            System.out.println(System.getProperty("user.dir") + File.separator + new Config().getProperty("CRAWLER_OUTPUT_RELATIVE_PATH").replaceAll("\\.", Matcher.quoteReplacement(System.getProperty("file.separator"))));
            availableConfigurations.putIfAbsent("Input Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("CRAWLER_OUTPUT_RELATIVE_PATH").replaceAll("\\.", Matcher.quoteReplacement(System.getProperty("file.separator"))));

            return availableConfigurations;
        }

        @Override
        public void setAvailableConfigurations(String key, String value) {
            availableConfigurations.put(key, value);
        }

        @Override
        public HashMap<File, String> read() throws Exception {
            if (getAvailableConfigurations().get("Input Directory") == null) {
                throw new Exception("Read directory path must be specified");
            } else {
                HashMap<File, String> pipelineItems = new HashMap<File, String>();

                ArrayList<File> files = Utilities.getDirectoryFiles(new File(availableConfigurations.get("Input Directory")));

                for (File _file: files) {
                    pipelineItems.put(_file, Utilities.getFileContent(_file));
                }

                return pipelineItems;
            }
        }

        @Override
        public void write(File file, String preprocessed) throws Exception {}

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
