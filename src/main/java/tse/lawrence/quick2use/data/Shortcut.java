package tse.lawrence.quick2use.data;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Shortcut
{
	public static final int ICON_WIDTH = 250;
	public static final int ICON_HEIGHT = 250;
	private final StringProperty name;
	private String icon;
	private String exec;

	public Shortcut()
	{
		name = new SimpleStringProperty();
	}

	public Shortcut(String name, String execPath, String icon)
	{
		this.name = new SimpleStringProperty(name);
		this.exec = execPath;
		this.icon = icon;
	}

	public String getName()
	{
		return name.get();
	}

	public void setName(String name)
	{
		this.name.set(name);
	}

	public StringProperty nameProperty()
	{
		return name;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getExec()
	{
		return exec;
	}

	public void setExec(String exec)
	{
		this.exec = exec;
	}
}
