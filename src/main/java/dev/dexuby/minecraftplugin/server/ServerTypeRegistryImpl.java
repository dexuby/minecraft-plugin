package dev.dexuby.minecraftplugin.server;

import com.intellij.openapi.components.Service;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Service(Service.Level.APP)
public final class ServerTypeRegistryImpl implements ServerTypeRegistry {

    private final Map<String, ServerTypeNew> serverTypeRegistry = new HashMap<>();

    @Override
    public ServerTypeNew get(@NotNull final String id) {

        return this.serverTypeRegistry.get(id);

    }

    @Override
    public void register(@NotNull final String id, @NotNull final ServerTypeNew serverType) {

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

}
