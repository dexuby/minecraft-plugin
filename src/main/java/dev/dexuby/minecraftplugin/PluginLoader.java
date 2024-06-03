package dev.dexuby.minecraftplugin;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.openapi.application.ApplicationManager;
import dev.dexuby.minecraftplugin.server.ServerTypeNew;
import dev.dexuby.minecraftplugin.server.ServerTypeRegistry;
import dev.dexuby.minecraftplugin.server.ServerTypes;

@SuppressWarnings("UnstableApiUsage")
public class PluginLoader implements AppLifecycleListener {

    @Override
    public void appStarted() {

        final ServerTypeRegistry serverTypeRegistry = ApplicationManager.getApplication().getService(ServerTypeRegistry.class);
        for (final ServerTypeNew serverType : ServerTypes.DEFAULTS) {
            serverTypeRegistry.register(serverType.getId(), serverType);
            serverType.fetchVersions().thenAccept(artifactVersions -> {
                serverType.setVersions(artifactVersions.stream().map(serverType::createServerVersion).toList());
            });
        }

    }

}
