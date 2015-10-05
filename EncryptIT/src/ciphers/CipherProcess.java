package ciphers;

/*
 * class that is converting text into plaintext and vice versa. in the class affine cipher is used
 * to encrypt text and automatically calculating inverse key 
 */
public class CipherProcess {
	// domain to encrypt the message string
	private char alpha[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'a', 'b', 'c', '!', 'd', 'e', 'f', '@', 'g', 'h', 'i', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', '#', 'j', 'k',
			'l', '.', 'm', 'n', 'o', '%', 'p', 'q', 'r', '^', 's', 't', 'u',
			'&', 'v', 'w', 'x', '*', 'y', 'z', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '0', 'W', 'X', 'Y', 'Z', '<', '>', '/', ']', '(',
			')', '_', '-', ';', ':', '"', '?', '+', '=', '{', '}', '[', '|' };

	// method to encrypt the string selected from a file with multiplcative and
	// additive keys
	String encryptMsg(String plnTxt, int mulKey, int addKey) {
		int tempKey;
		char[] charMsg = plnTxt.toCharArray();
		// checking each character from the message and converting it to the
		// encrypted forn
		for (int i = 0; i < charMsg.length; i++) {
			for (int j = 0; j < alpha.length; j++) {
				// when character is found in the domain of alpha[]
				if (charMsg[i] == alpha[j]) {
					tempKey = (j * mulKey + addKey) % alpha.length;
					charMsg[i] = alpha[tempKey];
					break;
				}
			}
		}
		return (new String(charMsg));
	}

	String decryptMsg(String cipTxt, int invkey, int addKey) {
		int tempKey;
		char[] charMsg = cipTxt.toCharArray();
		for (int i = 0; i < charMsg.length; i++) {
			for (int j = 0; j < alpha.length; j++) {
				if (charMsg[i] == alpha[j]) {
					tempKey = ((j - addKey) * invkey) % alpha.length;
					// handling negative results in the text
					if (tempKey < 0) {
						tempKey = alpha.length + tempKey;
					}
					charMsg[i] = alpha[tempKey];
					break;
				}
			}
		}
		return (new String(charMsg));
	}

	int calMultiInv(int key) {
		// calculating inverse using extend eucliden
		int q, r1 = alpha.length, r2 = key, r = 0, t1 = 0, t2 = 1, t;
		do {
			q = (int) r1 / r2;
			r = r1 % r2;
			t = t1 - q * t2;

			r1 = r2;
			r2 = r;
			t1 = t2;
			t2 = t;
		} while (r > 0);
		// if negative result getting result back in the domain
		if (t1 < 0)
			t1 = alpha.length + t1;
		return (t1);
	}
}
