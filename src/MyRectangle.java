import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class MyRectangle extends Rectangle{
	private Color color;
	private int thickness;
	private Connection [] connectPts;
	
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

	public void setConnections(int x, int y, int width, int height){
		connectPts = new Connection[5];
		connectPts[0] = new Connection(x + width/2, y + height/2);
		connectPts[1] = new Connection(x, y);
		connectPts[2] = new Connection(x + width, y);
		connectPts[3] = new Connection(x, y + height);
		connectPts[4] = new Connection(x + width, y + height);
		
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

}
