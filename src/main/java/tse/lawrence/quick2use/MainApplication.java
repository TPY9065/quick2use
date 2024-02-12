package tse.lawrence.quick2use;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tse.lawrence.quick2use.data.Shortcut;
import tse.lawrence.quick2use.region.MainWindow;

import java.io.File;
import java.net.URL;
import java.util.List;


public class MainApplication extends Application
{
	public static final double SMALL_FONT_SIZE = 14.0;
	public static final double DEFAULT_FONT_SIZE = 18.0;
	public static final double DEFAULT_PADDING = 5.0;
	public static final ObservableList<Shortcut> SHORTCUTS = FXCollections.observableArrayList();
	public static final double DEFAULT_CELL_SIZE = 50.0;

	@Override
	public void start(Stage stage)
	{
		loadConfigs();

		Scene scene = new Scene(new MainWindow(), 960, 540);

		stage.setTitle("Quick2Use");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() throws Exception
	{
		saveConfigs();
		super.stop();
	}

	public static void main(String[] args)
	{
		launch();
	}

	private void loadConfigs()
	{
		try
		{
			URL url = MainApplication.class.getResource("config");
			ObjectMapper objectMapper = new ObjectMapper();
			List<Shortcut> shortcuts = objectMapper.readValue(url, new TypeReference<>() {});
			SHORTCUTS.addAll(shortcuts);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private void saveConfigs()
	{
		try
		{
			URL url = MainApplication.class.getResource("config");
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
			objectWriter.writeValue(new File(url.getPath()), SHORTCUTS);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}