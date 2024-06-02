package dev.dexuby.minecraftplugin.server;

import org.jetbrains.annotations.NotNull;

public record Repository(@NotNull String id, @NotNull String url) {

    public static final Repository SPIGOT_REPO = new Repository("spigot-repo", "https://hub.spigotmc.org/nexus/content/repositories/snapshots/");
    public static final Repository PAPER_REPO = new Repository("papermc", "https://papermc.io/repo/repository/maven-public/");

}
