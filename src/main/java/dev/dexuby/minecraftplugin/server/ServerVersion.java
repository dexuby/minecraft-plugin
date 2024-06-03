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

    public static String extractShortVersion(@NotNull final String version) {

        final int dashIndex = version.indexOf('-');
        if (dashIndex == -1)
            throw new IllegalArgumentException("Invalid version string: " + version);

        return version.substring(0, dashIndex);

    }

    public static String extractPluginTarget(@NotNull final String version) {

        final String shortVersion = extractShortVersion(version);
        final String[] versionParts = shortVersion.split("\\.");
        String shorterVersion;
        if (versionParts.length >= 3) {
            shorterVersion = versionParts[0] + "." + versionParts[1];
        } else {
            shorterVersion = shortVersion;
        }

        return shorterVersion;

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

        final String[] fromParts = shortVersionFrom.split("\\.");
        if (fromParts.length < 3)
            throw new IllegalArgumentException("Semantic format only (major.minor.patch).");

        final int start = Integer.parseInt(fromParts[fromParts.length - 1]);
        final String[] toParts = shortVersionTo.split("\\.");
        if (fromParts.length != toParts.length)
            throw new IllegalArgumentException("The version format of the provided versions does not match (major.minor.patch).");
        final int end = Integer.parseInt(toParts[toParts.length - 1]);

        final List<ServerVersion> versions = new ArrayList<>();
        int counter = (end - start);
        while (counter >= 0) {
            versions.add(factory.apply(fromParts[0] + "." + fromParts[1] + "." + (end - counter)));
            counter--;
        }

        return versions;

    }

}
