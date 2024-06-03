package dev.dexuby.minecraftplugin.server;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ServerTypeRegistry {

    ServerTypeNew get(@NotNull final String id);

    void register(@NotNull final String id, @NotNull final ServerTypeNew serverType);

    void unregister(@NotNull final String id);

    List<String> getIds();

}
