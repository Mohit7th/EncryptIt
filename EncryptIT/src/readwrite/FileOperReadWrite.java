package readwrite;

import java.io.*;

public class FileOperReadWrite {
	public String readFileData(String fileName) {
		int ch = 0;
		String msg = "";
		try (BufferedReader bis = new BufferedReader(new FileReader(fileName))) {
			// Retrieving character by character and concatinating to a string
			while ((ch = bis.read()) != -1)
				msg = msg + ((char) ch);
		} catch (FileNotFoundException e) {
			System.out.println("FileOperReadWrite (read) : " + e);
		} catch (IOException e) {
			System.out.println("FileOperReadWrite (read) : " + e);
		}
		return msg;
	}

	public void writeDataToFile(String fileName, String msg) {
		char arr[] = msg.toCharArray();
		try (BufferedWriter bos = new BufferedWriter(new FileWriter(fileName))) {
			// taking character form encrypted text and storing it to file
			for (char c : arr)
				bos.write(c);
		} catch (FileNotFoundException e) {
			System.out.println("FileOperReadWrite (write) : " + e);
		} catch (IOException e) {
			System.out.println("FileOperReadWrite (write) : " + e);
		}
	}
}