package dev.dexuby.minecraftplugin;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.openapi.application.ApplicationManager;
import dev.dexuby.minecraftplugin.server.type.ServerTypeLoader;
import dev.dexuby.minecraftplugin.server.type.ServerTypeRegistry;

/**
 * Initializes the plugin by populating the {@link ServerTypeRegistry} with all available server versions from the
 * default server types. This process is non-blocking and should be started now to avoid IDE freezes when the
 * actual wizard gets opened.
 * <p>
 * While {@link AppLifecycleListener} is annotated as internal it's still fine to use it here and the recommended way
 * based on the JetBrains docs if plugin load actions can't be avoided.
 */

@SuppressWarnings("UnstableApiUsage")
public class PluginLoader implements AppLifecycleListener {

    @Override
    public void appStarted() {

        final ServerTypeRegistry serverTypeRegistry = ApplicationManager.getApplication().getService(ServerTypeRegistry.class);
        new ServerTypeLoader(serverTypeRegistry).loadDefaults(true);

    }

}
