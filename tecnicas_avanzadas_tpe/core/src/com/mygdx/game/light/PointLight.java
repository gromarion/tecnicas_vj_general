package com.mygdx.game.light;

public class PointLight extends Light {
	private float inner_radius;
	
	public PointLight(float x, float y, float z, float inner_radius) {
		super(x, y, z);
		this.inner_radius = inner_radius;
	}
	
	public float innerRadius() {
		return inner_radius;
	}
}
