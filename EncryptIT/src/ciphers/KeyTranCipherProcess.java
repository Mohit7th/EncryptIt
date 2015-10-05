package ciphers;

/*
 * class that will ecnrypt and decrypt the data, if data is not multiple
 * of four then addiing bogus data at the end of message
 */
public class KeyTranCipherProcess {
	/*
	 * encryptMsg method first check the length of the message sent, if it is
	 * not a multiple of 4. it means we cannot create blocks of equal length
	 */
	String encryptMsg(String plnTxt, int keyTemp[]) {
		char[] plnTxtArr = plnTxt.toCharArray();
		char msg[] = new char[4];
		int len = plnTxtArr.length;
		int m = len;
		char[] cip = { '#', '#', '#', '#' };

		// checking wether plaintext length is a multiple of 4
		if (m % 4 != 0) {
			// increasing length to make it multiple of 4
			while (len % 4 != 0) {
				len++;
			}
			// allocating new array that can hold message as well as bogus data
			char tempPlnTxtArr[] = new char[len];
			for (int i = 0; i < len; i++) {
				if (i < m) {
					tempPlnTxtArr[i] = plnTxtArr[i];
				} else {
					// adding bogus data at the end of array that is after
					// original message length
					tempPlnTxtArr[i] = 'z';
				}
			}
			plnTxtArr = tempPlnTxtArr;
		}

		char newMsg[] = new char[len];
		int t = 0;
		for (int i = 0; i < plnTxtArr.length;) {
			// getting first 4 characters from msg and in next loop next 4
			for (int j = 0; j < 4; j++) {
				msg[j] = plnTxtArr[i];
				i++;
			}
			// changing the position of msg i.e encrypting
			for (int j = 0; j < 4; j++) {
				cip[j] = msg[keyTemp[j]];
			}
			// copying 4 character in new array
			for (int j = 0; t < i; t++, j++) {
				newMsg[t] = cip[j];
			}
		}
		// converting array into string and returning it
		return (new String(newMsg));
	}

	// error in logic
	String decryptMsg(String cipTxt, int keyTemp[]) {
		char[] cipTxtArr = cipTxt.toCharArray();
		char msg[] = new char[4];
		int decKey[] = { 0, 1, 2, 3 };
		char[] pln = { '#', '#', '#', '#' };
		char newMsg[] = new char[cipTxtArr.length];
		int t = 0;

		// loops to convert the keyTemp into decryption keyTemp
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (keyTemp[i] < keyTemp[j]) {
					t = keyTemp[i];
					keyTemp[i] = keyTemp[j];
					keyTemp[j] = t;

					t = decKey[i];
					decKey[i] = decKey[j];
					decKey[j] = t;
				}
			}
		}

		// variable t for controlling the final array which will hold the
		// encrypted string
		t = 0;
		for (int i = 0; i < cipTxtArr.length;) {
			// getting first 4 characters from msg and in next loop next 4
			for (int j = 0; j < 4; j++) {
				msg[j] = cipTxtArr[i];
				i++;
			}

			// changing the position of msg i.e encrypting
			for (int j = 0; j < 4; j++) {
				pln[j] = msg[decKey[j]];
			}
			// assigning 4 encrypted values into a new array
			for (int j = 0; t < i; t++, j++) {
				newMsg[t] = pln[j];
			}
		}
		// converting array into string and returning it
		return (new String(newMsg));
	}
}
