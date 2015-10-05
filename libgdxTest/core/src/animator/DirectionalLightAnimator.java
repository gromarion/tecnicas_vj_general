package animator;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.light.DirectionalLight;

public class DirectionalLightAnimator {
	private Vector3 pivot, axis;
	private float degrees;
	private DirectionalLight light;
	
	public DirectionalLightAnimator(DirectionalLight light, Vector3 axis, float degrees) {
		this.light = light;
		this.pivot = light.position();
		this.axis = axis;
		this.degrees = degrees;
	}
	
	public void animate() {
		pivot = pivot.rotate(axis, degrees);
		light.direction(pivot);
	}
}
