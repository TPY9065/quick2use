package tse.lawrence.quick2use.region;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;
import tse.lawrence.quick2use.control.ShortcutListCell;
import tse.lawrence.quick2use.data.Shortcut;
import tse.lawrence.quick2use.utils.StyleUtils;

import static tse.lawrence.quick2use.MainApplication.DEFAULT_FONT_SIZE;
import static tse.lawrence.quick2use.MainApplication.DEFAULT_PADDING;
import static tse.lawrence.quick2use.MainApplication.SHORTCUTS;


public class MainWindow extends VBox
{
	public MainWindow()
	{
		Button bNewShortcut =  new Button();
		bNewShortcut.setGraphic(new FontIcon(AntDesignIconsOutlined.PLUS_CIRCLE));
		bNewShortcut.setFont(StyleUtils.createFont(FontWeight.BOLD, DEFAULT_FONT_SIZE));
		bNewShortcut.setOnMouseClicked(e -> CreateWindow.open());

		Region space = new Region();
		HBox.setHgrow(space, Priority.ALWAYS);

		HBox hBox = new HBox(space, bNewShortcut);
		hBox.setPadding(new Insets(DEFAULT_PADDING));
		hBox.setAlignment(Pos.CENTER);
		StyleUtils.fillBackground(hBox, Color.BLUEVIOLET);

		ListView<Shortcut> shortcutListView = new ListView<>();
		shortcutListView.setCellFactory(p -> new ShortcutListCell());
		shortcutListView.setItems(SHORTCUTS);
		VBox.setVgrow(shortcutListView, Priority.ALWAYS);

		getChildren().addAll(hBox, shortcutListView);
	}
}
