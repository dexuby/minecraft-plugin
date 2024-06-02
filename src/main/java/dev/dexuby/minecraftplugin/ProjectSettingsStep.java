package dev.dexuby.minecraftplugin;

import com.intellij.ide.wizard.AbstractNewProjectWizardStep;
import com.intellij.ide.wizard.NewProjectWizardStep;
import com.intellij.openapi.observable.properties.GraphProperty;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.projectRoots.SimpleJavaSdkType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.ui.configuration.JdkComboBox;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.dsl.builder.Cell;
import com.intellij.ui.dsl.builder.Panel;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ProjectSettingsStep extends AbstractNewProjectWizardStep {

    private final GraphProperty<Sdk> sdk = super.getPropertyGraph().lateinitProperty();

    private final GraphProperty<String> version = super.getPropertyGraph().property("1.0.0");
    private final GraphProperty<String> authors = super.getPropertyGraph().lateinitProperty();

    // Maven
    private final GraphProperty<String> groupId = super.getPropertyGraph().property("org.example");
    private final GraphProperty<String> artifactId = super.getPropertyGraph().property("untitled");

    private final JdkComboBox jdkComboBox;

    public ProjectSettingsStep(@NotNull final NewProjectWizardStep parent) {

        super(parent);

        final ProjectSdksModel projectSdksModel = new ProjectSdksModel();
        projectSdksModel.reset(null);

        this.jdkComboBox = new JdkComboBox(null, projectSdksModel, null, null, null, null);
        this.jdkComboBox.addActionListener(event -> {
            final Sdk jdk = this.jdkComboBox.getSelectedJdk();
            if (jdk != null)
                this.sdk.set(jdk);
        });

        this.preSelectBestJdk();

    }

    private void preSelectBestJdk() {

        final Project project = ProjectManager.getInstance().getDefaultProject();
        final Sdk defaultProjectSdk = ProjectRootManager.getInstance(project).getProjectSdk();
        if (defaultProjectSdk != null) {
            this.jdkComboBox.setSelectedJdk(defaultProjectSdk);
            return;
        }

        Sdk best = null;
        for (int i = 0; i < this.jdkComboBox.getModel().getSize(); i++) {
            if (!(this.jdkComboBox.getModel().getElementAt(i) instanceof JdkComboBox.ActualJdkComboBoxItem item))
                continue;
            final Sdk jdk = item.getJdk();
            if (best == null) {
                best = jdk;
                continue;
            }

            final SdkTypeId jdkType = jdk.getSdkType();
            if (!JdkComboBox.getSdkFilter(SimpleJavaSdkType.notSimpleJavaSdkType()).value(jdk)) continue;

            final SdkTypeId bestType = best.getSdkType();
            if (bestType == jdkType && bestType.versionComparator().compare(best, jdk) < 0)
                best = jdk;
        }

        if (best != null) {
            this.jdkComboBox.setSelectedJdk(best);
        } else {
            this.jdkComboBox.setSelectedItem(this.jdkComboBox.showNoneSdkItem());
        }

    }

    @Override
    public void setupUI(@NotNull final Panel builder) {

        builder.row("JDK:", (row) -> {
            row.cell(this.jdkComboBox)
                    .addValidationRule("Please select a valid JDK.", (combBox) -> combBox.getSelectedJdk() == null);
            return Unit.INSTANCE;
        });

        builder.group("Properties", false, (panel) -> {
            panel.row("", (row) -> {
                row.label("Version:");
                this.textField(row.textField(), 10, this.version.get(), this.version::set, true);
                row.label("Authors:");
                row.contextHelp("", "The authors of the project.");
                this.textField(row.textField(), 15, null, this.authors::set, true);
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        });

        builder.group("Maven", false, (panel) -> {
            panel.row("", (row) -> {
                row.label("GroupId:");
                this.textField(row.textField(), 10, this.groupId.get(), this.groupId::set, true);
                row.label("ArtifactId:");
                this.textField(row.textField(), 10, this.artifactId.get(), this.artifactId::set, true);
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        });

    }

    private void textField(@NotNull final Cell<JBTextField> textFieldCell, final int columns, @Nullable final String defaultText, @NotNull final Consumer<String> setter, final boolean required) {

        textFieldCell.onChanged((field) -> {
            setter.accept(field.getText());
            return Unit.INSTANCE;
        });

        if (required)
            textFieldCell.addValidationRule("This field is required.", (field) -> field.getText().isEmpty());

        final JBTextField textField = textFieldCell.getComponent();
        textField.setColumns(columns);
        if (defaultText != null)
            textField.setText(defaultText);

    }

    public String getVersion() {

        return this.version.get();

    }

    public String getAuthors() {

        return this.authors.get();

    }

    public String getGroupId() {

        return this.groupId.get();

    }

    public String getArtifactId() {

        return this.artifactId.get();

    }

    public Sdk getSdk() {

        return this.sdk.get();

    }

}
