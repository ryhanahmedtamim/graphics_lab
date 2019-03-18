import java.util.ArrayList;



import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class MidPointClip {
	private Graphics graphics;
	private Point windowPoint1;
	private Point windowPoint2;
	private ArrayList<Line> lineList = new ArrayList<Line>();
	
	private ArrayList<Point> allRectanglePoints = new ArrayList<Point>();
	
	int xMax;
	int yMax;
	int xMin;
	int yMin;
	
	private static final int INSIDE = 0;
    private static final int LEFT   = 1;
    private static final int RIGHT  = 2;
    private static final int BOTTOM = 4;
    private static final int TOP    = 8;
    
	
	public MidPointClip(Point windowPoint1, Point windowPoint2, ArrayList<Line> lineList, Graphics graphics) {
		
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
		this.graphics = graphics;
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
			ArrayList<Point> points2  =	lineClip(lineList.get(i));
			try {
				
				if(points2.size()!= 0)
				{
					resultLines.add(new Line(points2));
				}
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
	
	int lll = 0;
	 public ArrayList<Point> lineClip(Line line) {
		 
		 lll++;
		 if(lll<1204)
		 {
			 return null;
		 }
		 
		 if(line.getAllPoints().isEmpty())
		 {
			 return null;
		 }
		 
		 	ArrayList<Point> points;
		 	
		 	try {
		 		points= line.getAllPoints();
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		 	
		 	ArrayList<Point> resultpoints = new ArrayList<Point>();
		 	ArrayList<Point> resultpoints2 = new ArrayList<Point>();
		 	int numberOfPoints = points.size();
	        double p1x = points.get(0).getX();
	        double p1y = points.get(0).getY();
	        double p2x = points.get(numberOfPoints-1).getX();
	        double p2y = points.get(numberOfPoints-1).getY();
	        
	        
	        int c1 = regionCode(p1x, p1y);
	        
	        int c2 = regionCode(p2x, p2y);
	        
	        double dx = Math.abs(p1x-p2x);
	        double dy = Math.abs(p1y-p2x);
	        
	        if(dx+dy == 2)
	        	return resultpoints;
	        
	        Point mid = new Point(0, 0);
	        int v;
	        	        
	        v=visibility(c1,c2);
	        
	        switch(v)
	         {
	         case 0:  /* Line conpletely visible */
	          // drawline(p1,p2,15);
	        	 System.out.println("hi");
	        	 Point p1 = points.get(0);
	        	 Point p2 = points.get(numberOfPoints-1);
	        	 DirectLine directLine = new DirectLine(p1, p2);
	        	 points = directLine.getAllPoints();
	        	 
	        	 return points;
	        	 
	         case 1:  /* Line completely invisible */
	        	 return resultpoints;

	         case 2:  /* line partly visible */
	        	 
	        	 double px = dx/2;
	        	 double py = dy/2;
	        	 
	        	 int c =  regionCode(px, px);
	        	
	        	 int v2 = visibility(c1, c);
	        	 ArrayList<Point> pp = new ArrayList<Point>();
	        	 Line line2 = new Line(pp);
	        	 
	        	//Point tempP1 = new Point(0, 0);
	        	//Point tempP2 = new Point(0, 0);
	        	
//	        	 while(v2==1)
//	        	 {
//	        		 c = regionCode(px, py);
//		        	 v2 = visibility(c, c2);
//	        		 
//	        	 }
	        	 
	        	 if(v2 != 1)
	        	 {
	        		 mid.setX((int)((dx)+.5));
		        	 mid.setY((int)((dy)+.5));
		        	
		        	 pp.add(points.get(0));
		        	 pp.add(mid);
		        	 line.setAllPoints(pp);
		        	 resultpoints = lineClip(line2); 
	        	 }
	        	 
	        	 
	        	 
	        	 c = regionCode(px+1, py+1);
	        	 v2 = visibility(c, c2);
	        	 pp.clear();
	        	 
	        	 if(v2!=1)
	        	 {
	        		 mid.setX(mid.getX()+1);
		        	 mid.setY(mid.getY()+1);
		        	
		        	 pp.add(mid);
		        	 
		        	 pp.add(points.get(numberOfPoints-1));
		        	 resultpoints2 = lineClip(line2);
					
	        	 }
	        		
				
	        	 
	           break;
	         }
	        

       		// graphics.fillOval(points.get(i).getX(), points.get(i).getY(), 3, 3);
	        	//resultpoints.addAll(resultpoints2);
	       // System.out.println(resultpoints2.size());
	        
	        for(int i=0; i<resultpoints2.size(); i++)
	        {
	        	try {
	        		resultpoints.add(resultpoints2.get(i));
					
				} catch (Exception e) {
					// TODO: handle exception
				}
	        }
	        
	        
	       return resultpoints;


	    }
	
	        
	        
	        
	        
	 int visibility (int c1,int c2)
	 {
	 
		 if ((c1 & c2) != INSIDE)
             return 1;
		 
		 if(c1==INSIDE && c2 == INSIDE)
			 return 0;
		 
		 return 2;

	 }
	
	public boolean feq(double f1, double f2)
	{
		float EPSILON = 0.01f;
		return (Math.abs(f1 - f2) < EPSILON);
	}

	

}
