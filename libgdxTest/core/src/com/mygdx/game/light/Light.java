package com.mygdx.game.light;

import com.badlogic.gdx.math.Vector3;

public class Light {
	Vector3 light;
	Vector3 position;
	float r, g, b, a, spec_r, spec_g, spec_b, spec_a, intensity;
	
	public Light(float x, float y, float z) {
		light = new Vector3(x, y, z);
		position = new Vector3(0, 0, 0);
		r = g = b = spec_r = spec_g = spec_b = 0;
		a = spec_a = intensity = 1;
	}
	
	public Light position(float x, float y, float z) {
		position = new Vector3(x, y, z);
		return this;
	}
	
	public Light color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		return this;
	}
	
	public Light specularColor(float r, float g, float b) {
		spec_r = r;
		spec_g = g;
		spec_b = b;
		return this;
	}
	
	public float[] specularColorArray() {
		return new float[] {spec_r, spec_g, spec_b, spec_a};
	}
	
	public Vector3 position() {
		return position;
	}
	
	public Vector3 finalPosition() {
		return light.add(position);
	}
	
	public float intensity() {
		return intensity;
	}
	
	public Light intensity(float intensity) {
		this.intensity = intensity;
		return this;
	}
	
	public float[] toArray() {
		Vector3 finalPosition = finalPosition();
		return new float[] {finalPosition.x, finalPosition.y, finalPosition.z};
	}

	public float[] colorArray() {
		return new float[] {r, g, b, a};
	}
}
