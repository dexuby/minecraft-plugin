package dev.dexuby.minecraftplugin.server;

import dev.dexuby.minecraftplugin.util.Combine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class Versions {

    @NotNull
    public static List<ServerVersion> getVersions(@NotNull final ServerType serverType) {

        switch (serverType) {
            case SPIGOT -> {
                return Combine.list(
                        ServerVersion.ofSpigotRange("1.20.1", "1.20.6"),
                        ServerVersion.ofSpigotRange("1.19.1", "1.19.4")
                );
            }
            case PAPER -> {
                return Combine.list(
                        ServerVersion.ofPaperRange("1.20.1", "1.20.6"),
                        ServerVersion.ofPaperRange("1.19.1", "1.19.4")
                );
            }
            default -> {
                throw new IllegalArgumentException("Server version '" + serverType.name() + "' is invalid.");
            }
        }

    }

    @Nullable
    public static ServerVersion getVersion(@NotNull final ServerType serverType, @NotNull final String id) {

        for (final ServerVersion version : getVersions(serverType)) {
            if (version.id().equals(id))
                return version;
        }

        return null;

    }

    private Versions() {

        throw new UnsupportedOperationException();

    }

}
