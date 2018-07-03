package by.htp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		int port = 9696;
		try {
			// создаем сокет сервера и привязываем его к вышеуказанному порту
			serverSocket = new ServerSocket(port);
			System.out.println("Server started:");
			while (true) {
				// заставляем сервер ждать подключений и выводим сообщение
				// когда кто-то связался с сервером
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected:");

				InputStream is = null;
				BufferedInputStream bufferInput = null;

				OutputStream os = null;
				PrintWriter writer = null;

				try {
					// Берем входной и выходной потоки сокета, теперь можем
					// получать и отсылать данные клиенту.
					is = clientSocket.getInputStream();
					bufferInput = new BufferedInputStream(is);

					os = clientSocket.getOutputStream();
					writer = new PrintWriter(os);

					byte[] data = new byte[1024];

					// Читаем запрос клиента о назначение номера под замену
					bufferInput.read(data);
					String request = new String(data).trim();
					int number = Integer.parseInt(request);
					System.out.println("Data recieved(number):");
					System.out.println(number);
					// Отсылаем ответ клиенту о назначении номера
					writer.write("Number is received");
					writer.flush();

					// Читаем запрос клиента о назначении буквы
					bufferInput.read(data);
					String symbolReplace = new String(data).trim();
					System.out.println("Data recieved(symbol):");
					System.out.println(symbolReplace);
					// Отсылаем ответ клиенту о назначении буквы
					writer.write("Symbol is received");
					writer.flush();

					// Читаем текст из файла
					String text = getTextFromFile("resorces/sentens.txt");
					// заменяем буквы в тексте
					String newText = getNewTextWithReplaceSymbol(text, number, symbolReplace);
					// Отсылаем текст с замененными буквами клиенту
					writer.write(newText);
					writer.flush();
				} finally {
					bufferInput.close();
					writer.close();
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTextFromFile(String pathToFile) throws FileNotFoundException {
		File fileWithSentens = new File(pathToFile);
		Scanner scanner = new Scanner(fileWithSentens);
		StringBuffer text = new StringBuffer();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			text.append(line);
		}
		return text.toString();
	}

	public static String getNewTextWithReplaceSymbol(String text, int numberReplaceSimbol, String symbol) {
		StringBuffer textWithNewWord = new StringBuffer();
		String[] words = text.split(" ");

		for (int i = 0; i < words.length; i++) {
			StringBuffer wordStrBuffer = new StringBuffer(words[i]);
			if (wordStrBuffer.length() >= numberReplaceSimbol) {
				wordStrBuffer.replace(numberReplaceSimbol - 1, numberReplaceSimbol, symbol);
			}
			textWithNewWord.append(wordStrBuffer);
			textWithNewWord.append(" ");
		}

		return textWithNewWord.toString();
	}

}
