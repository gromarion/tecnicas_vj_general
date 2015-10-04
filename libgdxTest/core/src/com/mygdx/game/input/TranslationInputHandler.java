package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
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
	public void handleInput() {
		Matrix4 transform = new Matrix4();
		float x = 0, y = 0, z = 0;
		if (Gdx.input.isKeyPressed(KEYBOARD_W)) {
			z -= translationSpeed;
		}
		if (Gdx.input.isKeyPressed(KEYBOARD_S)) {
			z += translationSpeed;
		}
		if (Gdx.input.isKeyPressed(KEYBOARD_A)) {
			x -= translationSpeed;
		}
		if (Gdx.input.isKeyPressed(KEYBOARD_D)) {
			x += translationSpeed;
		}
		if (Gdx.input.isKeyPressed(KEYBOARD_Q)) {
			y -= translationSpeed;
		}
		if (Gdx.input.isKeyPressed(KEYBOARD_E)) {
			y += translationSpeed;
		}
		traslation = new Vector3(x, y, z);
		rotation = new Vector2(Gdx.input.getX(), Gdx.input.getY());
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
