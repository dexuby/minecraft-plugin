package dev.dexuby.minecraftplugin.server.paper;

import dev.dexuby.minecraftplugin.server.Repository;
import dev.dexuby.minecraftplugin.server.ServerTypeNew;
import dev.dexuby.minecraftplugin.server.ArtifactVersion;
import dev.dexuby.minecraftplugin.server.ServerVersion;
import org.jetbrains.annotations.NotNull;

import java.net.http.HttpClient;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class PaperServerType extends ServerTypeNew {

    public static final String ID = "Paper";
    public static final Repository REPO = new Repository("papermc", "https://papermc.io/repo/repository/maven-public/");

    private static final String ENDPOINT = "https://repo.papermc.io/service/extdirect";
    private static final String BODY = "{\"action\":\"coreui_Browse\",\"method\":\"read\",\"data\":[{\"repositoryName\":\"maven-public\",\"node\":\"io/papermc/paper/paper-api\"}],\"type\":\"rpc\",\"tid\":10}";

    public PaperServerType() {

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

        return new ServerVersion("paper " + ServerVersion.extractShortVersion(artifactVersion.text()), ServerVersion.extractPluginTarget(artifactVersion.text()), "io.papermc.paper", "paper-api", artifactVersion.text(), this.getRepository());

    }

    @NotNull
    @Override
    public Repository getRepository() {

        return REPO;

    }

}
