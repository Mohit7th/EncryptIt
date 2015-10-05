/*
 * Each frame has some common events associated with it, this class will handle these
 * common events 
 * Date : 11/5/2015
 */

package support;

import javax.swing.*;
import java.awt.event.*;
import interfacese.*;

public class HandlingEvents implements ActionListener {
	// main menu frame reference variables
	ViewData vdFrame;
	Settings setFrame;
	EncryptTxt encFrame;
	// warning class to warn before close
	ShowWarning warnDialog;
	// frame no to identify frame for setting visible property to false
	static int frameNo;
	static String UserName, password;

	ShowWarning warn = new ShowWarning();

	public HandlingEvents(JFrame frm, String uname, String password) {
		UserName = uname;
		HandlingEvents.password = password;
		warnDialog = new ShowWarning(this);
		// getting different type of references and downcasting them to there
		// equivalent frame type
		if (frm instanceof ViewData) {
			vdFrame = (ViewData) frm;
			frameNo = 3;
		} else if (frm instanceof Settings) {
			setFrame = (Settings) frm;
			frameNo = 4;
		} else if (frm instanceof EncryptTxt) {
			encFrame = (EncryptTxt) frm;
			frameNo = 1;
		}
	}

	public void actionPerformed(ActionEvent ae) {
		String btnPressStr = ae.getActionCommand();
		String strTemp[] = { UserName, password };

		switch (btnPressStr) {
		// all cases are common for 4 frames
		case "Exit":
			warnDialog.showWarnMsg(4);
			break;
		case "About Encrypt It":
			AboutUs.main(strTemp);
			break;
		case "Lock":
			if (warn.showWarnMsg(13) == 0) {
				disableFrame();
				LogIn.main(new String[2]);
			}
			break;
		case "Home":
			disableFrame();
			HomeMenu.main(strTemp);
			break;
		case "Help":
			HelpDoc.main(strTemp);
			break;
		}
	}

	// method to hide current frame
	private void disableFrame() {
		if (frameNo == 1)
			encFrame.setVisible(false);
		else if (frameNo == 3)
			vdFrame.setVisible(false);
		else if (frameNo == 4)
			setFrame.setVisible(false);
	}
}
