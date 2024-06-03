package dev.dexuby.minecraftplugin.server;

import dev.dexuby.minecraftplugin.server.type.ServerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ServerTypeRegistry {

    ServerType get(@NotNull final String id);

    void register(@NotNull final String id, @NotNull final ServerType serverType);

    void unregister(@NotNull final String id);

    List<String> getIds();

    boolean isHot();

    void tag();

}
