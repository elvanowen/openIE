package id.ac.itb.openie.plugins;

import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;
import ro.fortsoft.pf4j.PluginState;

import java.util.*;

/**
 * Created by elvanowen on 3/1/17.
 */
public class PluginLoader {

    private PluginManager pluginManager = null;
    private HashMap<Class, List> availableExtensions = new HashMap<>();

    public PluginLoader() {
        this.pluginManager = new DefaultPluginManager();
        this.reloadPlugins();
    }

    public void reloadPlugins() {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        this.reRegisterAvailableExtensions();
    }

    public PluginLoader reRegisterAvailableExtensions() {
        for (Map.Entry<Class, List> entry : availableExtensions.entrySet()) {
            availableExtensions.put(entry.getKey(), pluginManager.getExtensions(entry.getKey()));
        }

        return this;
    }

    public PluginLoader registerAvailableExtensions(Class type) {
        availableExtensions.put(type, pluginManager.getExtensions(type));
        return this;
    }

    public List getExtensions(Class type) {
        return availableExtensions.get(type);
    }
}
