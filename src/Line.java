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
		initPoints();
	}

	public Line(double x1, double y1, double x2, double y2, Color c, int t) {
		super(x1, y1, x2, y2);
		color = c;
		thickness = t;
		setConnections(x1, y1, x2, y2);
		initPoints();
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
	
	public void initPoints(){
		points = new ResizeRect[2];
		points[0] = new ResizeRect((int)x1, (int)y1, this);
		points[1] = new ResizeRect((int)x2, (int)y2, this);
	}
	public void changeResizeRect(int pos, int x, int y) {
		points[pos].setRect(x, y);
		updateLine();
	}
	
	public void updateLine(){
		this.setLine(points[0].getCenterX(), points[0].getCenterY(), 
				points[1].getCenterX(), points[1].getCenterY());
	}
}
