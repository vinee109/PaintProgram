import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;


public class Circle extends Ellipse2D.Double{
	private Color color;
	private int thickness;
	private Point center;
	private double radius;
	
	public Circle(){
	}

	public Circle(double x, double y, double width, double height) {
		super(x, y, width, height);
		center = new Point((int)(x+width/2) , (int)(y+height/2));
		radius = width/2;
	}
	
	public Circle(double x, double y, double width, double height, Color c, int t) {
		super(x, y, width, height);
		color = c;
		thickness = t;
		center = new Point((int)(x+width/2) , (int)(y+height/2));
		radius = height/2;
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
}
