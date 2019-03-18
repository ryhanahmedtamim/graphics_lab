import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JToolBar;

import com.sun.media.sound.AlawCodec;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;

public class GraphicsLab {

	private JFrame frame;
	
	
	private JButton Directline = new JButton("DirectLine");
	private JButton Dda = new JButton("DDA");
	private JButton Bresenhum = new JButton("Bresenhum");
	private JButton bCircle = new JButton("B Circle");
	private JButton mCircle = new JButton("M Circle");
	JButton btnSelect = new JButton("Select");
	JButton btnSClip = new JButton("S Clip");
	JButton btnMClip = new JButton("M Clip");
	private int k2 = 0;
	private Graphics g;
	int x1,y1,x2,y2;
	Point P1 = new Point(0,0);
	Point P2 = new Point(0,0);
	int doNotRePaint = 0;
	Point temPoint = new Point(0,0);
	
	Point windowPoint1;
	Point windowPoint2;
	
	//ArrayList<Line> allLines = new ArrayList<Line>();

	
	ArrayList<Point> points = new ArrayList<Point>();
	ArrayList<Line> lines = new ArrayList<Line>();
	ArrayList<Circle> circles =new ArrayList<Circle>();	
	Circle tempCircle = new Circle(null,null);
	Rectangle rectangle = new Rectangle(null,null);
	
	ArrayList<Point> recPoints = new ArrayList<Point>();
	
	/**
	 * Launch the application.
	 */
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicsLab window = new GraphicsLab();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public GraphicsLab() {
		initialize();
	}

	
	 
	MouseListener mouseListener = new MouseAdapter() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			y2 = e.getY();
			x2 = e.getX();			
			P2.setX(x2);
			P2.setY(y2);
			removeALine();
			paint();
			
			if(k2>0 && k2 <6)
			{
				lines.add(new Line(points));
				circles.add(tempCircle);
			}
			
			
	
				paintAllLine();
				paintAllCircle();
			
			
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			x1=x2=e.getX();
			y1=y2=e.getY();
			
