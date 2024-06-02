package dev.dexuby.minecraftplugin;

import com.intellij.ide.projectWizard.generators.AssetsNewProjectWizardStep;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LanguageLevelProjectExtension;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.pom.java.LanguageLevel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
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

        final Map<String, String> properties = new HashMap<>();
        // JDK
        properties.put("JDK", parent.getSdk().getName());
        properties.put("JDK_TYPE", parent.getSdk().getSdkType().getName());
        // Project settings
        properties.put("PROJECT_NAME", project.getName());
        properties.put("VERSION", parent.getVersion());
        properties.put("AUTHORS", parent.getAuthors());
        // Maven
        properties.put("GROUP_ID", parent.getGroupId());
        properties.put("ARTIFACT_ID", parent.getArtifactId());
        properties.put("ARTIFACT_ID_SAFE", parent.getArtifactId().replace('-', '_'));

        super.addTemplateAsset(".gitignore", ".gitignore", properties);
        super.addTemplateAsset("pom.xml", "pom.xml", properties);
        super.addTemplateAsset("src/main/java/resources/plugin.yml", "plugin.yml", properties);
        super.addTemplateAsset(
                String.format("src/main/java/%s/%s/%s.java", parent.getGroupId(), properties.get("ARTIFACT_ID_SAFE"), project.getName()),
                "Entry.java",
                properties
        );

    }

}
