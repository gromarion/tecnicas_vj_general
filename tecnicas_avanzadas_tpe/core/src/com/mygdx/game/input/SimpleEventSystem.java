package com.mygdx.game.input;

import java.net.Socket;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.camera.Camera;

public class SimpleEventSystem implements EventSystem {
	
	private static final float translationSpeed = .1f;
	
	private final Camera camera;
	private final InputHandler keyboardInputHandler;
	
	public SimpleEventSystem(final Camera camera) {
		this.camera = camera;
		this.keyboardInputHandler = new TranslationInputHandler(translationSpeed);
	}
	
	public void handleInput(Socket socket, String input) {
		this.keyboardInputHandler.handleInput(socket, input);
		Vector3 position = this.keyboardInputHandler.traslation();
		Vector2 rotation = this.keyboardInputHandler.rotation();
		Matrix4 translation = new Matrix4().translate(position.x, position.y, position.z);
		camera.position(position.x, position.y, position.z);
		camera.transform(translation);
	}
}
