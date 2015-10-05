package ciphers;

import java.io.IOException;
import readwrite.*;

public class KeyTranCipher {
	// readLine method can generate an exception
	public String KeyTranMeth(String fileName, int keyTemp[], int chk)
			throws IOException {
		String msg = "";
		boolean file_status = false;

		// when encryption is called without any file i.e using just the text
		// file_status = false
		if (fileName.substring(0, 4).equals("None"))
			file_status = false;
		else
			file_status = true;

		// class that will read data of the file and return by its method
		FileOperReadWrite frw = new FileOperReadWrite();

		if (file_status) {
			// reading file name from the user
			msg = frw.readFileData(fileName);
		} else {
			// removing the extra string from the message
			msg = fileName.substring(4, fileName.length());
		}

		KeyTranCipherProcess tcp = new KeyTranCipherProcess();
		switch (chk) {
		case 1:
			// method calling for encryption and decryption
			msg = tcp.encryptMsg(msg, keyTemp);
			break;
		case 2:
			// getting the text part (without key) from the file text
			int index = msg.lastIndexOf('$');
			msg = msg.substring(0, index);
			msg = tcp.decryptMsg(msg, keyTemp);
			break;
		}

		return msg;
	}
}
