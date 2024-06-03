package dev.dexuby.minecraftplugin.server.type;

import dev.dexuby.minecraftplugin.server.Repository;
import dev.dexuby.minecraftplugin.server.ArtifactVersion;
import dev.dexuby.minecraftplugin.server.ServerVersion;
import org.jetbrains.annotations.NotNull;

import java.net.http.HttpClient;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class SpigotServerType extends ServerType {

    public static final String ID = "Spigot";
    public static final Repository REPO = new Repository("spigot-repo", "https://hub.spigotmc.org/nexus/content/repositories/snapshots/");

    private static final String ENDPOINT = "https://hub.spigotmc.org/nexus/service/extdirect";
    private static final String BODY = "{\"action\":\"coreui_Browse\",\"method\":\"read\",\"data\":[{\"repositoryName\":\"snapshots\",\"node\":\"org/spigotmc/spigot-api\"}],\"type\":\"rpc\",\"tid\":11}";

    public SpigotServerType() {

        super(HttpClient.newBuilder().build());

    }

    @NotNull
    public String getId() {

        return ID;

    }

    @NotNull
    @Override
    public CompletableFuture<List<ArtifactVersion>> fetchVersions() {

        return super.fetchVersionsNexus(ENDPOINT, BODY);

    }

    @NotNull
    @Override
    public ServerVersion createServerVersion(@NotNull final ArtifactVersion artifactVersion) {

        return new ServerVersion("spigot " + ServerVersion.extractShortVersion(artifactVersion.text()), ServerVersion.extractPluginTarget(artifactVersion.text()), "org.spigotmc", "spigot-api", artifactVersion.text(), this.getRepository());

    }

    @NotNull
    @Override
    public Repository getRepository() {

        return REPO;

    }

}
