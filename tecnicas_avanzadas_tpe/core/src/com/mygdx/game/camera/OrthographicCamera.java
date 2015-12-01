package com.mygdx.game.camera;

import com.badlogic.gdx.math.Matrix4;

// The view volume of orthographic viewing is normally a rectangular box, 
// and the orthographic projection transforms this view volume into the unit cube. 
// The main characteristic of orthographic projection is that parallel lines remain 
// parallel after the transform. This transformation is a combination of a 
// translation and a scaling.

public class OrthographicCamera extends AbstractCamera {

	private float left = -1;
	private float right = 1;
	private float top = 1;
	private float bottom = -1;

	public OrthographicCamera(final float viewportWidth, final float viewportHeight) {
		right = viewportWidth / 2;
		top = viewportHeight / 2;
		left = -right;
		bottom = -top;
		update();
	}

	@Override
	protected void updateProjectionMatrix() {
		final float nearp = Math.abs(getNear());
		final float farp = Math.abs(getFar());
		final Matrix4 projection = getProjectionMatrix();

		projection.val[Matrix4.M00] = 2 / (right - left);
		projection.val[Matrix4.M10] = 0;
		projection.val[Matrix4.M20] = 0;
		projection.val[Matrix4.M30] = 0;
		projection.val[Matrix4.M01] = 0;
		projection.val[Matrix4.M11] = 2 / (top - bottom);
		projection.val[Matrix4.M21] = 0;
		projection.val[Matrix4.M31] = 0;
		projection.val[Matrix4.M02] = 0;
		projection.val[Matrix4.M12] = 0;
		projection.val[Matrix4.M22] = 2 / (farp - nearp);
		projection.val[Matrix4.M32] = 0;
		projection.val[Matrix4.M03] = 0;
		projection.val[Matrix4.M13] = 0;
		projection.val[Matrix4.M23] = 0;
		projection.val[Matrix4.M33] = 1;
	}

}
