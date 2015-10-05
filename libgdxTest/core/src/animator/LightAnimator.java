package animator;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.light.Light;

public class LightAnimator {
	private Light light;
	private Function f;
	private float step;
	private float current_value;
	private Vector3 pivot;
	
	public LightAnimator(Light light, Function f, float step) {
		this.light = light;
		this.f = f;
		this.step = step;
		this.pivot = light.position();
	}
	
	public void animate() {
		current_value += step;
		light.position(pivot.x + (float)f.apply(current_value), pivot.y, pivot.z);
	}
}
