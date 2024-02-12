package tse.lawrence.quick2use.utils;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static tse.lawrence.quick2use.data.Shortcut.ICON_HEIGHT;
import static tse.lawrence.quick2use.data.Shortcut.ICON_WIDTH;


public class ImageUtils
{
	private static final String resourcePath = Paths.get(".").toAbsolutePath().getParent().toAbsolutePath().toString() + "/images/";

	static
	{
		File dir = new File(resourcePath);
		boolean mkdirSuccess = false;
		if (!dir.exists())
		{
			mkdirSuccess = dir.mkdirs();
		}
		if (!mkdirSuccess)
		{
			System.out.println("Fail to create image directory/Directory is already exist");
		}
	}

	public static String saveSystemIcon(File file, String fileName)
	{
		try
		{
			Icon fileIcon = FileSystemView.getFileSystemView().getSystemIcon(file, ICON_WIDTH, ICON_HEIGHT);
			BufferedImage bufferedImage = new BufferedImage(fileIcon.getIconWidth(), fileIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			fileIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);

			File iconFile = new File( resourcePath + fileName + ".png");
			if (!iconFile.mkdirs() && !iconFile.exists())
			{
				return "";
			}
			ImageIO.write(bufferedImage, "png", iconFile);

			return iconFile.getAbsolutePath();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
			return "";
		}
	}

	public static void removeSystemIcon(String iconPath)
	{
		try
		{
			Files.delete(Paths.get(iconPath));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
