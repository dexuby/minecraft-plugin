package dev.dexuby.minecraftplugin.server;

import dev.dexuby.minecraftplugin.server.paper.PaperServerType;
import dev.dexuby.minecraftplugin.server.spigot.SpigotServerType;

public final class ServerTypes {

    public static final ServerTypeNew SPIGOT = new SpigotServerType();
    public static final ServerTypeNew PAPER = new PaperServerType();

    public static ServerTypeNew[] DEFAULTS = new ServerTypeNew[]{
            SPIGOT,
            PAPER
    };

    private ServerTypes() {

        throw new UnsupportedOperationException();

    }

}
