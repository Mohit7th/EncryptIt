package ciphers;

public class AutokeyCipherProcess {
	private char alpha[] = { 'a', 'b', 'c', '!', 'd', 'e', 'f', '@', 'g', 'h',
			'i', '#', 'j', 'k', 'l', '$', 'm', 'n', 'o', ':', 'p', 'q', 'r',
			'^', 's', 't', 'u', '&', 'v', 'w', 'x', '*', 'y', 'z', '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '-', ',', '"', '>', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private int keys[];

	String encryptMsg(String plnTxt, int firstKey) {
		int tempKey;
		char[] charMsg = plnTxt.toCharArray();
		keys = new int[charMsg.length];
		// each character of plaintext will be compared with the text from from
		// domain and on the basis of its index new ciphertext will be generated
		for (int i = 0; i < charMsg.length; i++) {
			for (int j = 0; j < alpha.length; j++) {
				if (charMsg[i] == alpha[j]) {
					// performing mod operation by the length of domain
					tempKey = (j + firstKey) % alpha.length;
					charMsg[i] = alpha[tempKey];
					// next key will be previous value
					firstKey = j;
					break;
				}
			}
		}
		return (new String(charMsg));
	}

	String decryptMsg(String cipTxt, int firstKey) {
		int tempKey;
		char[] charMsg = cipTxt.toCharArray();
		keys = new int[charMsg.length];

		for (int i = 0; i < charMsg.length; i++) {
			for (int j = 0; j < alpha.length; j++) {
				if (charMsg[i] == alpha[j]) {
					// firstkey will be the length of username
					keys[i] = firstKey;
					tempKey = ((j - keys[i])) % alpha.length;
					// if getting negative value changing it to positive and in
					// the domain
					if (tempKey < 0) {
						tempKey = alpha.length + tempKey;
					}
					charMsg[i] = alpha[tempKey];
					// each iteration will change firstkey by providing previous
					// character value to it
					firstKey = tempKey;
					break;
				}
			}
		}
		return (new String(charMsg));
	}
}
