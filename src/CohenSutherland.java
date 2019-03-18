import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

public class CohenSutherland {
	private Point windowPoint1;
	private Point windowPoint2;
	private ArrayList<Line> lineList = new ArrayList<Line>();
	
	int xMax;
	int yMax;
	int xMin;
	int yMin;
	
	private static final int INSIDE = 0;
    private static final int LEFT   = 1;
    private static final int RIGHT  = 2;
    private static final int BOTTOM = 4;
    private static final int TOP    = 8;
	
	public CohenSutherland(Point windowPoint1, Point windowPoint2, ArrayList<Line> lineList) {
		
		this.windowPoint1 = windowPoint1;
		this.windowPoint2 = windowPoint2;
		this.lineList = lineList;
		
		int x1 = windowPoint1.getX();
		int y1 = windowPoint1.getY();
		int x2 = windowPoint2.getX();
		int y2 = windowPoint2.getY();
		
		if(x1<x2)
		{
			xMin = x1;
			xMax = x2;
		}
		else {
			xMin = x2;
			xMax = x1;
		}
		
		
		if(y1<y2)
		{
			yMin = y1;
			yMax = y2;
		}
		else {
			yMax = y1;
			yMin = y2;
		}
	}

	public Point getWindowPoint1() {
		return windowPoint1;
	}

	public Point getWindowPoint2() {
		return windowPoint2;
	}

	public ArrayList<Line> getLineList() {
		return lineList;
	}

	public void setWindowPoint1(Point windowPoint1) {
		this.windowPoint1 = windowPoint1;
	}

	public void setWindowPoint2(Point windowPoint2) {
		this.windowPoint2 = windowPoint2;
	}

	public void setLineList(ArrayList<Line> lineList) {
		this.lineList = lineList;
	}
	
	public ArrayList<Line> clipAllLine()
	{
		ArrayList<Line> resultLines = new ArrayList<Line>();
		//System.out.println(lineList);
		for(int i=0; i<lineList.size(); i++)
		{
			Line line = clip(lineList.get(i));
			
			//System.out.println(line);
			
			try {
				if(line != null)
				resultLines.add(line);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
		return resultLines;
	}
	
	 private final int regionCode(double x, double y) {
		 
		 
		 int code = 0;
	             
	        
	        if(x<xMin)
	        {
	        	code = LEFT;
	        }
	        else if(x>xMax)
	        {
	        	code = RIGHT;
	        }
	        else {
				code = INSIDE;
			}	
	             if (y < yMin) code |= BOTTOM;
	        else if (y > yMax) code |= TOP;
	        return code;
	    }
	
	
	 public Line clip(Line line) {
		 
		 	ArrayList<Point> points = line.getAllPoints();
		 	int numberOfPoints = points.size();
	        double p1x = points.get(0).getX();
	        double p1y = points.get(0).getY();
	        double p2x = points.get(numberOfPoints-1).getX();
	        double p2y = points.get(numberOfPoints-1).getY();
	        
	        Line resultLine;

	        double qx = 0d;
	        double qy = 0d;

	        boolean vertical = p1x == p2x;

	        double slope = vertical ? 0d : (p2y-p1y)/(p2x-p1x);

	        int c1 = regionCode(p1x, p1y);
	        int c2 = regionCode(p2x, p2y);

	        while (c1 != INSIDE || c2 != INSIDE) {

	            if ((c1 & c2) != INSIDE)
	                return null;

	            int c = c1 == INSIDE ? c2 : c1;

	            if ((c & LEFT) != INSIDE) {
	                qx = xMin;
	                qy = (feq(qx, p1x) ? 0 : qx-p1x)*slope + p1y;
	            }
	            else if ((c & RIGHT) != INSIDE) {
	                qx = xMax;
	                qy = (feq(qx, p1x) ? 0 : qx-p1x)*slope + p1y;
	            }
	            else if ((c & BOTTOM) != INSIDE) {
	                qy = yMin;
	                qx = vertical ? p1x : (feq(qy, p1y) ? 0 : qy-p1y)/slope + p1x;
	            }
	            else if ((c & TOP) != INSIDE) {
	                qy = yMax;
	                qx = vertical ? p1x : (feq(qy, p1y) ? 0 : qy-p1y)/slope + p1x;
	            }

	            if (c == c1) {
	                p1x = qx;
	                p1y = qy;
	                c1  = regionCode(p1x, p1y);
	            }
	            else 
	            {
	                p2x = qx;
	                p2y = qy;
	                c2 = regionCode(p2x, p2y);
	            }
	        }
	        
	        int xx1 = (int) p1x;
	        int yy1 = (int) p1y;
	        int xx2 = (int) p2x;
	        int yy2 = (int) p2y;
	        
	        DigitalDifferential differential = new DigitalDifferential(new Point(xx1,yy1),new Point(xx2, yy2));
	        //line.setLine(p1x, p1y, p2x, p2y);
	        
	        resultLine = new Line(differential.getAllPoints());
	       // resultPoints = differential.getAllPoints();
	        return resultLine;
	    }
	
	
	public boolean feq(double f1, double f2)
	{
		float EPSILON = 0.01f;
		return (Math.abs(f1 - f2) < EPSILON);
	}

	

}
