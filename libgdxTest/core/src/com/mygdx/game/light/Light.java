package com.mygdx.game.light;

import com.badlogic.gdx.math.Vector3;

public class Light {
	Vector3 light;
	Vector3 position;
	float r, g, b, a;
	
	public Light(float x, float y, float z) {
		light = new Vector3(x, y, z);
		position = new Vector3(0, 0, 0);
		r = 0;
		g = 0;
		b = 0;
		a = 1;
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
	
	public Vector3 position() {
		return position;
	}
	
	public Vector3 finalPosition() {
		return light.add(position);
	}
	
	public float[] toArray() {
		Vector3 finalPosition = finalPosition();
		return new float[] {finalPosition.x, finalPosition.y, finalPosition.z};
	}

	public float[] colorArray() {
		return new float[] {r, g, b, a};
	}
}
