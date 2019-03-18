import java.util.ArrayList;

public class BresenhumLine {
	
	private Point p1;
	private Point p2;
	ArrayList<Point> allPoints = new ArrayList<Point>();
	
	public BresenhumLine(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public ArrayList<Point> getAllPoints()
	{
		
		int dy1 = Math.abs(p1.getY()-p2.getY());
        int dx1 = Math.abs(p1.getX()-p2.getX());
        double m = (double)(p1.getY()-p2.getY())/(p1.getX()-p2.getX());
        int k = 1;
        if(m<0)
        {
        	k=-1;
        }
        		                
        if(dy1>dx1)
        {
    	   if(p1.getY() > p2.getY()) 
            {
            	Point temp = p1;
            	p1 = p2;
            	p2 = temp;
            }
            
            int dx = p2.getX()-p1.getX();
            
            int dy = p2.getY()-p1.getY();
                                                                                                                        
            int dt = 2*(dx*k-dy);
            
            int ds = 2*dx*k;
            
            int d = 2*dx*k - dy;
            
            int X = p1.getX();
            
            int Y = p1.getY();

		   allPoints.add(new Point(X, Y));
     	//g.fillOval(X,Y,3, 3);
     	
		   int x = p1.getX();
		   int y = p1.getY();
            
		   while(y<p2.getY())
		   {
			   y++;
			   if(d<0)
			   {
				   d = d +ds;
			   }
			   else {
				   x+=k;
				   d = d + dt;
			   }
     		
			   allPoints.add(new Point(x, y));
         	//g.fillOval(x,y,3, 3);                		
     		
		   } 
        }
        else {
     	                
        	if(p1.getX() > p2.getX())
        	{
        		Point temp = p1;
        		p1 = p2;
        		p2 = temp;
        	}
     
        	int dx = p2.getX()-p1.getX();
     
        	int dy = p2.getY()-p1.getY();
                                                                                                                 
        	int dt = 2*(dy*k-dx);
     
        	int ds = 2*dy*k;
     
        	int d = 2*dy*k - dx;
     
        	int X = p1.getX();
     
        	int Y = p1.getY();
	
	 //System.out.println("dy = "+d);
        	allPoints.add(new Point(X, Y));
	//g.fillOval(X,Y,3, 3);
	
        	int x = p1.getX();
        	int y = p1.getY();
     
        	while(x<p2.getX())
        	{
        		//System.out.println(d);
        		//System.out.println(x+" "+y);
        		x++;
        		if(d<0)
        		{
        			d = d +ds;
        		}
        		else {
				y+=k;
				d = d + dt;
        		}
		                		
        		X = x;
        		Y = y;
        		allPoints.add(new Point(X, Y));
    	                		
		
        	}     

        }
		
		
		return allPoints;
	}
	
	

}
