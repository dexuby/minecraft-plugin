package dev.dexuby.minecraftplugin;

import com.intellij.icons.AllIcons;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MinecraftPluginModuleBuilder extends AbstractNewProjectWizardBuilder {

    @NotNull
    @Override
    public String getPresentableName() {

        return "Minecraft Plugin";

    }

    @NotNull
    @Override
    public String getDescription() {

        return "Easily create a plugin project for Minecraft servers.";

    }

    @NotNull
    @Override
    public Icon getNodeIcon() {

        return AllIcons.General.Gear;

    }

    @NotNull
    @Override
    protected NewProjectWizardStep createStep(@NotNull final WizardContext wizardContext) {

        return new NewProjectWizardChainStep<>(new RootNewProjectWizardStep(wizardContext))
                .nextStep(this::newProjectWizardBaseStepNoGap)
                .nextStep(GitNewProjectWizardStep::new)
                .nextStep((step) -> new ProjectSettingsStep(this, step))
                .nextStep(MinecraftPluginAssetStep::new);

    }

    private NewProjectWizardBaseStep newProjectWizardBaseStepNoGap(@NotNull final NewProjectWizardStep parent) {

        final NewProjectWizardBaseStep step = new NewProjectWizardBaseStep(parent);
        step.setBottomGap$intellij_platform_ide_impl(false);

        return step;

    }

}
