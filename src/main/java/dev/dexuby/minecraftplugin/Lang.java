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

    @NotNull
    public static String message(@NotNull @PropertyKey(resourceBundle = LANG_BUNDLE_PATH) final String key, @NotNull final Object... parameters) {

        return getInstance().getMessage(key, parameters);

    }

    @NotNull
    public static Supplier<String> messagePointer(@NotNull @PropertyKey(resourceBundle = LANG_BUNDLE_PATH) final String key, @NotNull final Object... parameters) {

        return getInstance().getLazyMessage(key, parameters);

    }

    public static Lang getInstance() {

        return LangBundleSingleton.INSTANCE;

    }

}
