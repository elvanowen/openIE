package classes;

import id.ac.itb.openie.extractor.IExtractorExtensionHandler;
import id.ac.itb.openie.relation.Relations;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public class ReverbExtractorV2Handler extends Plugin {

    public ReverbExtractorV2Handler(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class ReverbExtractorV2HandlerPlugin extends IExtractorExtensionHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Reverb Extractor V2";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            return availableConfigurations;
        }

        @Override
        public void setAvailableConfigurations(String key, String value) {
            availableConfigurations.put(key, value);
        }

        @Override
        public Relations extract(File file, String document, Relations extracted) throws Exception {
            return new ReverbExtractorV2().extract(file, document, extracted);
        }

        public void extractorWillRun() {
            System.out.println(this.getPluginName() + " will run..");
        }

        public void extractorDidRun() {
            System.out.println(this.getPluginName() + " did run..");
        }

    }
}
