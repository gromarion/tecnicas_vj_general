package com.mygdx.game.input;

import java.net.Socket;

public interface EventSystem {
	
	public void handleInput(Socket socket, String input);
	
}
