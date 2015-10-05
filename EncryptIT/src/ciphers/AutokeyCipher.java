package ciphers;

public class AutokeyCipher {
	public String AutokeyMeth(String msg, int keyTemp, int chk) {

		AutokeyCipherProcess tcp = new AutokeyCipherProcess();
		switch (chk) {
		case 1:
			// method calling for encryption and decryption
			msg = tcp.encryptMsg(msg, keyTemp);
			break;
		case 2:
			// getting the text part (without key) from the file text
			msg = tcp.decryptMsg(msg, keyTemp);
			break;
		}
		return msg;
	}
}
