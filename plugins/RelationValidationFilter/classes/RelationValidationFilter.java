package classes;

import id.ac.itb.nlp.NamedEntityTagger;
import id.ac.itb.nlp.PhraseChunker;
import id.ac.itb.openie.postprocess.IPostprocessorExtensionHandler;
import id.ac.itb.openie.relation.Relation;
import id.ac.itb.openie.relation.Relations;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public class RelationValidationFilter extends Plugin {

    public RelationValidationFilter(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class RelationValidationFilterHandler extends IPostprocessorExtensionHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Relation Validation Filter";
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
            PhraseChunker phraseChunker = new PhraseChunker();
            NamedEntityTagger namedEntityTagger = new NamedEntityTagger();

            for (Relation relation: relations.getRelations()) {
                System.out.println(namedEntityTagger.tag(relation.getOriginSentence()));
            }

            return relations;
        }

        @Override
        public void postprocessorWillRun() {

        }

        @Override
        public void postprocessorDidRun() {

        }
    }
}
