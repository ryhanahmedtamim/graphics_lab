import java.util.ArrayList;

public  class Line {
	
	private ArrayList<Point> allPoints = new ArrayList<Point>();
	
	public Line(ArrayList<Point> points)
	{
		this.allPoints = points;
	}

	public ArrayList<Point> getAllPoints() {
		return allPoints;
	}

	public void setAllPoints(ArrayList<Point> allPoints) {
		this.allPoints = allPoints;
	}

	@Override
	public String toString() {
		return "Line [allPoints=" + allPoints + "]";
	}
	
	
	
	

}
