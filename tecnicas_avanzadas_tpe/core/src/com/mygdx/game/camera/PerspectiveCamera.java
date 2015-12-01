package com.mygdx.game.camera;

import com.badlogic.gdx.math.Matrix4;

// In this type of projec- tion, the farther away an object lies 
// from the camera, the smaller it appears after projection. 
// In addition, parallel lines may converge at the horizon. 
// The perspective transform thus mimics the way we perceive objects’ size.

public class PerspectiveCamera extends AbstractCamera {

	private float aspectRatio;
	private float fov = 67;

	public PerspectiveCamera(final float fov, final float viewportWidth, final float viewportHeight) {
		aspectRatio = viewportWidth / viewportHeight;
		this.fov = Math.abs(fov);
		update();
	}

	public void setFov(final float fov) {
		this.fov = Math.abs(fov);
		updateProjectionMatrix();
	}

	@Override
	protected void updateProjectionMatrix() {
		final float nearp = Math.abs(getNear());
		final float farp = Math.abs(getFar());
		final float tmp1 = (float) (1.0 / Math.tan((fov * (Math.PI / 180)) / 2.0));
		final float tmp2 = (farp + nearp) / (nearp - farp);
		final float tmp3 = (2 * farp * nearp) / (nearp - farp);
		final Matrix4 projection = getProjectionMatrix();

		projection.val[Matrix4.M00] = tmp1 / aspectRatio;
		projection.val[Matrix4.M10] = 0;
		projection.val[Matrix4.M20] = 0;
		projection.val[Matrix4.M30] = 0;
		projection.val[Matrix4.M01] = 0;
		projection.val[Matrix4.M11] = tmp1;
		projection.val[Matrix4.M21] = 0;
		projection.val[Matrix4.M31] = 0;
		projection.val[Matrix4.M02] = 0;
		projection.val[Matrix4.M12] = 0;
		projection.val[Matrix4.M22] = tmp2;
		projection.val[Matrix4.M32] = -1;
		projection.val[Matrix4.M03] = 0;
		projection.val[Matrix4.M13] = 0;
		projection.val[Matrix4.M23] = tmp3;
		projection.val[Matrix4.M33] = 0;
	}
	
}
