package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.postprocess.IPostprocessorHandler;
import id.ac.itb.openie.relations.Relations;
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
public class PostprocessorFileReader extends Plugin {

    public PostprocessorFileReader(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class PostprocessorFileReaderHandler implements IPostprocessorHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Postprocessor File Reader";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            availableConfigurations.putIfAbsent("Input Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("EXTRACTIONS_OUTPUT_RELATIVE_PATH"));

            return availableConfigurations;
        }

        @Override
        public HashMap<File, Relations> postprocess(File file, Relations relations) throws Exception {
            if (getAvailableConfigurations().get("Input Directory") == null) {
                throw new Exception("Read directory path must be specified");
            } else {
                HashMap<File, Relations> pipelineItems = new HashMap<>();

                for (String inputDir: availableConfigurations.get("Input Directory").split(":")) {
                    ArrayList<File> files = Utilities.getDirectoryFiles(new File(inputDir));

                    for (File _file: files) {
                        pipelineItems.put(_file, new Relations(_file));
                    }
                }

                return pipelineItems;
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
