import java.util.ArrayList;

public class MidPointCircle {
	private Point p1;
	private Point p2;
	ArrayList<Point> allPoints = new ArrayList<Point>();
	
	public MidPointCircle(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public ArrayList<Point> getAllPoints()
	{
		double radius = calculateRadius(p1, p2)+.5;
		int x=0;
		int y = (int)radius;
		double p = 1-radius;
		while(x<=y)
		{
			allPoints.add(new Point(x, y));
			//set8Pixel(g, p1, x, y);
			
			if(p<0)
			{
				p = p+2*x+3;
			}
			else
			{
				p = p + 2*(x-y)+5;
				y--;
			}
			x++;
		}
		
		return allPoints;
	}
	
	public double calculateRadius(Point p1, Point p2)
	{
		return Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2.0) + Math.pow(p1.getY()-p2.getY(), 2));
	}

}
