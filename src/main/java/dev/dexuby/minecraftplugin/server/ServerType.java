package dev.dexuby.minecraftplugin.server;

import org.jetbrains.annotations.NotNull;

public enum ServerType {

    SPIGOT("Spigot"),
    PAPER("Paper");

    private final String friendlyId;

    ServerType(@NotNull final String friendlyId) {

        this.friendlyId = friendlyId;

    }

    public String getFriendlyId() {

        return this.friendlyId;

    }

    @NotNull
    public static ServerType getByFriendlyId(@NotNull final String friendlyId, @NotNull final ServerType defaultValue) {

        for (final ServerType serverType : ServerType.values())
            if (serverType.friendlyId.equals(friendlyId))
                return serverType;

        return defaultValue;

    }

}
