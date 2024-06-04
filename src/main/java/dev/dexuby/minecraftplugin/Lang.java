package dev.dexuby.minecraftplugin;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.function.Supplier;

public final class Lang extends DynamicBundle {

    public static final String LANG_BUNDLE_PATH = "messages.lang";

    private static class LangBundleSingleton {

        private static final Lang INSTANCE = new Lang(LANG_BUNDLE_PATH);

    }

    private Lang(@NotNull final String pathToBundle) {

        super(pathToBundle);

    }

    /**
     * Obtains the string message linked to the provided key from the lang resource bundle. If the provided key is not
     * present it returns a string representation of the key and the provided parameters (if present).
     *
     * @param key        The key.
     * @param parameters The parameters.
     * @return The message.
     */

    @NotNull
    public static String message(@NotNull @PropertyKey(resourceBundle = LANG_BUNDLE_PATH) final String key, @NotNull final Object... parameters) {

        return getInstance().getMessage(key, parameters);

    }

    /**
     * Obtains a supplier that can supply the string message linked to the provided key from the lang resource bundle.
     * If the provided key is not present it returns a supplier that can supply a string representation of the key and
     * the provided parameters (if present).
     *
     * @param key        The key.
     * @param parameters The parameters.
     * @return The message supplier.
     */

    @NotNull
    public static Supplier<String> messagePointer(@NotNull @PropertyKey(resourceBundle = LANG_BUNDLE_PATH) final String key, @NotNull final Object... parameters) {

        return getInstance().getLazyMessage(key, parameters);

    }

    public static Lang getInstance() {

        return LangBundleSingleton.INSTANCE;

    }

}
