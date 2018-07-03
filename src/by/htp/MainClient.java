package by.htp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter position from word for replace:");
		int numberReplaceSimbol = scanner.nextInt();
		
		System.out.println("Enter symbol:");
		String symbolForReplace = scanner.next();
		
		Socket clientSocket = new Socket("localhost", 9696);
		System.out.println("Client started:");

		// Берем входной поток сокета, теперь
		// можем получать данные клиентом.
		InputStream sin = clientSocket.getInputStream();
		BufferedInputStream bufferInput = new BufferedInputStream(sin);

		// Берем выходной поток сокета, теперь
		// можем отсылать данные клиентом.
		OutputStream sout = clientSocket.getOutputStream();
		PrintWriter writer = new PrintWriter(sout);

		// Отправляем серверу номер буквы в слове для замены
		writer.println(numberReplaceSimbol);
		writer.flush();
		
		// Ждем ответа сервера
		byte[] data = new byte[1024];
		bufferInput.read(data);
		System.out.println("Request about number:");
		System.out.println(new String(data));

		// Отправляем серверу Новую букву
		writer.println(symbolForReplace);
		writer.flush();
		// Ждем ответа сервера
		bufferInput.read(data);
		System.out.println("Response about symbol:");
		System.out.println(new String(data));

		// получаем текст с измененными буквами
		bufferInput.read(data);
		String textWhithReplaceSymbol = new String(data);
		System.out.println("Response about symbol:");
		System.out.println(textWhithReplaceSymbol);

		writeTextInFile(textWhithReplaceSymbol, "resorces/result.txt");
		
		writer.close();
		bufferInput.close();

		clientSocket.close();
	}
	
	public static void writeTextInFile(String text, String pathToFile) throws IOException {
		File f = new File(pathToFile);
		if (!f.exists()) {
			f.createNewFile();
		}

		OutputStream os = new FileOutputStream(f);
		os.write(text.getBytes());
		os.flush();
		os.close();
	}

}
