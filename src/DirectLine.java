import java.util.ArrayList;

public class DirectLine {
	
	private Point p1;
	private Point p2;
	ArrayList<Point> allPoints = new ArrayList<Point>();
	
	public DirectLine(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	
	public ArrayList<Point> getAllPoints()
	{
		double m = (double)(p1.getY()-p2.getY())/(p1.getX()-p2.getX());
        
        double c = p1.getY() - m* p1.getX();
        
        //System.out.println("x2 = "+p2.getX()+" "+" y2 ="+p2.getY());
        
       int dy = Math.abs(p1.getY()-p2.getY());
       int dx = Math.abs(p1.getX()-p2.getX());
        
        if(dy>dx)
        {
        	if(p1.getY() > p2.getY())
        	{
        		Point temp = p1;
        		p1 = p2;
        		p2 = temp;
        	}
        	
        	//System.out.println(p1.getY());
        	//x = 
        	for(int i=p1.getY(); i<=p2.getY(); i++)
        	{
        		double x = p1.getX();
        		if(dx!=0)
        		{
        			x =  ((double)i-c)/m;
        		}
        		else {
					x++;
				}
        		  
        		 
        		 int X = (int)(x+.5);
        		 
        		 
        		 
        		 //System.out.println(X+" "+Y);
        		 allPoints.add(new Point(X, i));
        		// g.fillOval(X,i,3, 3);
        		 
        	}
        }
        else {
        	
        	if(p1.getX() > p2.getX())
        	{
        		Point temp = p1;
        		p1 = p2;
        		p2 = temp;
        	}
        	
        	for(int i=p1.getX(); i<=p2.getX(); i++)
        	{
        		 double y =  (double)i*m + c;
        		 
        		 //int X = (int)((2*i)+(wd/2)+.5);
        		 int Y =(int) (y+.5);
        		 allPoints.add(new Point(i, Y));
        		 //g.fillOval(i,Y,3, 3);
        		 
        	}
			
		}
        
		return allPoints;
	}
	


}
