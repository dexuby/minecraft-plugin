package dev.dexuby.minecraftplugin;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.openapi.application.ApplicationManager;
import dev.dexuby.minecraftplugin.server.type.ServerTypeLoader;
import dev.dexuby.minecraftplugin.server.type.ServerTypeRegistry;

@SuppressWarnings("UnstableApiUsage")
public class PluginLoader implements AppLifecycleListener {

    @Override
    public void appStarted() {

        final ServerTypeRegistry serverTypeRegistry = ApplicationManager.getApplication().getService(ServerTypeRegistry.class);
        new ServerTypeLoader(serverTypeRegistry).loadDefaults(true);

    }

}
