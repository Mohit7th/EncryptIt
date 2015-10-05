package support;

/*
 *	every Interface has some type of waring messages associated with them
 *	on wrong user click or input, this class is used to provide warning, 
 *	information and the confirmation messages
 *	 
 */

import javax.swing.*;
import interfacese.*;
import readwrite.*;

public class ShowWarning {
	// Method to generate waring messages to the users
	EncryptTxt encTxt;
	String decText;

	public ShowWarning(Object obj) {
		if (obj instanceof EncryptTxt)
			this.encTxt = (EncryptTxt) obj;
	}

	public ShowWarning(String decText) {
		this.decText = decText;
		showWarnMsg(23);
	}

	public ShowWarning() {

	}

	public int showWarnMsg(int type) {
		// any temporary value
		int n = 11;
		String warnMsgStr = "";
		JFrame frame = new JFrame();

		// setting message string according to user input
		if (type == 1)
			warnMsgStr = "All four key should be unique!";
		else if (type == 0)
			warnMsgStr = "Select a Cipher technique!";
		else if (type == 2)
			warnMsgStr = "Select a File from MenuBar!";
		else if (type == 3)
			warnMsgStr = "You Need to select a key";
		else if (type == 4)
			warnMsgStr = "Do you want to Exit?";
		else if (type == 5)
			warnMsgStr = "Do you want to save changes?";
		else if (type == 6)
			warnMsgStr = "You Need to select an Encrypted file!";
		else if (type == 7)
			warnMsgStr = "file already exist!";
		else if (type == 8)
			warnMsgStr = "Encrypted! Do you want to switch to Decryption Mode?";
		else if (type == 9)
			warnMsgStr = "Decrypted! Do you want to switch to Encryption Mode?";
		else if (type == 10)
			warnMsgStr = "Incorrect username or password!";
		else if (type == 11)
			warnMsgStr = "UserName and password cannot be left blank";
		else if (type == 12)
			warnMsgStr = "Not authorized user to decrypt file";
		else if (type == 13)
			warnMsgStr = "Do you want to logout?";
		else if (type == 14)
			warnMsgStr = "File already exists!";
		else if (type == 15)
			warnMsgStr = "Username length > 4 and Password length == 15";
		else if (type == 16)
			warnMsgStr = "New User added to the database";
		else if (type == 17)
			warnMsgStr = "User Name already exist! Enter unique name";
		else if (type == 18)
			warnMsgStr = "New Password should be different";
		else if (type == 19)
			warnMsgStr = "Password changed";
		else if (type == 21)
			warnMsgStr = "Old password is not valid!";
		else if (type == 22)
			warnMsgStr = "Data saved to the Database";
		else if (type == 23)
			warnMsgStr = decText;
		else if (type == 24)
			warnMsgStr = "New Password length should be equal to 15";
		else if (type == 25)
			warnMsgStr = "Nothing to save, write or select a file first";
		else if (type == 26)
			warnMsgStr = "Select a encrypted data field first";
		else if (type == 27)
			warnMsgStr = "Password fields cannot be left blank";

		// else part to display a waring message to user before exiting the
		// software
		if (type <= 3 || type == 6 || type == 7 || type == 10 || type == 11
				|| type == 12 || type == 14 || type == 15 || type == 16
				|| type == 17 || type == 18 || type == 19 || type == 21
				|| type == 22 || type == 23 || type == 24 || type == 25
				|| type == 26 || type == 27)
			JOptionPane.showMessageDialog(frame, warnMsgStr);
		else if (type == 4) {
			// providing confirmation before exiting the software
			n = JOptionPane.showConfirmDialog(frame, warnMsgStr,
					"Confirmation Message", JOptionPane.YES_NO_OPTION);
			if (n == 0)
				System.exit(0);
		} else if (type == 5) {
			FileOperReadWrite forw = new FileOperReadWrite();
			n = JOptionPane.showConfirmDialog(frame, warnMsgStr, "Save",
					JOptionPane.YES_NO_OPTION);
			if (n == 0)
				forw.writeDataToFile(encTxt.filename, encTxt.jta.getText());
		} else if (type == 8 || type == 9 || type == 13) {
			n = JOptionPane.showConfirmDialog(frame, warnMsgStr,
					"Switching Modes", JOptionPane.YES_NO_OPTION);
		}
		// returning zero for type equals to 8 or 9 means user selected yes
		return n;
	}
}
