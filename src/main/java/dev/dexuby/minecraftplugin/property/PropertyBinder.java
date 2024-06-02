package dev.dexuby.minecraftplugin.property;

import com.intellij.openapi.observable.properties.ObservableMutableProperty;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PropertyBinder<T> {

    private final Map<Property, T> properties = new HashMap<>();

    public void bind(@NotNull final Property key, @NotNull final ObservableMutableProperty<T> property) {

        property.afterChange((value) -> {
            this.updateProperty(key, value);
            return Unit.INSTANCE;
        });

    }

    public void updateProperty(@NotNull final Property key, final T value) {

        this.properties.put(key, value);

    }

    @Nullable
    public T get(@NotNull final Property key) {

        return this.properties.get(key);

    }

    @NotNull
    public T getOrThrow(@NotNull final Property key) {

        if (!this.properties.containsKey(key))
            throw new NullPointerException("No property found with key: " + key.name());

        return this.properties.get(key);

    }

    @NotNull
    public T getOrDefault(@NotNull final Property key, @NotNull final T defaultValue) {

        return this.properties.getOrDefault(key, defaultValue);

    }

    public Map<String, String> export() {

        final Map<String, String> properties = new HashMap<>(this.properties.size());
        for (final Map.Entry<Property, T> entry : this.properties.entrySet())
            properties.put(entry.getKey().name(), entry.getValue().toString());

        return properties;

    }

}
