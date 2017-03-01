package id.ac.itb.openie.plugins;

import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;

import java.util.List;

/**
 * Created by elvanowen on 3/1/17.
 */
public class PluginLoader<T> {

    private Class<T> type = null;
    private PluginManager pluginManager = null;

    public PluginLoader(Class<T> type) {
        this.type = type;

        this.pluginManager = new DefaultPluginManager();
        this.pluginManager.loadPlugins();
        this.pluginManager.startPlugins();
    }

    public List<T> getExtensions() {
        return pluginManager.getExtensions(type);
    }
}
