package com.mygdx.game.shaders;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.light.Light;
import com.mygdx.game.light.PointLight;

public class PointLightShaderProgram extends LightShaderProgram {
	private static final String vertexShaderPath = "shaders/PointLightVS.glsl";
	private static final String fragmentShaderPath = "shaders/PointLightFS.glsl";

	public PointLightShaderProgram(Light pointLight) {
		super(Gdx.files.internal(vertexShaderPath).readString(), Gdx.files.internal(fragmentShaderPath).readString(), pointLight);
	}
	
	public void setup() {
		super.setup();
		setUniformf("l_radius", ((PointLight) light).innerRadius());
		setUniformf("l_position", light.position());
	}
}
