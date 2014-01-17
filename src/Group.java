import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;


public class Group extends Rectangle implements BasicShape{
	
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
	
	public void draw(Graphics2D g){
		g.draw(this);
		for( int i = 0; i < containedShapes.size(); i++){
			BasicShape shape = (BasicShape)containedShapes.get(i);
			g.setStroke(new BasicStroke(shape.getThickness()));
			g.setColor(shape.getColor());
			g.draw(shape);
		}
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getThickness() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
