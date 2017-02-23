package id.ac.itb.openie.crawler;

import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by elvanowen on 2/22/17.
 */
public class KompasCrawler extends BaseCrawler {

    protected HashSet<String> getSeedEndpoints() {
        HashSet<String> urls = new HashSet<String>();

        urls.add("http://www.kompas.com");

        return urls;
    }

    protected Boolean shouldFollowLink(String link) {
        return link.contains("kompas.com/read");
    }

    protected Pair<String, String> extractContent(String url, String html) {
        ArrayList<Pair<String, String>> output = new ArrayList<Pair<String, String>>();
        Document doc = Jsoup.parse(html);

        Elements contents = doc.getElementsByClass("kcm-read-text");
        for (Element content : contents) {
            String contentText = content.text();

            return Pair.of(url, contentText);
        }

        return null;
    }
}
