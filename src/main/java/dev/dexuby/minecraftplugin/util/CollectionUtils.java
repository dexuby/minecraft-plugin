package dev.dexuby.minecraftplugin.util;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CollectionUtils {

    private CollectionUtils() {

        throw new UnsupportedOperationException();

    }

    public static <T> List<T> combineLists(@NotNull final List<T> first,
                                           @NotNull final List<T> second) {

        first.addAll(second);

        return first;

    }

    public static <T> List<T> combineLists(@NotNull final List<T> first,
                                           @NotNull final List<T> second,
                                           @NotNull final List<T> third) {

        first.addAll(second);
        first.addAll(third);

        return first;

    }

    public static <T> List<T> combineLists(@NotNull final List<T> first,
                                           @NotNull final List<T> second,
                                           @NotNull final List<T> third,
                                           @NotNull final List<T> fourth) {

        first.addAll(second);
        first.addAll(third);
        first.addAll(fourth);

        return first;

    }

    public static <T> List<T> combineLists(@NotNull final List<T> first,
                                           @NotNull final List<T> second,
                                           @NotNull final List<T> third,
                                           @NotNull final List<T> fourth,
                                           @NotNull final List<T> fifth) {

        first.addAll(second);
        first.addAll(third);
        first.addAll(fourth);
        first.addAll(fifth);

        return first;

    }

}
