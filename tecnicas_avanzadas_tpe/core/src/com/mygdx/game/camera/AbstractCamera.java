package com.mygdx.game.camera;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class AbstractCamera implements Camera {

	private Matrix4 combined = new Matrix4();
	// The purpose of the view transform is to place the camera at the origin
	// and aim it, to make it look in the direction of the negative z-axis,2
	// with the y-axis pointing upwards and the x-axis pointing to the right.
	private Matrix4 view = new Matrix4();
	private Matrix4 projection = new Matrix4();

	private float near = 0.3f;
	private float far = 100;
	private Vector3 position = new Vector3(0, 0, 0);
	private Vector3 lookAt = new Vector3();
	private final Vector3 direction = new Vector3(0, 0, -1);
	private final Vector3 up = new Vector3(0, 1, 0);
	private Vector3 tmp = new Vector3();

	@Override
	public void update() {
		view.setToLookAt(position, tmp.set(position).add(direction), up);
		updateProjectionMatrix();
		updateCombinedMatrix();
	}

	@Override
	public void transform(final Matrix4 view) {
		if (view != null) {
			this.view.mul(view.inv());
			direction.rot(view);
			up.rot(view);
			updateCombinedMatrix();
		}
	}
	
	@Override
	public Vector3 getPosition() {
		return position;
	}
	
	@Override
	public void rotate(Vector3 axis, float degrees) {
		direction.rotate(axis, degrees);
		up.rotate(axis, degrees);
	}
	
	public void position(float x, float y, float z) {
		position = new Vector3(position.x + x, position.y + y, position.z + z);
	}

	@Override
	public void setNear(final float near) {
		this.near = near;
	}

	@Override
	public void setFar(final float far) {
		this.far = far;
	}

	@Override
	public float getNear() {
		return near;
	}

	@Override
	public float getFar() {
		return far;
	}

	@Override
	public Matrix4 getViewMatrix() {
		return view;
	}

	@Override
	public Matrix4 getCombinedMatrix() {
		return combined;
	}

	@Override
	public Matrix4 getProjectionMatrix() {
		return projection;
	}

	protected abstract void updateProjectionMatrix();

	protected void updateCombinedMatrix() {
		combined.set(getProjectionMatrix()).mul(view);
		//System.out.println(combined);
	}
}
