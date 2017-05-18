package id.ac.itb.openie.crawler;

/**
 * Created by elvanowen on 2/24/17.
 */
public abstract class ICrawlerExtensionHandler implements ICrawlerHandler {

    public String toString() {
        return this.getPluginName();
    }

}
