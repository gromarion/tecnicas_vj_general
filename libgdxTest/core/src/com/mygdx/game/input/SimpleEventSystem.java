package com.mygdx.game.input;

import com.badlogic.gdx.math.Matrix4;
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
	
	public void handleInput() {
		Vector3 vector = this.keyboardInputHandler.handleInput();
		Matrix4 translation = new Matrix4().translate(vector.x, vector.y, vector.z);
		camera.position(vector.x, vector.y, vector.z);
		camera.transform(translation);
	}
}
