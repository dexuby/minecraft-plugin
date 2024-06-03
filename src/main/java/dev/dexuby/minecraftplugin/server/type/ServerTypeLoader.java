package dev.dexuby.minecraftplugin.server.type;

import dev.dexuby.minecraftplugin.server.ArtifactVersion;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServerTypeLoader {

    private final ServerTypeRegistry serverTypeRegistry;

    public ServerTypeLoader(@NotNull final ServerTypeRegistry serverTypeRegistry) {

        this.serverTypeRegistry = serverTypeRegistry;

    }

    public void loadDefaults(final boolean async) {

        for (final ServerType serverType : ServerTypes.DEFAULTS) {
            this.serverTypeRegistry.register(serverType.getId(), serverType);
            if (async) {
                serverType.fetchVersions().thenAccept(artifactVersions -> {
                    serverType.setVersions(artifactVersions.stream().map(serverType::createServerVersion).toList());
                });
            } else {
                final List<ArtifactVersion> artifactVersions = serverType.fetchVersions().join();
                serverType.setVersions(artifactVersions.stream().map(serverType::createServerVersion).toList());
            }
        }
        this.serverTypeRegistry.tag();

    }

}
