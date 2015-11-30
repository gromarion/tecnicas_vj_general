package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private static final int port = 9999;

	public Server() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			System.out.println("Conectado con:" + clientSocket.getInetAddress() + ":" + clientSocket.getLocalPort());
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream());
			while (true) {
				while (inputStream.ready()) {
					String input = inputStream.readLine();
					outputStream.write(input + "\n");
//					Thread.sleep(50);
					outputStream.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server();
	}

}
