package dev.dexuby.minecraftplugin.asset;

import com.intellij.ide.projectWizard.generators.AssetsNewProjectWizardStep;
import dev.dexuby.minecraftplugin.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public record Asset(@NotNull String path,
                    @NotNull String name) {

    public void apply(@NotNull final AssetsNewProjectWizardStep step, @NotNull final Map<String, String> properties) {

        step.addTemplateAsset(this.path, this.name, properties);

    }

    public void applyDynamic(@NotNull final AssetsNewProjectWizardStep step, @NotNull final Map<String, String> properties) {

        step.addTemplateAsset(StringUtils.applyProperties(this.path, properties), StringUtils.applyProperties(this.name, properties), properties);

    }

}
