package classes;

import id.ac.itb.openie.postprocess.IPostprocessorExtensionHandler;
import id.ac.itb.openie.relation.Relations;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.HashMap;

public class Postprocessor extends Plugin {

    public Postprocessor(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class PostprocessorHandler extends IPostprocessorExtensionHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            /* Return postprocessor name e.g. `Relation Validation Filter` */
            return "";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            return null;
        }

        @Override
        public void setAvailableConfigurations(String key, String value) {
            availableConfigurations.put(key, value);
        }

        @Override
        public Relations postprocess(Relations relations, Relations postprocessed) throws Exception {
            /* TODO: Postprocessed relations, e.g. filter valid relations */
            return postprocessed;
        }

        @Override
        public void postprocessorWillRun() {
            /* TODO: before postprocessor start crawling */
        }

        @Override
        public void postprocessorDidRun() {
            /* TODO: after postprocessor finish crawling */
        }

        public String toString() {
            return this.getPluginName();
        }

    }
}
