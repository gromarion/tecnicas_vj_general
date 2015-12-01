package animator;

public class Sin implements Function {
	@Override
	public double apply(double x) {
		return 2 * Math.sin(x);
	}
}
