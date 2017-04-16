package classes;

import id.ac.itb.nlp.NamedEntityTagger;
import id.ac.itb.nlp.PhraseChunker;
import id.ac.itb.openie.postprocess.IPostprocessorHandler;
import id.ac.itb.openie.relation.Relation;
import id.ac.itb.openie.relation.Relations;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.HashMap;

/**
 * Created by elvanowen on 2/24/17.
 */
public class RelationValidationFilter extends Plugin {

    public RelationValidationFilter(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class RelationValidationFilterHandler implements IPostprocessorHandler {

        HashMap<String, String> availableConfigurations = new HashMap<>();

        public String getPluginName() {
            return "Relation Validation Filter";
        }

        @Override
        public HashMap<String, String> getAvailableConfigurations() {
            return null;
        }

        @Override
        public void postprocessorWillRun() {

        }

        @Override
        public void postprocessorDidRun() {

        }

        @Override
        public HashMap<File, Relations> postprocess(File file, Relations relations) throws Exception {
//            System.out.println("my file");
//            System.out.println(file);
//            System.out.println("my relation");
//            System.out.println(relations);

            PhraseChunker phraseChunker = new PhraseChunker();
            NamedEntityTagger namedEntityTagger = new NamedEntityTagger();

            for (Relation relation: relations.getRelations()) {
//                System.out.println(relation.getOriginSentence());
//                System.out.println(phraseChunker.chunk(relation.getOriginSentence()).get(0)[0]);
//                System.out.println(phraseChunker.chunk(relation.getOriginSentence()).get(0)[1]);

                System.out.println(namedEntityTagger.tag(relation.getOriginSentence()));
            }

            HashMap<File, Relations> pipelineItems = new HashMap<>();
            pipelineItems.put(file, relations);

            return pipelineItems;
        }

        public String toString() {
            return this.getPluginName();
        }

    }
}
