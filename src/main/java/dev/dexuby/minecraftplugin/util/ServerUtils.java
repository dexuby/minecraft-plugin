package dev.dexuby.minecraftplugin.util;

import dev.dexuby.minecraftplugin.server.ArtifactVersion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ServerUtils {

    private static final Pattern VERSIONS_DATA_PATTERN = Pattern.compile("\"data\":\\[(.*?)]");
    private static final Pattern VERSIONS_ENTRY_PATTERN = Pattern.compile("\"id\":\"(.*?)\",\"text\":\"(.*?)\",\"type\":\"folder\"");

    @NotNull
    public static List<ArtifactVersion> extractVersionsFromResponseData(@NotNull final String responseData) {

        final List<ArtifactVersion> output = new ArrayList<>();
        Matcher matcher = VERSIONS_DATA_PATTERN.matcher(responseData);
        if (matcher.find()) {
            final String entries = matcher.group(1);
            matcher = VERSIONS_ENTRY_PATTERN.matcher(entries);
            while (matcher.find()) {
                final String id = matcher.group(1);
                final String text = matcher.group(2);
                output.add(new ArtifactVersion(id, text));
            }
        }

        return output;

    }

    private ServerUtils() {

        throw new UnsupportedOperationException();

    }

}
