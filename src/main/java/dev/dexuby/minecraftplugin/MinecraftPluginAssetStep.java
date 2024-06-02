package dev.dexuby.minecraftplugin;

import com.intellij.ide.projectWizard.generators.AssetsNewProjectWizardStep;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LanguageLevelProjectExtension;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.pom.java.LanguageLevel;
import dev.dexuby.minecraftplugin.asset.Asset;
import dev.dexuby.minecraftplugin.asset.Assets;
import dev.dexuby.minecraftplugin.property.PropertyBinder;
import dev.dexuby.minecraftplugin.property.Property;
import dev.dexuby.minecraftplugin.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class MinecraftPluginAssetStep extends AssetsNewProjectWizardStep {

    private final ProjectSettingsStep parent;

    public MinecraftPluginAssetStep(@NotNull final ProjectSettingsStep parent) {

        super(parent);

        this.parent = parent;

    }

    @Override
    public void setupProject(@NotNull final Project project) {

        if (parent.getSdk() != null) {
            final Application application = ApplicationManager.getApplication();
            application.invokeAndWait(() -> {
                application.runWriteAction(() -> {
                    ProjectRootManager.getInstance(project).setProjectSdk(parent.getSdk());
                    LanguageLevelProjectExtension.getInstance(project).setLanguageLevel(LanguageLevel.HIGHEST);
                });
            });
        }

        super.setupProject(project);

    }

    @Override
    public void setupAssets(@NotNull final Project project) {

        final PropertyBinder<String> propertyBinder = this.parent.getPropertyBinder();
        propertyBinder.updateProperty(Property.PROJECT_NAME, project.getName());
        propertyBinder.updateProperty(Property.ARTIFACT_ID_SAFE, StringUtils.tameArtifactId(propertyBinder.getOrThrow(Property.ARTIFACT_ID)));

        final Map<String, String> exportedProperties = propertyBinder.export();
        for (final Asset asset : Assets.DEFAULT_ASSETS) {
            if (asset.equals(Assets.MAIN_CLASS)) {
                asset.applyDynamic(this, exportedProperties);
            } else {
                asset.apply(this, exportedProperties);
            }
        }

    }

}
