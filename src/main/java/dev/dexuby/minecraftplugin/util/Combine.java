package dev.dexuby.minecraftplugin.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Combine {

    private Combine() {

        throw new UnsupportedOperationException();

    }

    public static <T> List<T> list(@NotNull final List<T> first, @NotNull final List<T> second) {

        return safe(first, second);

    }

    public static <T> List<T> list(@NotNull final List<T> first, @NotNull final List<T> second, @NotNull final List<T> third) {

        return safe(first, second, third);

    }

    public static <T> List<T> list(@NotNull final List<T> first, @NotNull final List<T> second, @NotNull final List<T> third, @NotNull final List<T> fourth) {

        return safe(first, second, third, fourth);

    }

    public static <T> List<T> list(@NotNull final List<T> first, @NotNull final List<T> second, @NotNull final List<T> third, @NotNull final List<T> fourth, @NotNull final List<T> fifth) {

        return safe(first, second, third, fourth, fifth);

    }

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

}
