import java.awt.Graphics;
import java.util.ArrayList;

public  class LineDraw {
		
	 private  ArrayList<Point> allPoints = new ArrayList<Point>();

	public LineDraw(ArrayList<Point> allPoints) {
		super();
		this.allPoints = allPoints;
	}
	 
	 
	public void paint(Graphics g) {
       // g.drawLine(this.x1, this.y1, this.x2, this.y2);
		
		for(int i=0; i<allPoints.size(); i++)
		{
			Point p = allPoints.get(i);
			g.fillOval(p.getX(), p.getY(), 3, 3);
		}
		
    }

}
