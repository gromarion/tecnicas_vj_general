package com.mygdx.game.light;

import com.badlogic.gdx.math.Vector3;

public class PointLight extends Light {
	private Vector3 position;
	private float inner_radius;
	
	public PointLight(float x, float y, float z, float inner_radius) {
		super(x, y, z);
		this.inner_radius = inner_radius;
	}
	
	public Light position(float x, float y, float z) {
		position = new Vector3(x, y, z);
		return this;
	}
	
	public Vector3 position() {
		return position;
	}
	
	public Vector3 finalPosition() {
		return direction.add(position);
	}
	
	public float innerRadius() {
		return inner_radius;
	}
}
