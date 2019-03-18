import java.util.ArrayList;

public class Circle {
	
	private ArrayList<Point> allPoints = new ArrayList<Point>();
	private Point centerPoint;
	
	public Circle(ArrayList<Point> points, Point p)
	{
		this.centerPoint = p;
		this.allPoints = points;
	}

	public Point getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}

	public ArrayList<Point> getAllPoints() {
		return allPoints;
	}

	public void setAllPoints(ArrayList<Point> allPoints) {
		this.allPoints = allPoints;
	}
}
