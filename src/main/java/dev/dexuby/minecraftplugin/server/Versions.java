package dev.dexuby.minecraftplugin.server;

import dev.dexuby.minecraftplugin.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class Versions {

    private Versions() {

        throw new UnsupportedOperationException();

    }

    @NotNull
    public static List<ServerVersion> getVersions(@NotNull final String serverVersion) {

        switch (serverVersion.toUpperCase()) {
            case "SPIGOT" -> {
                return CollectionUtils.combineLists(
                        ServerVersion.ofSpigotRange("1.20.1", "1.20.6"),
                        ServerVersion.ofSpigotRange("1.19.1", "1.19.4")
                );
            }
            case "PAPER" -> {
                return CollectionUtils.combineLists(
                        ServerVersion.ofPaperRange("1.20.1", "1.20.6"),
                        ServerVersion.ofPaperRange("1.19.1", "1.19.4")
                );
            }
            default -> {
                throw new IllegalArgumentException("Server version '" + serverVersion + "' is invalid.");
            }
        }

    }

    @Nullable
    public static ServerVersion getVersion(@NotNull final String serverVersion, @NotNull final String id) {

        for (final ServerVersion version : getVersions(serverVersion)) {
            if (version.id().equals(id))
                return version;
        }

        return null;

    }

}
