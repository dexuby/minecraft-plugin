package dev.dexuby.minecraftplugin.server;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record ServerVersion(@NotNull String id,
                            @NotNull String pluginTarget,
                            @NotNull String groupId,
                            @NotNull String artifactId,
                            @NotNull String version,
                            @NotNull Repository repository) {

    public static ServerVersion ofSpigot(@NotNull final String shortVersion) {

        return of(shortVersion, "spigot", "org.spigotmc", Repository.SPIGOT_REPO);

    }

    public static List<ServerVersion> ofSpigotRange(@NotNull final String shortVersionFrom, @NotNull final String shortVersionTo) {

        return ofRange(shortVersionFrom, shortVersionTo, ServerVersion::ofSpigot);

    }

    public static ServerVersion ofPaper(@NotNull final String shortVersion) {

        return of(shortVersion, "paper", "io.papermc.paper", Repository.PAPER_REPO);

    }

    public static List<ServerVersion> ofPaperRange(@NotNull final String shortVersionFrom, @NotNull final String shortVersionTo) {

        return ofRange(shortVersionFrom, shortVersionTo, ServerVersion::ofPaper);

    }

    public static ServerVersion of(@NotNull final String shortVersion, @NotNull final String name, @NotNull final String groupId, @NotNull final Repository repository) {

        final String[] versionParts = shortVersion.split("\\.");
        String shorterVersion;
        if (versionParts.length >= 3) {
            shorterVersion = versionParts[0] + "." + versionParts[1];
        } else {
            shorterVersion = shortVersion;
        }

        return new ServerVersion(name + "-api " + shortVersion, shorterVersion, groupId, name + "-api", shortVersion + "-R0.1-SNAPSHOT", repository);

    }

    public static List<ServerVersion> ofRange(@NotNull final String shortVersionFrom, @NotNull final String shortVersionTo, @NotNull final Function<String, ServerVersion> factory) {

        String[] versionParts = shortVersionFrom.split("\\.");
        if (versionParts.length < 3)
            return List.of(factory.apply(shortVersionFrom));

        final int start = Integer.parseInt(versionParts[versionParts.length - 1]);
        versionParts = shortVersionTo.split("\\.");
        final int end = Integer.parseInt(versionParts[versionParts.length - 1]);

        final List<ServerVersion> versions = new ArrayList<>();
        int counter = (end - start);
        while (counter >= 0) {
            versions.add(factory.apply(versionParts[0] + "." + versionParts[1] + "." + (end - counter)));
            counter--;
        }

        return versions;

    }

}
