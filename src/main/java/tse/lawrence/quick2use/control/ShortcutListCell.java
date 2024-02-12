package tse.lawrence.quick2use.control;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;
import tse.lawrence.quick2use.data.Shortcut;
import tse.lawrence.quick2use.dialog.AlertDialog;
import tse.lawrence.quick2use.utils.ImageUtils;
import tse.lawrence.quick2use.utils.StyleUtils;

import java.awt.*;
import java.io.File;

import static tse.lawrence.quick2use.MainApplication.DEFAULT_CELL_SIZE;
import static tse.lawrence.quick2use.MainApplication.DEFAULT_FONT_SIZE;
import static tse.lawrence.quick2use.MainApplication.DEFAULT_PADDING;
import static tse.lawrence.quick2use.MainApplication.SHORTCUTS;


public class ShortcutListCell extends ListCell<Shortcut>
{
	private static final Font BOLD_FONT = StyleUtils.createFont(FontWeight.BOLD, DEFAULT_FONT_SIZE);
	private static final Font NORMAL_FONT = StyleUtils.createFont(FontWeight.NORMAL, DEFAULT_FONT_SIZE);

	public ShortcutListCell()
	{
		setText(null);
		setHeight(DEFAULT_CELL_SIZE);
		setAlignment(Pos.CENTER_LEFT);
		setPadding(new Insets(DEFAULT_PADDING));
		setOnMouseClicked(this::onMouseClicked);
	}

	@Override
	protected void updateItem(Shortcut item, boolean empty)
	{
		super.updateItem(item, empty);
		if (item == null || empty)
		{
			setGraphic(null);
		}
		else
		{
			Node icon;
			try
			{
				ImageView image = new ImageView(item.getIcon());
				image.setFitHeight(DEFAULT_CELL_SIZE);
				image.setSmooth(true);
				image.setPreserveRatio(true);
				icon = image;
			}
			catch (IllegalArgumentException e)
			{
				FontIcon fontIcon = new FontIcon(AntDesignIconsOutlined.FILE_IMAGE);
				fontIcon.setIconSize((int) DEFAULT_CELL_SIZE);
				icon = fontIcon;
			}

			TextField tfName = new TextField(item.getName());
			tfName.setFont(NORMAL_FONT);
			tfName.setMinHeight(DEFAULT_CELL_SIZE);
			tfName.setVisible(false);
			tfName.setOnKeyPressed(event -> {
				if (tfName.isFocused() && event.getCode().equals(KeyCode.ENTER))
				{
					this.requestFocus();
				}
			});

			Label lName = new Label();
			lName.setFont(BOLD_FONT);
			lName.setMinHeight(DEFAULT_CELL_SIZE);
			lName.setCursor(Cursor.TEXT);

			StackPane spName = new StackPane(tfName, lName);
			spName.setAlignment(Pos.CENTER_LEFT);
			HBox.setHgrow(spName, Priority.ALWAYS);

			lName.setOnMouseClicked(event -> {
				if (isDoubleClicked(event))
				{
					lName.setVisible(false);
					tfName.setVisible(true);
					tfName.requestFocus();
					event.consume();
				}
			});

			tfName.focusedProperty().addListener(((observable, oldValue, focused) -> {
				if (!focused)
				{
					tfName.setVisible(false);
					lName.setVisible(true);
				}
			}));

			lName.textProperty().bind(tfName.textProperty());
			item.nameProperty().bind(lName.textProperty());

			Button bOpen = new Button();
			bOpen.setGraphic(new FontIcon(AntDesignIconsOutlined.FOLDER_OPEN));
			bOpen.setFont(BOLD_FONT);
			bOpen.setOnMouseClicked(this::openInFileExplorer);

			Button bDelete =  new Button();
			bDelete.setGraphic(new FontIcon(AntDesignIconsOutlined.DELETE));
			bDelete.setFont(BOLD_FONT);
			bDelete.setOnMouseClicked(this::deleteShortcut);

			HBox graphic = new HBox(icon, spName, bOpen, bDelete);
			graphic.setSpacing(DEFAULT_PADDING);
			graphic.setAlignment(Pos.CENTER_LEFT);

			setGraphic(graphic);
		}
	}

	private void onMouseClicked(MouseEvent event)
	{
		try
		{
			Shortcut shortcut = getItem();
			if (shortcut != null && isDoubleClicked(event))
			{
				File file = new File(shortcut.getExec());
				Desktop.getDesktop().open(file);
			}
		}
		catch (Exception e)
		{
			AlertDialog.open(e.getMessage());
		}
	}

	private void openInFileExplorer(MouseEvent event)
	{
		Shortcut shortcut = getItem();
		if (shortcut != null)
		{
			try
			{
				File file = new File(shortcut.getExec());
				Desktop.getDesktop().open(file.getParentFile());
			}
			catch (Exception e)
			{
				AlertDialog.open(e.getMessage());
			}
		}
	}

	private void deleteShortcut(MouseEvent event)
	{
		Shortcut shortcut = getItem();
		if (shortcut != null)
		{
			ImageUtils.removeSystemIcon(shortcut.getIcon());
			SHORTCUTS.remove(shortcut);
		}
	}

	private boolean isDoubleClicked(MouseEvent e)
	{
		return e.getClickCount() == 2 && e.getButton().equals(MouseButton.PRIMARY);
	}
}
