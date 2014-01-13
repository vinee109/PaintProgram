import java.awt.Point;
import java.awt.geom.Point2D;


public class ResizeRect extends MyRectangle{
	public final static int SIZE = 8;
	private int centerX;
	private int centerY;
	
	
	public ResizeRect(int x, int y){
		super(x - SIZE/2, y-SIZE/2, SIZE, SIZE);
		centerX = x;
		centerY = y;
	}
	
	public ResizeRect(Point2D p){
		super((int)p.getX() - SIZE/2, (int)p.getY() - SIZE/2, SIZE, SIZE);
		centerX = (int)p.getX();
		centerY = (int)p.getY();
	}
	
	
}
