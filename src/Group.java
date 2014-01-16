import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;


public class Group extends Rectangle{
	
	private ArrayList<Shape> containedShapes;
	
	public Group(){
		containedShapes = new ArrayList<Shape>();
	}
	
	public Group(ArrayList<Shape> s){
		containedShapes = s;
		setDimension();
	}
	
	public void addShape(Shape s){
		containedShapes.add(s);
	}
	
	public void setDimension(){
		if (containedShapes.size() > 0){
			Rectangle bounds = containedShapes.get(0).getBounds();
			for ( int i = 1; i < containedShapes.size(); i++){
				bounds = bounds.union(containedShapes.get(i).getBounds());
			}
			this.setRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
	
}
