import java.awt.geom.Ellipse2D;


public class Circle2D extends Ellipse2D.Double{
	private int center_x;
	private int center_y;
	private int width;
	private int height;
	
	public Circle2D(int px, int py, int w, int h){
		super(px - w/2, py - h/2, w, h);
		center_x = px;
		center_y = py;
		width = w;
		height = h;
	}

}
