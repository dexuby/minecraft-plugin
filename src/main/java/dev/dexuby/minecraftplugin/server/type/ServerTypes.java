package dev.dexuby.minecraftplugin.server.type;

public final class ServerTypes {

    public static final ServerType SPIGOT = new SpigotServerType();
    public static final ServerType PAPER = new PaperServerType();

    public static ServerType[] DEFAULTS = new ServerType[]{
            SPIGOT,
            PAPER
    };

    private ServerTypes() {

        throw new UnsupportedOperationException();

    }

}
