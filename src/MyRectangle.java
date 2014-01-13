import java.awt.Color;
import java.awt.Rectangle;


public class MyRectangle extends Rectangle{
	private Color color;
	private int thickness;
	
	public MyRectangle(int x, int y, int width, int height){
		super(x, y, width, height);
	}

	public MyRectangle() {
		// TODO Auto-generated constructor stub
	}

	public MyRectangle(MyRectangle rect) {
		super(rect.x, rect.y, rect.width, rect.height);
	}
	
	public MyRectangle(MyRectangle rect, Color c, int t){
		super(rect.x, rect.y, rect.width, rect.height);
		setColor(c);
		setThickness(t);
	}
	
	public MyRectangle(int x, int y, int width, int height, Color c, int thick) {
		super(x, y, width, height);
		setColor(c);
		setThickness(thick);
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
