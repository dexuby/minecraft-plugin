package dev.dexuby.minecraftplugin.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for combining collections. This class should not be initialized, attempting to do so anyway utilizing
 * reflection will throw an {@link UnsupportedOperationException}.
 */

public final class Combine {

    /**
     * Safely combines all elements of the provided lists into a new {@link ArrayList} matching the exact size of all
     * provided lists. Safely in this context means that type and <code>null</code> checks are getting performed during
     * the process. If a type mismatch is found an {@link IllegalArgumentException} will be thrown.
     *
     * @param first  The first list.
     * @param second The second list.
     * @param <T>    The type.
     * @return The new list containing the combined elements.
     */

    public static <T> List<T> list(@NotNull final List<T> first, @NotNull final List<T> second) {

        return safe(first, second);

    }

    /**
     * Safely combines all elements of the provided lists into a new {@link ArrayList} matching the exact size of all
     * provided lists. Safely in this context means that type and <code>null</code> checks are getting performed during
     * the process. If a type mismatch is found an {@link IllegalArgumentException} will be thrown.
     *
     * @param first  The first list.
     * @param second The second list.
     * @param third  The third list.
     * @param <T>    The type.
     * @return The new list containing the combined elements.
     */

    public static <T> List<T> list(@NotNull final List<T> first, @NotNull final List<T> second, @NotNull final List<T> third) {

        return safe(first, second, third);

    }

    /**
     * Safely combines all elements of the provided lists into a new {@link ArrayList} matching the exact size of all
     * provided lists. Safely in this context means that type and <code>null</code> checks are getting performed during
     * the process. If a type mismatch is found an {@link IllegalArgumentException} will be thrown.
     *
     * @param first  The first list.
     * @param second The second list.
     * @param third  The third list.
     * @param fourth The fourth list.
     * @param <T>    The type.
     * @return The new list containing the combined elements.
     */

    public static <T> List<T> list(@NotNull final List<T> first, @NotNull final List<T> second, @NotNull final List<T> third, @NotNull final List<T> fourth) {

        return safe(first, second, third, fourth);

    }

    /**
     * Safely combines all elements of the provided lists into a new {@link ArrayList} matching the exact size of all
     * provided lists. Safely in this context means that type and <code>null</code> checks are getting performed during
     * the process. If a type mismatch is found an {@link IllegalArgumentException} will be thrown.
     *
     * @param first  The first list.
     * @param second The second list.
     * @param third  The third list.
     * @param fourth The fourth list.
     * @param fifth  The fifth list.
     * @param <T>    The type.
     * @return The new list containing the combined elements.
     */

    public static <T> List<T> list(@NotNull final List<T> first, @NotNull final List<T> second, @NotNull final List<T> third, @NotNull final List<T> fourth, @NotNull final List<T> fifth) {

        return safe(first, second, third, fourth, fifth);

    }

    /**
     * Safely combines all elements of the provided lists into a new {@link ArrayList} matching the exact size of all
     * provided lists. Safely in this context means that type and <code>null</code> checks are getting performed during
     * the process. If a type mismatch is found an {@link IllegalArgumentException} will be thrown.
     *
     * @param lists The lists.
     * @param <T>   The type.
     * @return The new list containing the combined elements.
     */

    @SafeVarargs
    public static <T> List<T> safe(@NotNull final List<T>... lists) {

        int totalSize = 0;
        Class<?> type = null;
        for (final List<T> list : lists) {
            if (list.isEmpty()) continue;
            if (type != null) {
                totalSize += list.size();
                continue;
            }
            final T item = list.get(0);
            Objects.requireNonNull(item);
            type = item.getClass();
            totalSize += list.size();
        }

        final List<T> output = new ArrayList<>(totalSize);
        if (type == null)
            return output;

        for (final List<T> list : lists) {
            for (final T item : list) {
                Objects.requireNonNull(item);
                if (!type.isInstance(item))
                    throw new IllegalArgumentException("Type mismatch - required: " + type.getName() + " have: " + item.getClass().getName());
                output.add(item);
            }
        }

        return output;

    }

    private Combine() {

        throw new UnsupportedOperationException();

    }

}
