import java.awt.Color;
import java.awt.Shape;


public interface BasicShape extends Shape {
	
	public abstract Color getColor();
	public abstract int getThickness();
	public abstract ResizeRect [] getPoints();
	public abstract void changeResizeRect(int pos, int x, int y);
}
