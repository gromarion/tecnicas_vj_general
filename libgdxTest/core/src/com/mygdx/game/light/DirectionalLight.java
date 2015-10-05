package com.mygdx.game.light;

import com.badlogic.gdx.math.Vector3;

public class DirectionalLight extends Light {
	private Vector3 direction;
	
	public DirectionalLight(float x, float y, float z) {
		super(x, y, z);
		direction = new Vector3(x, y, z);
	}
	
	public float[] toArray() {
		return new float[] { direction.x, direction.y, direction.z };
	}
	
	public Vector3 direction () {
		return direction;
	}
	
	public void direction(Vector3 direction) {
		this.direction = direction;
	}
}
