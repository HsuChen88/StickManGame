import javafx.scene.shape.Line;

public class Land {
	public Line line;
	private double startX, endX, y;

	public Land(double startX, double endX, double y){
			this.startX = startX;
			this.endX = endX;
			this.y = y;
			line = new Line(startX, y, endX, y);
		}

	public double getStartX() {
		return this.startX;
	}
	
	public double getEndX() {
		return this.endX;
	}
	
	public double getY() {
		return this.y;
	}
	
}
