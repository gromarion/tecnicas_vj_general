package com.mygdx.game.light;

import com.badlogic.gdx.math.Vector3;

public class SpotLight extends Light {

	private Vector3 direction;
	private float angle = 20;
	private float cutoffAngle = (float) Math.cos(Math.toRadians(angle));
	private float falloff = 10;

	public SpotLight(float x, float y, float z) {
		super(x, y, z);
		direction = new Vector3(x, y, z);
	}

	public float[] toArray() {
		return new float[] { direction.x, direction.y, direction.z };
	}

	public Vector3 direction() {
		return direction;
	}

	public void direction(Vector3 direction) {
		this.direction = direction;
	}

	public float cutoffAngle() {
		return cutoffAngle;
	}

	public float falloff() {
		return falloff;
	}

}
