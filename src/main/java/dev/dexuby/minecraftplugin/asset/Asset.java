package dev.dexuby.minecraftplugin.asset;

import com.intellij.ide.projectWizard.generators.AssetsNewProjectWizardStep;
import dev.dexuby.minecraftplugin.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public record Asset(@NotNull String path,
                    @NotNull String name) {

    /**
     * Adds the asset to the provided {@link AssetsNewProjectWizardStep}.
     *
     * @param step       The assets step.
     * @param properties The properties.
     */

    public void apply(@NotNull final AssetsNewProjectWizardStep step, @NotNull final Map<String, String> properties) {

        step.addTemplateAsset(this.path, this.name, properties);

    }

    /**
     * Adds the asset to the provided {@link AssetsNewProjectWizardStep} applying all properties to the path and name
     * values before adding them.
     *
     * @param step       The assets step.
     * @param properties The properties.
     */

    public void applyDynamic(@NotNull final AssetsNewProjectWizardStep step, @NotNull final Map<String, String> properties) {

        step.addTemplateAsset(StringUtils.applyProperties(this.path, properties), StringUtils.applyProperties(this.name, properties), properties);

    }

}
