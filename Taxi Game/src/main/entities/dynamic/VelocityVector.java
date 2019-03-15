package main.entities.dynamic;

// a utility class for the tick() methods in the dynamic entities
public class VelocityVector {
	
	private double xv, yv;		// pixel-precision
	private double r, angle;

	public VelocityVector(int xv, int yv) {
		this.xv = xv;
		this.yv = yv;
		calculateVector();
	}
	public VelocityVector(double r, double angle) {
		this.r = r;
		this.angle = angle;
		calculateCartesian();
	}
	
	public double getXV() {
		calculateCartesian();
		return xv;
	}
	public void setXV(double xv) {
		this.xv = xv;
		calculateVector();
	}
	
	public double getYV() {
		calculateCartesian();
		return yv;
	}
	public void setYV(double yv) {
		this.yv = yv;
		calculateVector();
	}

	public double getR() {
		calculateVector();
		return r;
	}
	public void setR(double r) {
		this.r = r;
		if (r != 0) calculateCartesian();
	}

	public double getAngle() {
		calculateVector();
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
		calculateCartesian();
	}
	
	public double getRadian() {
		return Math.atan2(yv, xv);
	}
	
	private void calculateCartesian() {
		xv = r * Math.cos(Math.toRadians(angle));
		yv = r * Math.sin(Math.toRadians(angle));
	}

	private void calculateVector() {
		r = Math.sqrt(Math.pow(xv, 2) + Math.pow(yv, 2));
		double radian = Math.atan2(yv, xv);
		angle = Math.toDegrees(radian);
	}
	
	
}
