package dev.dexuby.minecraftplugin.server;

import com.intellij.openapi.components.Service;
import dev.dexuby.minecraftplugin.server.type.ServerType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Service(Service.Level.APP)
public final class ServerTypeRegistryImpl implements ServerTypeRegistry {

    private final Map<String, ServerType> serverTypeRegistry = new HashMap<>();
    private boolean hot;

    @Override
    public ServerType get(@NotNull final String id) {

        return this.serverTypeRegistry.get(id);

    }

    @Override
    public void register(@NotNull final String id, @NotNull final ServerType serverType) {

        this.serverTypeRegistry.put(id, serverType);

    }

    @Override
    public void unregister(@NotNull final String id) {

        this.serverTypeRegistry.remove(id);

    }

    @Override
    public List<String> getIds() {

        return new ArrayList<>(this.serverTypeRegistry.keySet());

    }

    @Override
    public boolean isHot() {

        return this.hot;

    }

    @Override
    public void tag() {

        this.hot = true;

    }

}
