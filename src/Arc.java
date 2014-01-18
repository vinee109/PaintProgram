import java.awt.Color;
import java.awt.geom.Arc2D;


public class Arc extends Arc2D.Double implements BasicShape{
	private Color color;
	private int thickness;
	
	public Arc(){
		
	}

	public Arc(double x, double y, double w, double h, double start, double extent, int open) {
		super(x, y, w, h, start, extent, open);
	}
	
	public Arc(double x, double y, double w, double h, double start, double extent, int open, Color c, int t) {
		super(x, y, w, h, start, extent, open);
		color = c;
		thickness = t;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getThickness(){
		return thickness;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public void setThickness(int t){
		thickness = t;
	}

	@Override
	public ResizeRect[] getPoints() {
		// TODO Auto-generated method stub
		return null;
	}
}
