package dev.dexuby.minecraftplugin.common;

import com.intellij.openapi.observable.properties.ObservableMutableProperty;
import org.jetbrains.annotations.NotNull;

public class BoundObservableMutableProperty<T> implements ObservableMutableProperty<T> {

    private final ObservableMutableProperty<T> property;

    private BoundObservableMutableProperty(@NotNull final ObservableMutableProperty<T> property) {

        this.property = property;

    }

    @Override
    public void set(@NotNull final T value) {

        this.property.set(value);

    }

    @Override
    public T get() {

        return this.property.get();

    }

    public static <T> BoundObservableMutableProperty<T> of(@NotNull final ObservableMutableProperty<T> property) {

        return new BoundObservableMutableProperty<>(property);

    }

}
