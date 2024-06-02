package dev.dexuby.minecraftplugin.util;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class StringUtils {

    private StringUtils() {

        throw new UnsupportedOperationException();

    }

    public static String tameArtifactId(@NotNull final String artifactId) {

        return artifactId.replace('-', '_');

    }

    public static String applyProperties(@NotNull final String input, @NotNull final Map<String, String> properties) {

        boolean foundProperty = false;
        final StringBuilder output = new StringBuilder();
        final StringBuilder property = new StringBuilder();
        for (final char c : input.toCharArray()) {
            if (c == '%') {
                if (foundProperty) {
                    output.append(properties.getOrDefault(property.toString(), ""));
                    property.setLength(0);
                    foundProperty = false;
                } else {
                    foundProperty = true;
                }
            } else {
                if (foundProperty) {
                    property.append(c);
                } else {
                    output.append(c);
                }
            }
        }

        return output.toString();

    }

}
