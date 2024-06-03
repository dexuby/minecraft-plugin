package dev.dexuby.minecraftplugin;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.wizard.AbstractNewProjectWizardStep;
import com.intellij.ide.wizard.NewProjectWizardStep;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.observable.properties.GraphProperty;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.ui.configuration.JdkComboBox;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.dsl.builder.Cell;
import com.intellij.ui.dsl.builder.Panel;
import com.intellij.ui.dsl.builder.SegmentedButton;
import dev.dexuby.minecraftplugin.property.PropertyBinder;
import dev.dexuby.minecraftplugin.property.Property;
import dev.dexuby.minecraftplugin.server.ServerTypeRegistry;
import dev.dexuby.minecraftplugin.server.ServerVersion;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class ProjectSettingsStep extends AbstractNewProjectWizardStep {

    private final GraphProperty<Sdk> sdk = super.getPropertyGraph().lateinitProperty();
    private final GraphProperty<ServerVersion> serverVersion = super.getPropertyGraph().lateinitProperty();
    private final GraphProperty<String> version = super.getPropertyGraph().property("1.0.0");
    private final GraphProperty<String> author = super.getPropertyGraph().lateinitProperty();
    private final GraphProperty<String> groupId = super.getPropertyGraph().property("org.example");
    private final GraphProperty<String> artifactId = super.getPropertyGraph().property("untitled");

    private final PropertyBinder<String> propertyBinder = new PropertyBinder<>();
    private final CollectionComboBoxModel<String> comboBoxModel = new CollectionComboBoxModel<>();
    private final ServerTypeRegistry serverTypeRegistry;
    private final ModuleBuilder moduleBuilder;
    private final JdkComboBox jdkComboBox;

    public ProjectSettingsStep(@NotNull final ModuleBuilder moduleBuilder,
                               @NotNull final NewProjectWizardStep parent) {

        super(parent);

        this.moduleBuilder = moduleBuilder;
        this.serverTypeRegistry = ApplicationManager.getApplication().getService(ServerTypeRegistry.class);

        final ProjectSdksModel projectSdksModel = new ProjectSdksModel();
        projectSdksModel.reset(null);

        this.jdkComboBox = new JdkComboBox(null, projectSdksModel, null, null, null, null);
        this.jdkComboBox.addActionListener(event -> {
            final Sdk jdk = this.jdkComboBox.getSelectedJdk();
            if (jdk != null)
                this.sdk.set(jdk);
        });

        this.bindStringProperties();
        this.preSelectBestJdk();

    }

    private void bindStringProperties() {

        this.sdk.afterChange((sdk) -> {
            this.propertyBinder.updateProperty(Property.JDK, sdk.getName());
            this.propertyBinder.updateProperty(Property.JDK_TYPE, sdk.getSdkType().getName());
            return Unit.INSTANCE;
        });

        this.serverVersion.afterChange((serverVersion) -> {
            this.propertyBinder.updateProperty(Property.SERVER_VERSION_PLUGIN_TARGET, serverVersion.pluginTarget());
            this.propertyBinder.updateProperty(Property.SERVER_VERSION_GROUP_ID, serverVersion.groupId());
            this.propertyBinder.updateProperty(Property.SERVER_VERSION_ARTIFACT_ID, serverVersion.artifactId());
            this.propertyBinder.updateProperty(Property.SERVER_VERSION_VERSION, serverVersion.version());
            this.propertyBinder.updateProperty(Property.SERVER_VERSION_REPO_ID, serverVersion.repository().id());
            this.propertyBinder.updateProperty(Property.SERVER_VERSION_REPO_URL, serverVersion.repository().url());
            return Unit.INSTANCE;
        });

        this.propertyBinder.bind(Property.VERSION, this.version);
        this.propertyBinder.bind(Property.AUTHOR, this.author);
        this.propertyBinder.bind(Property.GROUP_ID, this.groupId);
        this.propertyBinder.bind(Property.ARTIFACT_ID, this.artifactId);

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
            if (!JdkComboBox.getSdkFilter(this.moduleBuilder::isSuitableSdkType).value(jdk)) continue;

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

        builder.row(Lang.message("jdk"), (row) -> {
            row.cell(this.jdkComboBox)
                    .addValidationRule(Lang.message("jdk-validation"), (combBox) -> combBox.getSelectedJdk() == null);
            return Unit.INSTANCE;
        });

        builder.row(Lang.message("server-version"), (row) -> {
            final List<String> serverTypeIds = this.serverTypeRegistry.getIds();
            final SegmentedButton<String> segmentedButton = row.segmentedButton(serverTypeIds, ((itemPresentation, value) -> {
                itemPresentation.setText(value);
                return Unit.INSTANCE;
            }));
            segmentedButton.setSelectedItem(serverTypeIds.get(0));
            segmentedButton.whenItemSelected(null, (serverTypeId) -> {
                this.updateServerVersionComboBox(serverTypeId);
                return Unit.INSTANCE;
            });
            row.cell(new ComboBox<>(this.comboBoxModel))
                    .onChanged((value) -> {
                        this.updateSelectedServerVersionId(Objects.requireNonNull(segmentedButton.getSelectedItem()), value.getItem());
                        return Unit.INSTANCE;
                    });
            this.updateServerVersionComboBox(Objects.requireNonNull(segmentedButton.getSelectedItem()));
            return Unit.INSTANCE;
        });

        builder.group(Lang.message("properties-group-title"), false, (panel) -> {
            panel.row("", (row) -> {
                row.label(Lang.message("properties-group-version"));
                row.contextHelp("", Lang.message("properties-group-version-help"));
                this.textField(row.textField(), 10, this.version.get(), this.version::set, true);
                row.label(Lang.message("properties-group-author"));
                row.contextHelp("", Lang.message("properties-group-author-help"));
                this.textField(row.textField(), 15, null, this.author::set, true);
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        });

        builder.group(Lang.message("maven-group-title"), false, (panel) -> {
            panel.row("", (row) -> {
                row.label(Lang.message("maven-group-group-id"));
                row.contextHelp("", Lang.message("maven-group-group-id-help"));
                this.textField(row.textField(), 10, this.groupId.get(), this.groupId::set, true);
                row.label(Lang.message("maven-group-artifact-id"));
                row.contextHelp("", Lang.message("maven-group-artifact-id-help"));
                this.textField(row.textField(), 10, this.artifactId.get(), this.artifactId::set, true);
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        });

    }

    private void updateServerVersionComboBox(@NotNull final String serverTypeId) {

        this.comboBoxModel.removeAll();
        for (final ServerVersion version : this.serverTypeRegistry.get(serverTypeId).getVersions())
            this.comboBoxModel.add(version.id());
        final String selected = this.comboBoxModel.getElementAt(0);
        this.comboBoxModel.setSelectedItem(selected);

    }

    private void updateSelectedServerVersionId(@NotNull final String serverTypeId, @NotNull final String id) {

        final ServerVersion version = this.serverTypeRegistry.get(serverTypeId).getVersionById(id);
        if (version != null)
            this.serverVersion.set(version);

    }

    private void textField(@NotNull final Cell<JBTextField> textFieldCell, final int columns, @Nullable final String defaultText, @NotNull final Consumer<String> setter, final boolean required) {

        textFieldCell.onChanged((field) -> {
            setter.accept(field.getText());
            return Unit.INSTANCE;
        });

        if (required)
            textFieldCell.addValidationRule(Lang.message("field-required"), (field) -> field.getText().isEmpty());

        final JBTextField textField = textFieldCell.getComponent();
        textField.setColumns(columns);
        if (defaultText != null)
            textField.setText(defaultText);

    }

    public Sdk getSdk() {

        return this.sdk.get();

    }

    public ServerVersion getServerVersion() {

        return this.serverVersion.get();

    }

    public PropertyBinder<String> getPropertyBinder() {

        return this.propertyBinder;

    }

}
