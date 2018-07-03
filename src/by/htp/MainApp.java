package by.htp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Scanner;

public class MainApp {

	public static void main(String[] args) throws IOException {
		int numberForReplace = 5;
		String symbolForReplace = "P";
		
		File f = new File("resorces/sample.txt");
		if (!f.exists()) {
			f.createNewFile();
		}

		InputStream is = new FileInputStream(f);
		int bytes = is.available();
		System.out.println("available:" + bytes);
		
		OutputStream os = new FileOutputStream(f);
		os.write("Hello Wolrd".getBytes());

		bytes = is.available();
		System.out.println("available:" + bytes);
	
		
		
		FileReader fileWithSentens = new FileReader("resorces/sentens.txt");
		StringBuffer text = new StringBuffer();
		Scanner scanner = new Scanner(fileWithSentens);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			text.append(line);
		}
		System.out.println(text);
		
	}
}
