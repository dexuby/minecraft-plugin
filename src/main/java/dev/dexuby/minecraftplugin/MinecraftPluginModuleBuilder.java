package dev.dexuby.minecraftplugin;

import com.intellij.ide.starters.local.wizard.StarterInitialStep;
import com.intellij.ide.starters.shared.CommonStarterInitialStep;
import com.intellij.ide.util.projectWizard.SdkSettingsStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.*;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.ColorIcon;
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

        return "This is a placeholder description.";

    }

    @NotNull
    @Override
    public Icon getNodeIcon() {

        return new ColorIcon(20, JBColor.GREEN);

    }

    @NotNull
    @Override
    protected NewProjectWizardStep createStep(@NotNull final WizardContext wizardContext) {

        return new NewProjectWizardChainStep<>(new RootNewProjectWizardStep(wizardContext))
                .nextStep(this::newProjectWizardBaseStepNoGap)
                .nextStep(GitNewProjectWizardStep::new)
                .nextStep(ProjectSettingsStep::new)
                .nextStep(MinecraftPluginAssetStep::new);

    }

    private NewProjectWizardBaseStep newProjectWizardBaseStepNoGap(@NotNull final NewProjectWizardStep parent) {

        final NewProjectWizardBaseStep step = new NewProjectWizardBaseStep(parent);
        step.setBottomGap$intellij_platform_ide_impl(false);

        return step;

    }

}
