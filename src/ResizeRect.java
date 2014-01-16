import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;


public class ResizeRect extends MyRectangle{
	public final static int SIZE = 8;
	private int centerX;
	private int centerY;
	private Shape shape;
	
	
	public ResizeRect(int x, int y, Shape s){
		super(x - SIZE/2, y-SIZE/2, SIZE, SIZE);
		centerX = x;
		centerY = y;
		shape = s;
	}
	
	public ResizeRect(Point2D p, Shape s){
		super((int)p.getX() - SIZE/2, (int)p.getY() - SIZE/2, SIZE, SIZE);
		centerX = (int)p.getX();
		centerY = (int)p.getY();
		shape = s;
	}
	
	public boolean isInside(int x, int y){
		//System.out.println("center: " + "(" + centerX + ", " + centerY + ")");
		boolean xCond = x > centerX - 8 && x < centerX + 8;
		boolean yCond = y > centerY - 8 && y < centerY + 8;
		return xCond && yCond;
	}
	
	public Shape getShape(){
		return shape;
	}
	
	
}
