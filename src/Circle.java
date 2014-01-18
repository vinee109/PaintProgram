import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;


public class Circle extends Ellipse2D.Double implements BasicShape{
	private Color color;
	private int thickness;
	private Point center;
	private double radius;
	private Connection [] connectPts;
	
	public Circle(){
	}

	public Circle(double x, double y, double width, double height) {
		super(x, y, width, height);
		center = new Point((int)(x+width/2) , (int)(y+height/2));
		radius = width/2;
		setConnections((int)(x+width/2), (int)(y+height/2), (int)(width/2));
	}
	
	public Circle(double x, double y, double width, double height, Color c, int t) {
		super(x, y, width, height);
		color = c;
		thickness = t;
		center = new Point((int)(x+width/2) , (int)(y+height/2));
		radius = height/2;
		setConnections((int)(x+width/2), (int)(y+height/2), (int)(width/2));
	}
	
	public void setConnections(int center_x, int center_y, int radius){
		connectPts = new Connection[5];
		connectPts[0] = new Connection(center_x, center_y);
		connectPts[1] = new Connection(center_x + radius, center_y);
		connectPts[2] = new Connection(center_x - radius, center_y);
		connectPts[3] = new Connection(center_x, center_y + radius);
		connectPts[4] = new Connection(center_x, center_y - radius);
	}
	
	public void setThickness(int t){
		thickness = t;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public int getThickness(){
		return thickness;
	}
	
	public Color getColor(){
		return color;
	}
	
	public Point getCenter(){
		return center;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public Connection[] getConnections(){
		return connectPts;
	}

	@Override
	public ResizeRect[] getPoints() {
		// TODO Auto-generated method stub
		return null;
	}
}
