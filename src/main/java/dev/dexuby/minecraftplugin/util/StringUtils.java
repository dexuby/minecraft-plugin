package dev.dexuby.minecraftplugin.util;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class StringUtils {

    /**
     * Tames the provided artifact id by replacing certain characters to make it valid and naming convention compatible.
     * Currently only replaces dash ('-') characters with underscores ('_').
     *
     * @param artifactId The input artifact id.
     * @return The updated artifact id.
     */

    public static String tameArtifactId(@NotNull final String artifactId) {

        return artifactId.replace('-', '_');

    }

    /**
     * Applies the provided properties to the provided input string, property identifiers in the provided input string
     * have to be surrounded with percent signs ('%') in order to be identifiable.
     *
     * @param input      The input string.
     * @param properties The properties.
     * @return The updated string.
     */

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

    private StringUtils() {

        throw new UnsupportedOperationException();

    }

}
