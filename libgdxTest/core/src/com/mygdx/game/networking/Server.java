package com.mygdx.game.networking;

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
			while (inputStream.ready()) {
				String input = inputStream.readLine().trim();
				System.out.println(input);
				outputStream.write(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ZOMG");
		}
	}

}
