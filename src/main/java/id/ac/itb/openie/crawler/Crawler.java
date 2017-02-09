package id.ac.itb.openie.crawler;

import com.github.slugify.Slugify;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by elvanowen on 2/9/17.
 */
public class Crawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpeg|jpg|png|mp3|mp3|zip|gz))$");
    public final static String CRAWL_SAVE_DIRECTORY_PATH = "data/crawl/";

    static HashSet<String> getSeedURLs() {
        HashSet<String> urls = new HashSet<String>();

        urls.add("http://www.kompas.com");
        urls.add("http://www.detik.com");

        return urls;
    }

    Boolean shouldFollowURL(String url) {

        // If url is seed then allow
        for (String seedURL: getSeedURLs()) {
            if (url.equalsIgnoreCase(seedURL)) {
                return true;
            }
        }

        HashSet<Boolean> mustConditions = new HashSet<Boolean>();
        HashSet<Boolean> shouldConditions = new HashSet<Boolean>();
        HashMap<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();

        map.put("kompas.com", new HashSet<String>(
                Arrays.asList(
                        "kompas.com/read"
                ))
        );
        map.put("detik.com", new HashSet<String>(
                Arrays.asList(
                        "detik.com/read",
                        "detik.com/berita"
                ))
        );

        Iterator<Map.Entry<String, HashSet<String>>> mapItr = map.entrySet().iterator();

        while (mapItr.hasNext()) {
            Map.Entry<String, HashSet<String>> entry = mapItr.next();

            if (url.contains(entry.getKey().toString())) {
                Iterator<String> itr2 = entry.getValue().iterator();

                while (itr2.hasNext()) {
                    String validSubURL = itr2.next();
                    shouldConditions.add(url.contains(validSubURL));
                }
            }
        }

        mustConditions.add(!FILTERS.matcher(url).matches());

        return !mustConditions.contains(Boolean.FALSE) && shouldConditions.contains(Boolean.TRUE);
    }

    String getContentClassname(String page) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("kompas.com", "kcm-read-text");
        map.put("detik.com", "detail_text");

        for (Object o : map.entrySet()) {
            Map.Entry pair = (Map.Entry) o;

            if (page.contains(pair.getKey().toString())) {
                return pair.getValue().toString();
            }
        }

        return "";
    }

    void writeToFile(String filename, String content) {
        // Make sure filename is valid
        Slugify slg = new Slugify();
        filename = slg.slugify(filename);

        File directoryPath = new File(CRAWL_SAVE_DIRECTORY_PATH);

        // Create directory if not exist
        if (!directoryPath.exists()) {
            try{
                directoryPath.mkdir();
            } catch(SecurityException e){
                System.err.println(e);
            }
        }

        // Write to file
        try{
            PrintWriter writer = new PrintWriter(CRAWL_SAVE_DIRECTORY_PATH + filename + ".txt", "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String targetHref = url.getURL().toLowerCase();
        String referringHref = referringPage.getWebURL().getURL().toLowerCase();

        System.out.println(referringHref + " -> " + targetHref);

        return shouldFollowURL(targetHref);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();

            Document doc = Jsoup.parse(html);

            Elements contents = doc.getElementsByClass(getContentClassname(url));
            for (Element content : contents) {
                String contentText = content.text();
                writeToFile(url, contentText);
            }
        }
    }
}