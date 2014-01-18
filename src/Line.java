import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.ArrayList;


public class Line extends Line2D.Double implements BasicShape{
	private Color color;
	private int thickness;
	private Connection [] connectPts;
	private ResizeRect [] points;
	
	public Line(){
		
	}
	
	public Line(double x1, double y1, double x2, double y2){
		super(x1, y1, x2, y2);
		setConnections(x1, y1, x2, y2);
	}

	public Line(double x1, double y1, double x2, double y2, Color c, int t) {
		super(x1, y1, x2, y2);
		color = c;
		thickness = t;
		setConnections(x1, y1, x2, y2);
	}
	
	public void setConnections(double x1, double y1, double x2, double y2){
		connectPts = new Connection[2];
		connectPts[0] = new Connection((int)x1, (int)y1);
		connectPts[1] = new Connection((int)x2, (int)y2);
	}
	
	public Connection[] getConnections(){
		return connectPts;
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

	public void setPoints(ResizeRect [] args){
		points = args;
	}

	public ResizeRect[] getPoints() {
		return points;
	}

	@Override
	public void changeResizeRect(int pos, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
