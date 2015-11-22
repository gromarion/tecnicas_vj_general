package com.mygdx.game.input;

import java.net.Socket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TranslationInputHandler implements InputHandler {

	private static final int KEYBOARD_W = Input.Keys.W;
	private static final int KEYBOARD_D = Input.Keys.D;
	private static final int KEYBOARD_A = Input.Keys.A;
	private static final int KEYBOARD_S = Input.Keys.S;
	private static final int KEYBOARD_Q = Input.Keys.Q;
	private static final int KEYBOARD_E = Input.Keys.E;
	private final float translationSpeed;
	
	private int lastMousePositionInX;
	private int lastMousePositionInY;
	private Vector3 traslation;
	private Vector2 rotation;
	
	public TranslationInputHandler(final float translationSpeed) {
		lastMousePositionInX = Gdx.input.getX();
		lastMousePositionInY = Gdx.input.getY();
		this.translationSpeed = translationSpeed;
	}
	
	@Override
	public void handleInput(Socket clientSocket, String input) {
		int parsed_input = parseInput(input);
		if (parsed_input == -1) {
			return;
		}
		float x = 0, y = 0, z = 0;
		switch (parsed_input) {
		case KEYBOARD_W:
			z -= translationSpeed;
			break;
		case KEYBOARD_S:
			z += translationSpeed;
			break;
		case KEYBOARD_A:
			x -= translationSpeed;
			break;
		case KEYBOARD_D:
			x += translationSpeed;
			break;
		case KEYBOARD_Q:
			y -= translationSpeed;
			break;
		case KEYBOARD_E:
			y += translationSpeed;
			break;
		}
		traslation = new Vector3(x, y, z);
		rotation = new Vector2(Gdx.input.getX(), Gdx.input.getY());
	}
	
	private int parseInput(String input) {
		switch (input) {
		case "w":
			return KEYBOARD_W;
		case "a":
			return KEYBOARD_A;
		case "s":
			return KEYBOARD_S;
		case "d":
			return KEYBOARD_D;
		}
		return -1;
	}
	
	@Override
	public Vector2 rotation() {
		return rotation;
	}
	
	@Override
	public Vector3 traslation() {
		return traslation;
	}
}
