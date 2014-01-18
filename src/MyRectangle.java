import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class MyRectangle extends Rectangle implements BasicShape{
	private Color color;
	private int thickness;
	private Connection [] connectPts;
	private ResizeRect [] points;
	
	public MyRectangle(int x, int y, int width, int height){
		super(x, y, width, height);
		setConnections(x, y, width, height);
	}

	public MyRectangle() {
		// TODO Auto-generated constructor stub
	}

	public MyRectangle(MyRectangle rect) {
		super(rect.x, rect.y, rect.width, rect.height);
		setConnections(rect.x, rect.y, rect.width, rect.height);
	}
	
	public MyRectangle(MyRectangle rect, Color c, int t){
		super(rect.x, rect.y, rect.width, rect.height);
		setColor(c);
		setThickness(t);
		setConnections(rect.x, rect.y, rect.width, rect.height);
	}
	
	public MyRectangle(int x, int y, int width, int height, Color c, int thick) {
		super(x, y, width, height);
		setColor(c);
		setThickness(thick);
		setConnections(x, y, width, height);
	}

	public void setPoints(ResizeRect [] args){
		points = args;
	}
	
	public ResizeRect [] getPoints(){
		return points;
	}
	public void setConnections(int x, int y, int width, int height){
		connectPts = new Connection[9];
		connectPts[0] = new Connection(x + width/2, y + height/2);
		connectPts[1] = new Connection(x, y);
		connectPts[2] = new Connection(x + width, y);
		connectPts[3] = new Connection(x, y + height);
		connectPts[4] = new Connection(x + width, y + height);
		connectPts[5] = new Connection(x + width/2, y);
		connectPts[6] = new Connection(x + width/2, y+height);
		connectPts[7] = new Connection(x, y+height/2);
		connectPts[8] = new Connection(x + width, y+height/2);
		
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
	
	public Connection [] getConnections(){
		return connectPts;
	}
	
	public void changeResizeRect(int pos, int x, int y){
		points[pos].setRect(x, y);
		updateRect();
	}
	
	public void updateRect(){
		double width = Math.abs(points[0].getCenterX() - points[1].getCenterX());
		double height = Math.abs(points[0].getCenterY() - points[1].getCenterY());
		this.setRect(points[0].getCenterX(), points[0].getCenterY(), width, height);
	}
	

}
