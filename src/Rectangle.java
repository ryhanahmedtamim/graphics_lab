import java.util.ArrayList;

public class Rectangle {
	private Point p1;
	private Point p2;
	
	
	public Rectangle(Point p1, Point p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}


	public Point getP1() {
		return p1;
	}


	public Point getP2() {
		return p2;
	}


	public void setP1(Point p1) {
		this.p1 = p1;
	}


	public void setP2(Point p2) {
		this.p2 = p2;
	}
	
	
	public ArrayList<Point> getAllPoints()
	{
		ArrayList<Point> points = new ArrayList<Point>();
		
		int x1,x2;
		int y1,y2;
		
		if(this.p1.getX()>p2.getX())
		{
			x1 = p2.getX();
			x2 = p1.getX();
		}
		else {
			x1 = p1.getX();
			x2 = p2.getX();
		}
		
		
		if(this.p1.getY()>p2.getY())
		{
			y1 = p2.getY();
			y2 = p1.getY();
		}
		else {
			y1 = p1.getY();
			y2 = p2.getY();
		}
		
		int x = x1;
		
		while(x1<=x2)
		{
			points.add(new Point(x1, y1));
			points.add(new Point(x1, y2));
			x1++;
		}
		while(y1<=y2)
		{
			points.add(new Point(x, y1));
			points.add(new Point(x2, y1));
			y1++;
		}
		
		
		return points;
	}


	@Override
	public String toString() {
		return "Rectangle [p1=" + p1 + ", p2=" + p2 + "]";
	}
	

}
