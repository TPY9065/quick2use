package tse.lawrence.quick2use.utils;

import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class StyleUtils
{
	public static void fillBackground(Region region, Paint color)
	{
		region.setBackground(Background.fill(color));
	}

	public static Font createFont(FontWeight fontWeight, double fontSize)
	{
		return Font.font(null, fontWeight, fontSize);
	}
}
