package dev.dexuby.minecraftplugin.server.type;

import dev.dexuby.minecraftplugin.server.ArtifactVersion;
import dev.dexuby.minecraftplugin.server.Repository;
import dev.dexuby.minecraftplugin.server.ServerVersion;
import dev.dexuby.minecraftplugin.util.ServerUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ServerType {

    private final HttpClient httpClient;
    private List<ServerVersion> serverVersions = new ArrayList<>();

    public ServerType(@NotNull final HttpClient httpClient) {

        this.httpClient = httpClient;

    }

    @NotNull
    public List<ServerVersion> getVersions() {

        return this.serverVersions;

    }

    public void setVersions(@NotNull final List<ServerVersion> serverVersions) {

        this.serverVersions = serverVersions;

    }

    @Nullable
    public ServerVersion getVersionById(@NotNull final String versionId) {

        for (final ServerVersion serverVersion : this.serverVersions)
            if (serverVersion.id().equals(versionId))
                return serverVersion;

        return null;

    }

    @NotNull
    protected CompletableFuture<List<ArtifactVersion>> fetchVersionsNexus(@NotNull final String endpoint, @NotNull final String body) {

        return this.httpClient.sendAsync(HttpRequest.newBuilder(URI.create(endpoint))
                        .header("Content-Type", "application/json;charset=utf-8")
                        .timeout(Duration.ofSeconds(2L))
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build(), HttpResponse.BodyHandlers.ofString())
                .handle((response, throwable) -> {
                    if (throwable != null)
                        throw new RuntimeException(throwable);
                    return ServerUtils.extractVersionsFromResponseData(response.body());
                });


    }

    @NotNull
    public abstract String getId();

    @NotNull
    public abstract CompletableFuture<List<ArtifactVersion>> fetchVersions();

    @NotNull
    public abstract ServerVersion createServerVersion(@NotNull final ArtifactVersion artifactVersion);

    @NotNull
    public abstract Repository getRepository();

}