			P1 = new Point(x1,y1);
			P2 = new Point(x2,y2);
			removeRectangle();
			paint();
			
		
		}		

	};
	
	MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
		
		@Override
		public void mouseDragged(MouseEvent e) {
			
			y2 = e.getY();
			x2 = e.getX();
			
			P2.setX(x2);
			P2.setY(y2);
			//paint();
			
			if(k2==1 || k2==2 || k2==3)
			{
				removeALine();
			}
			else if(k2 == 4 || k2 == 5)
			{
				removeACircle();
			}
			else if(k2 == 6 && btnSelect.getText().equals("Unselect")) {
				
				removeRectangle();
			}
			paint();
			
			
			
			
			//System.out.println(P2);
			
		}
		
	};
	
	Color color;
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		color = frame.getBackground();
		
		Directline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				k2=1;
				buttonDisable();
			}
		});
		
		Directline.setBounds(0, 0, 122, 25);
		Dda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				k2=2;
				buttonDisable();
			}
		});
		
		Dda.setBounds(0, 25, 122, 25);
		Bresenhum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				k2=3;
				buttonDisable();
			}
		});
		Bresenhum.setBounds(0, 49, 122, 25);
		bCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				k2 = 4;
				buttonDisable();
			}
		});
		bCircle.setBounds(123, 0, 122, 25);
		mCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				k2 = 5;
				buttonDisable();
			}
		});
		mCircle.setBounds(123, 25, 122, 25);
		frame.getContentPane().add(Directline);
		frame.getContentPane().add(Dda);
		frame.getContentPane().add(Bresenhum);
		frame.getContentPane().add(bCircle);
		frame.getContentPane().add(mCircle);
		
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(btnSelect.getText().equals("Select"))
				{
					btnSelect.setText("Unselect");
				}
				else {
					removeRectangle();
					recPoints.clear();
					btnSelect.setText("Select");
				}
				
				
				k2=6;
				
				buttonDisable();
			}
			
		});
		btnSelect.setBounds(246, 0, 117, 25);
		frame.getContentPane().add(btnSelect);
		
		
		//clipping btn
		btnSClip.setVisible(false);
		btnSClip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				k2 = 7;
				
				for(int i=0; i<lines.size(); i++)
				{
					ArrayList<Point> temp = lines.get(i).getAllPoints();
					
					for(int j=0; j<temp.size(); j++)
					{
						removePixel(temp.get(j));
					}
				}
				
				CohenSutherland cohenSutherland = new CohenSutherland(windowPoint1, windowPoint2, lines);
				//lines.clear();
				
				//System.out.println(cohenSutherland.clipAllLine());
				lines = cohenSutherland.clipAllLine();
				
				//frame.repaint();
				
				
				paint();
				//paintAllLine();
				
				//System.out.println("h");
			}
		});
		btnSClip.setBounds(246, 25, 117, 25);//
		frame.getContentPane().add(btnSClip);
		
		
		btnMClip.setVisible(false);
		btnMClip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				k2 = 7;
				
				for(int i=0; i<lines.size(); i++)
				{
					ArrayList<Point> temp = lines.get(i).getAllPoints();
					
					for(int j=0; j<temp.size(); j++)
					{
						removePixel(temp.get(j));
					}
				}
				
				//CohenSutherland cohenSutherland = new CohenSutherland(windowPoint1, windowPoint2, lines);
				//lines.clear();
				
				//System.out.println(cohenSutherland.clipAllLine());
				//lines = cohenSutherland.clipAllLine();
				
				MidPointClip midPointClip = new MidPointClip(windowPoint1, windowPoint2, lines, frame.getGraphics());
				lines = midPointClip.clipAllLine();
				
				//frame.repaint();
				
				
				paint();
				
				k2 = 8;
			}
		});
		btnMClip.setBounds(246, 49, 117, 25);
		frame.getContentPane().add(btnMClip);
		
		
		frame.getContentPane().addMouseMotionListener(mouseMotionListener);
		frame.getContentPane().addMouseListener(mouseListener);
		
		
		//g = frame.getContentPane().getGraphics();
		
	}
	
	
	public void removeALine()
	{
		
		for(int i=0; i<points.size(); i++)
		{
			//System.out.println(points.get(i).getX());
			removePixel(points.get(i));
		}
	}
	
	public void removeRectangle()
	{
		
		for(int i=0; i<recPoints.size(); i++)
		{
			//System.out.println(recPoints.get(i).getX());
			removePixel(recPoints.get(i));
		}
	}
	
	public void removeACircle()
	{
		Point centerPoint = tempCircle.getCenterPoint();
		ArrayList<Point> criclePoints  = tempCircle.getAllPoints();
		for(int i=0; i<criclePoints.size(); i++)
		{
			//System.out.println(points.get(i).getX());
			remove8Pixel(g,centerPoint,criclePoints.get(i).getX(),criclePoints.get(i).getY());
		}
	}
	
	public void buttonDisable() 
	{
		if(k2==1)
		{
			Directline.setEnabled(false);
			Dda.setEnabled(true);
			Bresenhum.setEnabled(true);
			bCircle.setEnabled(true);
			mCircle.setEnabled(true);
		}
		else if(k2==2)
		{
			Directline.setEnabled(true);
			Dda.setEnabled(false);
			Bresenhum.setEnabled(true);
			bCircle.setEnabled(true);
			mCircle.setEnabled(true);
		}
		else if(k2 == 3)
		{
			Directline.setEnabled(true);
			Dda.setEnabled(true);
			Bresenhum.setEnabled(false);
			bCircle.setEnabled(true);
			mCircle.setEnabled(true);
		}
		else if(k2 == 4)
		{
			Directline.setEnabled(true);
			Dda.setEnabled(true);
			Bresenhum.setEnabled(true);
			bCircle.setEnabled(false);
			mCircle.setEnabled(true);
		}
		else if(k2 == 5)
		{
			Directline.setEnabled(true);
			Dda.setEnabled(true);
			Bresenhum.setEnabled(true);
			bCircle.setEnabled(true);
			mCircle.setEnabled(false);
		}
		else if(k2 == 6)
		{
			if(btnSelect.getText().equals("Unselect"))
			{
				Directline.setEnabled(false);
				Dda.setEnabled(false);
				Bresenhum.setEnabled(false);
				bCircle.setEnabled(false);
				mCircle.setEnabled(false);
				//btnMClip.setVisible(true);
				btnSClip.setVisible(true);
			}
			else {
				Directline.setEnabled(true);
				Dda.setEnabled(true);
				Bresenhum.setEnabled(true);
				bCircle.setEnabled(true);
				mCircle.setEnabled(true);
				//btnMClip.setVisible(false);
				btnSClip.setVisible(false);
			}
		}
		
	}

	public void setPixel(Graphics g, Point p)
	{
		
		g.fillOval(p.getX(), p.getY(), 3, 3);
		//System.out.println("2"+p);
	}
	
	public void removePixel( Point p)
	{
		g.setColor(color);
		g.fillOval(p.getX(), p.getY(), 3, 3);
	}
	
	
	public void paintAllLine()
	{
		//frame.repaint();
		
		
		
		for(int i=0; i<lines.size(); i++)
		{
			Line line = lines.get(i);
			//System.out.println(lines.get(i).getAllPoints().size());
			try {
				for(int j=0; j<line.getAllPoints().size(); j++)
				{
					Point point = line.getAllPoints().get(j);
					g.fillOval(point.getX(), point.getY(), 3, 3);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
	}
	
	public void paintAllCircle()
	{

		
		
		for(int i=0; i<circles.size(); i++)
		{
			Circle circle1 = circles.get(i);
			Point centerPoint = circle1.getCenterPoint();
			try {
				for(int j=0; j<circle1.getAllPoints().size(); j++)
				{
					Point point = circle1.getAllPoints().get(j);
					
					set8Pixel(g, centerPoint, point.getX(), point.getY());
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
	
	
	public void bresenhumMethod(Graphics g, Point p1, Point p2)
	{
			BresenhumLine bresenhumLine = new BresenhumLine(p1, p2);
			
			ArrayList<Point> allPoints = bresenhumLine.getAllPoints();
			
			for(int i=0; i<allPoints.size(); i++)
			{
				setPixel(g, allPoints.get(i));
			}
			points = allPoints;
	}
	
	public void ddaMethod(Graphics g, Point p1, Point p2)
	{
		DigitalDifferential digitalDifferential = new DigitalDifferential(p1, p2);
		
		ArrayList<Point> allPoints = digitalDifferential.getAllPoints();
		
		for(int i=0; i<allPoints.size(); i++)
		{
			setPixel(g, allPoints.get(i));
		}
		points = allPoints;
	}
	
	
	public void directlineMethod(Graphics g, Point p1, Point p2)
	{
    	
		DirectLine directLine = new DirectLine(p1, p2);
		
		ArrayList<Point> allPoints = directLine.getAllPoints();
		
		for(int i=0; i<allPoints.size(); i++)
		{
			setPixel(g, allPoints.get(i));
		}
		points = allPoints;
		
	}
	
	public void midpointCircle(Graphics g, Point p1, Point p2)
	{
		MidPointCircle midPointCircle = new MidPointCircle(p1, p2);
		
		ArrayList<Point> allPoints = midPointCircle.getAllPoints();
		
		for(int i=0; i<allPoints.size(); i++)
		{
			set8Pixel(g,p1 ,allPoints.get(i).getX(),allPoints.get(i).getY());
		}
		tempCircle = new Circle(allPoints,p1);
		
	}
	

	public void bresenhumCircle(Graphics g, Point p1, Point p2)
	{
		
		BresenhumCirlce bresenhumCirlce = new BresenhumCirlce(p1, p2);
		
		ArrayList<Point> allPoints = bresenhumCirlce.getAllPoints();
		
		for(int i=0; i<allPoints.size(); i++)
		{
			set8Pixel(g,p1 ,allPoints.get(i).getX(),allPoints.get(i).getY());
		}
		tempCircle = new Circle(allPoints,p1);
		
	}
	
	public void drawRectangle(Graphics g, Point p1, Point p2)
	{
		windowPoint1 = p1;
		windowPoint2 = p2;
		Rectangle rectangle = new Rectangle(p1, p2);
		
		ArrayList<Point> allPoints = rectangle.getAllPoints();
		
		for(int i=0; i<allPoints.size(); i++)
		{
			setPixel(g, allPoints.get(i));
		}
		recPoints = allPoints;
	}
	
	public void set8Pixel(Graphics g, Point p1, int x,int y)
	{
		//g = frame.getGraphics();
		g.setColor(Color.BLACK);
		int h = p1.getY();
		int w = p1.getX();
		//1st half of 4th
		g.fillOval(w+x, h+y, 2, 2);
		
		// 2nd of 4th
		int tempx,tempy;
		tempx = y;
		tempy = x;
		g.fillOval(w+tempx, h+tempy, 2, 2);
		
		//1st half of 1st
		tempx = y;
		tempy = -1*x;
		g.fillOval(w+tempx, h+tempy, 2, 2);
		 
		 //2nd half of 1st
		 tempx = x;
		 tempy = -1*y;
		 g.fillOval(w+tempx, h+tempy, 2, 2);
		 
		//1st half of 2nd
		 tempx = -1*x;
		 tempy = -1*y;
		 
		g.fillOval(w+tempx, h+tempy, 2, 2);
		 
		 
		// 2nd half of 2nd codent 
		 tempx = -1*y;
		 tempy = -1*x;
		 g.fillOval(w+tempx, h+tempy, 2, 2);
	    
	    //1st half of 3rd 
	     tempx = -1*y;
		 tempy = x;
	     g.fillOval(w+tempx, h+tempy, 2, 2);
	    
	    //2nd half of 3rd
	    
	     tempx = -1*x;
	     tempy = y;
	     g.fillOval(w+tempx, h+tempy, 2, 2);
		
	}

	public void remove8Pixel(Graphics g, Point p1, int x,int y)
	{
		//g.setColor(Color.WHITE);
		g.setColor(color);
		int h = p1.getY();
		int w = p1.getX();
		//1st half of 4th
		g.fillOval(w+x, h+y, 2, 2);
		
		// 2nd of 4th
		int tempx,tempy;
		tempx = y;
		tempy = x;
		g.fillOval(w+tempx, h+tempy, 2, 2);
		
		//1st half of 1st
		tempx = y;
		tempy = -1*x;
		g.fillOval(w+tempx, h+tempy, 2, 2);
		 
		 //2nd half of 1st
		 tempx = x;
		 tempy = -1*y;
		 g.fillOval(w+tempx, h+tempy, 2, 2);
		 
		//1st half of 2nd
		 tempx = -1*x;
		 tempy = -1*y;
		 
		g.fillOval(w+tempx, h+tempy, 2, 2);
		 
		 
		// 2nd half of 2nd codent 
		 tempx = -1*y;
		 tempy = -1*x;
		 g.fillOval(w+tempx, h+tempy, 2, 2);
	    
	    //1st half of 3rd 
	     tempx = -1*y;
		 tempy = x;
	     g.fillOval(w+tempx, h+tempy, 2, 2);
	    
	    //2nd half of 3rd
	    
	     tempx = -1*x;
	     tempy = y;
	     g.fillOval(w+tempx, h+tempy, 2, 2);
	}

	public void paint()
	{
		 Point p1 = P1;
		 Point p2 = P2;
		 
		 g = frame.getContentPane().getGraphics();
       
	     if(k2 == 3)
	      {
	    	 bresenhumMethod(g,p1,p2);
	      }
	      else if (k2==1) 
	      {
	    	  directlineMethod(g, p1, p2);
	      }
	      else if(k2 == 2) 
	       {
	    	   ddaMethod(g, p1, p2);
	       }
	      else if(k2==4)
	      {
	    	  bresenhumCircle(g, p1, p2);
	      }
	      else if(k2 == 5)
	      {
	    	  midpointCircle(g, p1, p2);
	      }
	      else if(k2 == 6 && btnSelect.getText().equals("Unselect"))
	      {
	    	  drawRectangle(g,p1,p2);
	      }
	      else if(k2 == 7)
	      {
	    	 paintAllLine();
	    	 k2 = 6;
	      }
	      else if(k2 == 8)
	      {
	    	  paintAllLine();
		    	 k2 = 6;
	      }
	     
	}
}
