package com.mygdx.game.shaders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.game.light.Light;

public abstract class LightShaderProgram extends ShaderProgram {
	protected Light light;

	public LightShaderProgram(String vertexShader, String fragmentShader, Light light) {
		super(vertexShader, fragmentShader);
		this.light = light;
	}	
	
	public void setup() {
		setUniform4fv("l_ambient", light.colorArray(), 0, 4);
		setUniform4fv("specular_color", light.specularColorArray(), 0, 4);
		setUniformf("l_intensity", light.intensity());;
	}
}
