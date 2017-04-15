package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.extractor.IExtractorHandler;
import id.ac.itb.openie.relation.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public class ExtractorFileReader extends Plugin {

    public ExtractorFileReader(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class ExtractorFileReaderHandler implements IExtractorHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Extractor File Reader";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            availableConfigurations.putIfAbsent("Input Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("PREPROCESSES_OUTPUT_RELATIVE_PATH"));

            return availableConfigurations;
        }

        @Override
        public HashMap<File, Pair<String, Relations>> extract(File file, String payload, Relations relations) throws Exception {
            if (getAvailableConfigurations().get("Input Directory") == null) {
                throw new Exception("Read directory path must be specified");
            } else {
                HashMap<File, Pair<String, Relations>> pipelineItems = new HashMap<>();

                for (String inputDir: availableConfigurations.get("Input Directory").split(":")) {
                    ArrayList<File> files = Utilities.getDirectoryFiles(new File(inputDir));

                    for (File _file: files) {
                        pipelineItems.put(_file, Pair.of(Utilities.getFileContent(_file), null));
                    }
                }

                return pipelineItems;
            }
        }

        @Override
        public void extractorWillRun() {

        }

        @Override
        public void extractorDidRun() {

        }

        public String toString() {
            return this.getPluginName();
        }
    }
}
