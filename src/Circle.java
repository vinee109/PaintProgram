import java.awt.Color;
import java.awt.geom.Ellipse2D;


public class Circle extends Ellipse2D.Double{
	private Color color;
	private int thickness;
	
	public Circle(){
	}

	public Circle(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	public Circle(double x, double y, double width, double height, Color c, int t) {
		super(x, y, width, height);
		color = c;
		thickness = t;
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
}
