package com.mygdx.game.input;

import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.camera.Camera;

public class SimpleEventSystem implements EventSystem {
	
	private static final float translationSpeed = .5f;
	
	private final Camera camera;
	private final InputHandler keyboardInputHandler;
	
	public SimpleEventSystem(final Camera camera) {
		this.camera = camera;
		this.keyboardInputHandler = new TranslationInputHandler(translationSpeed);
	}
	
	public void handleInput() {
		Matrix4 translation = this.keyboardInputHandler.handleInput();
		camera.transform(translation);
	}
}
