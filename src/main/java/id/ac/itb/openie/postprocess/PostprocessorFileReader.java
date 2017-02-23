package id.ac.itb.openie.postprocess;

import id.ac.itb.openie.relations.Relation;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by elvanowen on 2/23/17.
 */
public class PostprocessorFileReader implements IPostprocessorPipelineElement {
    private String readDirectoryPath = null;

    public PostprocessorFileReader setReadDirectoryPath(String readDirectoryPath) {
        this.readDirectoryPath = readDirectoryPath;
        return this;
    }

    public HashMap<File, Relations> execute(File file, Relations relations) throws Exception {
        if (readDirectoryPath == null) {
            throw new Exception("Read directory path must be specified");
        } else {
            ArrayList<File> files = Utilities.getDirectoryFiles(new File(readDirectoryPath));
            HashMap<File, Relations> pipelineItems = new HashMap<File, Relations>();

            for (File _file: files) {
                ArrayList<String> _relations = Utilities.getFileContent(_file);
                Relations aRelations = new Relations();

                for (String _relation: _relations) {
                    ArrayList<String> terms = new ArrayList<String>();

                    String relationPattern = "^<(.*?)\\|\\|(.*?)\\|\\|(.*?)\\>$";
                    Pattern pattern = Pattern.compile(relationPattern);
                    Matcher matcher = pattern.matcher(_relation);

                    while(matcher.find()) {
                        terms.add(matcher.group(1));
                        terms.add(matcher.group(2));
                        terms.add(matcher.group(3));

                        System.out.println("found: ");
                        System.out.println(terms);
                    }

                    aRelations.addRelation(new Relation(terms.get(0), terms.get(1), terms.get(2)));
                }

                pipelineItems.put(_file, aRelations);
            }

            return pipelineItems;
        }
    }
}
