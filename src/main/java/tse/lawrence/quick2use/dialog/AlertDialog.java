package tse.lawrence.quick2use.dialog;

import javafx.scene.control.Alert;


public class AlertDialog extends Alert
{
	private static final AlertDialog instance = new AlertDialog();

	public static void open(String errMsg)
	{
		instance.setContentText(errMsg);
		instance.showAndWait();
	}

	private AlertDialog()
	{
		super(AlertType.ERROR);
	}
}
