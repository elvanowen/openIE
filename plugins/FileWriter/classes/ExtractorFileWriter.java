package classes;

import id.ac.itb.openie.config.Config;
import id.ac.itb.openie.extractor.IExtractorHandler;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public class ExtractorFileWriter extends Plugin {

    public ExtractorFileWriter(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class ExtractorFileWriterHandler implements IExtractorHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Extractor File Writer";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            availableConfigurations.putIfAbsent("Output Directory", System.getProperty("user.dir") + File.separator + new Config().getProperty("EXTRACTIONS_OUTPUT_RELATIVE_PATH"));

            return availableConfigurations;
        }

        @Override
        public HashMap<File, Pair<String, Relations>> extract(File file, String payload, Relations relations) throws Exception {
            if (getAvailableConfigurations().get("Output Directory") == null) {
                throw new Exception("Write directory path must be specified");
            } else {
                System.out.println("Extractor file writer");
                System.out.println(relations);
                Utilities.writeToFile(availableConfigurations.get("Output Directory"), file.getName(), relations.toString());

                HashMap<File, Pair<String, Relations>> pipelineItems = new HashMap<>();
                pipelineItems.put(file, Pair.of(payload, relations));

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
