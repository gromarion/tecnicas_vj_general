package com.mygdx.game.camera;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public interface Camera {
	
	/**
	 * Rebuilds both View and Projection matrices
	 */
	public void update();
	
	public void transform(final Matrix4 view);
	public Matrix4 getViewMatrix();
	public Matrix4 getProjectionMatrix();
	public Matrix4 getCombinedMatrix();
	
	public void setNear(final float near);
	public void setFar(final float far);
	public float getNear();
	public float getFar();
	public Vector3 getPosition();
	public void position(float x, float y, float z);

}
