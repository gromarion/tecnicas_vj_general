package com.mygdx.game.input;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public interface InputHandler {

	public void handleInput();
	public Vector2 rotation();
	public Vector3 traslation();
}
