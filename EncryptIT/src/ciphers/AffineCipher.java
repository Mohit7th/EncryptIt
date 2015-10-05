package ciphers;

/*	
 *	Implementing Affine cipher (additive + multipicative) E = (p*key+key2)%88 & D = ((c-key)*ikey)%88
 *	Date : 15-02-2015 - 14-05-2015
 */

import java.io.*;
import readwrite.*;

public class AffineCipher {
	// affine method that will call encryption or decryption process
	public String affineMeth(String fileName, int keyTemp[], int chk)
			throws IOException {
		String msg = "";
		boolean file_status = false;

		if (fileName.substring(0, 4).equals("None"))
			file_status = false;
		else
			file_status = true;

		// class that will read file and return its contents
		FileOperReadWrite frw = new FileOperReadWrite();
		if (file_status) {
			// reading file name from the user
			msg = frw.readFileData(fileName);
		} else {
			msg = fileName.substring(4, fileName.length());
		}

		CipherProcess cp = new CipherProcess();
		// choosing encryption or decryption process
		switch (chk) {
		case 1:
			// call encrypt method with 2 keys for encryption process
			msg = cp.encryptMsg(msg, keyTemp[1], keyTemp[0]);
			break;
		case 2:
			// key[1]=mulKey , key[0]=addKey & invKey inverse key
			int index = msg.lastIndexOf('$');
			msg = msg.substring(0, index);

			int invKey = cp.calMultiInv(keyTemp[1]);
			msg = cp.decryptMsg(msg, invKey, keyTemp[0]);
			break;
		}
		return msg;
	}
}
