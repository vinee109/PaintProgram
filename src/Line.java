import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.ArrayList;


public class Line extends Line2D.Double{
	private Color color;
	private int thickness;
	
	public Line(){
		
	}
	
	public Line(double x1, double y1, double x2, double y2){
		super(x1, y1, x2, y2);
	}

	public Line(double x1, double y1, double x2, double y2, Color c, int t) {
		super(x1, y1, x2, y2);
		color = c;
		thickness = t;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public void setThickness(int t){
		thickness = t;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getThickness(){
		return thickness;
	}
}
