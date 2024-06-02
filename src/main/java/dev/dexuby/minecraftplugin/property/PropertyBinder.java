package dev.dexuby.minecraftplugin.property;

import com.intellij.openapi.observable.properties.ObservableMutableProperty;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to make the management of string value properties easier, so they can later be exported and used in the asset
 * building step.
 *
 * @param <T> The type.
 */

public class PropertyBinder<T> {

    private final Map<Property, T> properties = new HashMap<>();

    /**
     * Binds the provided key to a property by listening to the property's change event.
     *
     * @param key      The key.
     * @param property The property.
     */

    public void bind(@NotNull final Property key, @NotNull final ObservableMutableProperty<T> property) {

        property.afterChange((value) -> {
            this.updateProperty(key, value);
            return Unit.INSTANCE;
        });

    }

    /**
     * Sets the value of the provided key to the provided value overriding a potentially existing value.
     *
     * @param key   The key.
     * @param value The value.
     */

    public void updateProperty(@NotNull final Property key, final T value) {

        this.properties.put(key, value);

    }

    /**
     * Return the value mapped to the provided key or <code>null</code> if it's not present.
     *
     * @param key The key.
     * @return The value or <code>null</code>.
     */

    @Nullable
    public T get(@NotNull final Property key) {

        return this.properties.get(key);

    }

    /**
     * Returns the value mapped to the provided key or throws a {@link NullPointerException} if it's not present.
     *
     * @param key The key.
     * @return The value.
     */

    @NotNull
    public T getOrThrow(@NotNull final Property key) {

        if (!this.properties.containsKey(key))
            throw new NullPointerException("No property found with key: " + key.name());

        return this.properties.get(key);

    }

    /**
     * Returns the value mapped to the provided key or the provided default value if it's not present.
     *
     * @param key          The key.
     * @param defaultValue The default value.
     * @return The value or the provided default value if it's not present.
     */

    @NotNull
    public T getOrDefault(@NotNull final Property key, @NotNull final T defaultValue) {

        return this.properties.getOrDefault(key, defaultValue);

    }

    /**
     * Exports the stored properties into an asset step usable state with string type keys and values.
     *
     * @return The export properties.
     */

    public Map<String, String> export() {

        final Map<String, String> properties = new HashMap<>(this.properties.size());
        for (final Map.Entry<Property, T> entry : this.properties.entrySet())
            properties.put(entry.getKey().name(), entry.getValue().toString());

        return properties;

    }

}
