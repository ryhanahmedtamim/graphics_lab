import java.util.ArrayList;

public class DigitalDifferential {
	
	private Point p1;
	private Point p2;
	ArrayList<Point> allPoints = new ArrayList<Point>();
	
	public DigitalDifferential(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	
	public ArrayList<Point> getAllPoints()
	{
		
		double m = (double)(p1.getY()-p2.getY())/(p1.getX()-p2.getX());
        
        // System.out.println(m);
         
        double c = p1.getY() - m* p1.getX();
         
         //System.out.println(m);
        int dy = Math.abs(p1.getY()-p2.getY());
        int dx = Math.abs(p1.getX()-p2.getX());
              
        if(dy>dx)
        {
       	   //System.out.println(m);
          	
        	if(p1.getY() > p2.getY())
         	{
         		Point temp = p1;
         		p1 = p2;
         		p2 = temp;
         	}
         	//System.out.println(p1.getY());
         	double xOdl = p1.getX();
         	int xoldInt = (int) (xOdl+.5);
         	
         	int X = (int)(xOdl+.5);
         	int Y = (int)(p1.getY()+.5);
         	
         	
    		allPoints.add(new Point(X, Y));
 
         	double x = p1.getX();
         	
         	for(int i=p1.getY()+1; i<=p2.getY(); i++)
         	{
         		
         		if(dx != 0)
         		{ 
         		  x = (double) (xOdl + 1/m);
         		  X = (int)(x+.5);
         		}
         		else {
					
         			x = p1.getX();
				}
         		  //Y = (int)((hi-(2*i)+.5);	                		
         		  xOdl = x;
         		  //System.out.println(xOdl);
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
         	
         	double yOdl = (double)(p1.getX()*m)+c;
         	//int xoldInt = (int) (xOdl+.5);
         	
         	int X = (int)(p1.getX()+.5);
         	int Y = (int)(yOdl+.5);
         	
         	 //System.out.println(Y);
         	allPoints.add(new Point(X, Y));
         	//g.fillOval(X,Y,3, 3);
         	
         	for(int i=p1.getX(); i<=p2.getX(); i++)
         	{
         		  double y = (double) (yOdl + m);
         		 
          		  Y = (int)(y+.5);
         		  yOdl = y;	
         		 allPoints.add(new Point(i, Y));
         		  //g.fillOval(i,Y,3, 3);
         		 
         	}
				
			}
		return allPoints;
	}

}
