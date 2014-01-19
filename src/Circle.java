import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;


public class Circle extends Ellipse2D.Double implements BasicShape{
	private Color color;
	private int thickness;
	private Point center;
	private double radius;
	private Connection [] connectPts;
	private ResizeRect [] points;
	
	public Circle(){
	}

	public Circle(double x, double y, double width, double height) {
		super(x, y, width, height);
		center = new Point((int)(x+width/2) , (int)(y+height/2));
		radius = width/2;
		setConnections((int)(x+width/2), (int)(y+height/2), (int)(width/2));
		initializePoints();
	}
	
	public Circle(double x, double y, double width, double height, Color c, int t) {
		super(x, y, width, height);
		color = c;
		thickness = t;
		center = new Point((int)(x+width/2) , (int)(y+height/2));
		radius = height/2;
		setConnections((int)(x+width/2), (int)(y+height/2), (int)(width/2));
		initializePoints();
	}
	
	public void setConnections(int center_x, int center_y, int radius){
		connectPts = new Connection[5];
		connectPts[0] = new Connection(center_x, center_y);
		connectPts[1] = new Connection(center_x + radius, center_y);
		connectPts[2] = new Connection(center_x - radius, center_y);
		connectPts[3] = new Connection(center_x, center_y + radius);
		connectPts[4] = new Connection(center_x, center_y - radius);
	}
	
	public void initializePoints(){
		points = new ResizeRect[4];
		points[0] = new ResizeRect(center.x + (int)radius, center.y, this);
		points[1] = new ResizeRect(center.x - (int)radius, center.y, this);
		points[2] = new ResizeRect(center.x, center.y + (int)radius, this);
		points[3] = new ResizeRect(center.x, center.y - (int)radius, this);
		
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

	public void setPoints(ResizeRect[] args){
		points = args;
	}
	
	public ResizeRect[] getPoints() {
		return points;
	}

	public void changeResizeRect(int pos, int x, int y) {
		double r = getDistFromCenter(new Point(x, y));
		updatePoints(r);
		updateCirc();
	}
	
	public double getDistFromCenter(Point p){
		return Point.distance(center.x, center.y, p.x, p.y);
	}
	
	public void updatePoints(double r){
		points[0].setRect((int)(center.x + r), center.y);
		points[1].setRect((int)(center.x - r), center.y);
		points[2].setRect(center.x, (int)(center.y + r));
		points[3].setRect(center.x, (int)(center.y - r));
	}
	
	public void updateCirc(){
		radius = getDistFromCenter(points[0].getCenter());
		this.x = center.x - radius;
		this.y = center.y - radius;
		this.width = radius*2;
		this.height = radius*2;
	}
	
	public void setLocation(double xCoord, double yCoord){
		this.x = xCoord;
		this.y = yCoord;
		center = new Point((int)(this.x + radius), (int)(this.y + radius));
		updatePoints(radius);
		
	}
}
