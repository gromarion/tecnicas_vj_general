package com.mygdx.game.shaders;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.light.DirectionalLight;

public class DirectionalLightShaderProgram extends LightShaderProgram {
	private static final String vertexShaderPath = "shaders/DirectionalLightVS.glsl";
	private static final String fragmentShaderPath = "shaders/DirectionalLightFS.glsl";

	public DirectionalLightShaderProgram(DirectionalLight light) {
		super(Gdx.files.internal(vertexShaderPath).readString(), Gdx.files.internal(fragmentShaderPath).readString(), light);
	}
	
	public void setup() {
		super.setup();
		setUniform3fv("l_direction", ((DirectionalLight) light).toArray(), 0, 3);
	}
}
