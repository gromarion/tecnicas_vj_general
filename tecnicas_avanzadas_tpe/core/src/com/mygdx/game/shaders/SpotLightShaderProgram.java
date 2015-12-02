package com.mygdx.game.shaders;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.light.Light;
import com.mygdx.game.light.SpotLight;

public class SpotLightShaderProgram extends LightShaderProgram {

	private static final String vertexShaderPath = "shaders/SpotLightVS.glsl";
	private static final String fragmentShaderPath = "shaders/SpotLightFS.glsl";

	public SpotLightShaderProgram(Light spotLight) {
		super(Gdx.files.internal(vertexShaderPath).readString(), Gdx.files.internal(fragmentShaderPath).readString(), spotLight);
	}

	public void setup() {
		super.setup();
		setUniformf("u_light_cutoffAngle", ((SpotLight) light).cutoffAngle());
		setUniformf("u_light_position", light.position());
		setUniform3fv("u_spot_direction", ((SpotLight) light).toArray(), 0, 3);
		//setUniformf("u_light_falloff", ((SpotLight) light).falloff());
	}
}
