import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class MyRectangle extends Rectangle{
	private Color color;
	private int thickness;
	private Connection [] connectPts;
	
	public MyRectangle(int x, int y, int width, int height){
		super(x, y, width, height);
		connectPts = new Connection[1];
		connectPts[0] = new Connection(x + width, y + height);
	}

	public MyRectangle() {
		// TODO Auto-generated constructor stub
	}

	public MyRectangle(MyRectangle rect) {
		super(rect.x, rect.y, rect.width, rect.height);
		connectPts = new Connection[1];
		connectPts[0] = new Connection(rect.x + rect.width, rect.y + rect.height);
	}
	
	public MyRectangle(MyRectangle rect, Color c, int t){
		super(rect.x, rect.y, rect.width, rect.height);
		setColor(c);
		setThickness(t);
		connectPts = new Connection[1];
		connectPts[0] = new Connection(rect.x + rect.width/2, rect.y + rect.height/2);
	}
	
	public MyRectangle(int x, int y, int width, int height, Color c, int thick) {
		super(x, y, width, height);
		setColor(c);
		setThickness(thick);
		connectPts = new Connection[1];
		connectPts[0] = new Connection(x + width, y + height);
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
