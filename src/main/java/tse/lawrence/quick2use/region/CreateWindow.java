package tse.lawrence.quick2use.region;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;
import tse.lawrence.quick2use.data.Shortcut;
import tse.lawrence.quick2use.utils.ImageUtils;
import tse.lawrence.quick2use.utils.StyleUtils;

import java.io.File;

import static tse.lawrence.quick2use.MainApplication.DEFAULT_FONT_SIZE;
import static tse.lawrence.quick2use.MainApplication.DEFAULT_PADDING;
import static tse.lawrence.quick2use.MainApplication.SHORTCUTS;
import static tse.lawrence.quick2use.MainApplication.SMALL_FONT_SIZE;


public class CreateWindow extends Stage
{
	private static final CreateWindow window = new CreateWindow();

	private final Font BOLD_FONT = StyleUtils.createFont(FontWeight.BOLD, DEFAULT_FONT_SIZE);
	private final Font NORMAL_FONT = StyleUtils.createFont(FontWeight.NORMAL, SMALL_FONT_SIZE);
	private final FileChooser imageFileChooser = new FileChooser();
	private final FileChooser executableFileChooser = new FileChooser();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty path = new SimpleStringProperty();
	private final StringProperty icon = new SimpleStringProperty();
	private File executable;

	public static void open()
	{
		window.show();
	}

	public CreateWindow()
	{
		initializeFileChooser();
		initializeProperty();

		VBox root = new VBox();
		renderForm(root);

		Scene scene = new Scene(root, 480, 360);
		setTitle("Create New Shortcut");
		setScene(scene);
	}

	private void initializeFileChooser()
	{
		imageFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
		imageFileChooser.setTitle("Select Image");

		executableFileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Executables", "*.jar", "*.exe"));
		executableFileChooser.setTitle("Select Executable");
	}

	private void initializeProperty()
	{
		name.set("my-new-shortcut");
		path.set("Select executable");
		icon.set("Select shortcut icon (if any)");
	}

	private void renderForm(VBox root)
	{
		VBox pathFields = createInputFieldsAndBindEvent("Executable path", this::openExecutableFileChooser, path);
		VBox nameFields = createInputFieldsAndBindEvent("Name", null, name);
		VBox iconFields = createInputFieldsAndBindEvent("Shortcut Icon", this::openImageFileChooser, icon);

		Button bCreate = new Button("Create");
		bCreate.setFont(BOLD_FONT);
		bCreate.setOnMouseClicked(event -> {
			if (icon.get().equalsIgnoreCase("Select shortcut icon (if any)"))
			{
				icon.set(ImageUtils.saveSystemIcon(executable, name.get()));
			}

			SHORTCUTS.add(new Shortcut(name.get(), path.get(), icon.get()));
			close();
		});
		bCreate.disableProperty().bind(path.isEqualTo("Select executable"));

		root.getChildren().addAll(pathFields, nameFields, iconFields, bCreate);
		root.setSpacing(DEFAULT_PADDING * 2);
		root.setPadding(new Insets(DEFAULT_PADDING));
		root.setAlignment(Pos.CENTER);
	}

	private VBox createInputFieldsAndBindEvent(String label, EventHandler<MouseEvent> onFileChooserClicked, StringProperty observable)
	{
		Label lName = new Label(label);
		lName.setFont(BOLD_FONT);

		TextField tfName = new TextField(observable.get());
		tfName.setFont(NORMAL_FONT);
		tfName.setFocusTraversable(false);
		tfName.textProperty().bindBidirectional(observable);

		if (onFileChooserClicked == null)
		{
			return new VBox(lName, tfName);
		}

		Button fileChooser = new Button();
		fileChooser.setGraphic(new FontIcon(AntDesignIconsOutlined.FOLDER));
		fileChooser.setOnMouseClicked(onFileChooserClicked);
		fileChooser.minHeightProperty().bind(tfName.heightProperty());
		fileChooser.minWidthProperty().bind(tfName.heightProperty());

		HBox inputFields = new HBox(tfName, fileChooser);
		inputFields.setSpacing(DEFAULT_PADDING);
		HBox.setHgrow(tfName, Priority.ALWAYS);

		return new VBox(lName, inputFields);
	}

	private void openExecutableFileChooser(MouseEvent event)
	{
		executable = executableFileChooser.showOpenDialog(this);
		if (executable != null)
		{
			path.set(executable.getAbsolutePath());
			name.set(executable.getName().substring(0, executable.getName().indexOf('.')));
		}
	}

	private void openImageFileChooser(MouseEvent event)
	{
		File file = imageFileChooser.showOpenDialog(this);
		if (file != null)
		{
			icon.set(file.getAbsolutePath());
		}
	}
}
