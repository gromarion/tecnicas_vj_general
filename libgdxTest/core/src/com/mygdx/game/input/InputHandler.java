package com.mygdx.game.input;

import java.net.Socket;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public interface InputHandler {

	public void handleInput(Socket clientSocket, String input);
	public Vector2 rotation();
	public Vector3 traslation();
}
