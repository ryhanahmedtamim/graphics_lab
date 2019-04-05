import java.util.ArrayList;

public class SutherlandHodgMan {
	
	private Point windowPoint1;
	private Point windowPoint2;
	private ArrayList<Point> polygonPoints = new ArrayList<Point>();
	ArrayList<Point> clipperPoints = new ArrayList<Point>();
	
	int xMax;
	int yMax;
	int xMin;
	int yMin;
	
	public SutherlandHodgMan(Point windowPoint1, Point windowPoint2, ArrayList<Point> polygonPoints) {
		
		this.windowPoint1 = windowPoint1;
		this.windowPoint2 = windowPoint2;
		this.polygonPoints = polygonPoints;
		
		int x1 = windowPoint1.getX();
		int y1 = windowPoint1.getY();
		int x2 = windowPoint2.getX();
		int y2 = windowPoint2.getY();
		
		if(x1<x2)
		{
			xMin = x1;
			xMax = x2;
		}
		else 
		{
			xMin = x2;
			xMax = x1;
		}
		
		
		if(y1<y2)
		{
			yMin = y1;
			yMax = y2;
		}
		else 
		{
			yMax = y1;
			yMin = y2;
		}
		
		clipperPoints.add(new Point(xMin, yMin));
		clipperPoints.add(new Point(xMax, yMin));
		clipperPoints.add(new Point(xMax, yMax));
		clipperPoints.add(new Point(xMin, yMax));
		
		
	}
	
	
	 public ArrayList<Point> clipPolygon() {
		 
	               
	        ArrayList<Point> resultPolygonPoints = new ArrayList<Point>();
	        
	        int len = clipperPoints.size();
	        for(int i=0; i<len; i++)
	        {
	        	resultPolygonPoints.clear();
	        	Point P1 = clipperPoints.get((i + len - 1) % len);
	        	Point P2 = clipperPoints.get(i);
	        	
	        	int len2 = polygonPoints.size();
	        	for(int j=0; j<len2; j++)
	        	{
	        		 Point P = polygonPoints.get((j + len2 - 1) % len2);
	                 Point Q = polygonPoints.get(j);
	                 
	                 if (isLeft(P1, P2, Q)) {
	                     if (!isLeft(P1, P2, P))
	                         resultPolygonPoints.add(getIntersection(P1, P2, P, Q));
	                     resultPolygonPoints.add(Q);
	                 } else if (isLeft(P1, P2, P))
	                	 resultPolygonPoints.add(getIntersection(P1, P2, P, Q));
	             }
	        	}
	        	
	        	
	        	
	        	polygonPoints = resultPolygonPoints;
	        
	       
	        
	        
	        return resultPolygonPoints;
	    }
	
	private boolean isLeft(Point P1, Point P2, Point p) {
		
		return ((P2.getX() - P1.getX())*(p.getY() - P1.getY()) - (P2.getY() - P1.getY())*(p.getX() - P1.getX())) > 0;
      }
	
	
	 private Point getIntersection(Point p1, Point p2, Point P, Point Q) {
		 
		 	Point resultPoint;
	        double A1 = p2.getY() - p1.getY();
	        double B1 = p1.getX() - p2.getX();
	        double C1 = A1 * p2.getX() + B1 * p1.getY();
	 
	        double A2 = Q.getY() - P.getY();
	        double B2 = P.getX() - Q.getX();
	        double C2 = A2 * P.getX() + B2 * P.getY();
	 
	        double det = A1 * B2 - A2 * B1;
	        double x = (B2 * C1 - B1 * C2) / det;
	        double y = (A1 * C2 - A2 * C1) / det;
	        
	        resultPoint = new Point((int)x, (int)y);
	        return resultPoint;
	    }
	
	
	
	

}
