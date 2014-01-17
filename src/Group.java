import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;


public class Group extends Rectangle implements BasicShape{
	
	private ArrayList<Shape> containedShapes;
	private final Color color = Color.red;
	private final int thickness = 1;
	private final int PAD = 2;
	
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
			this.setRect(bounds.x-PAD, bounds.y-PAD, bounds.width+2*PAD, bounds.height+2*PAD);
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
		return color;
	}

	@Override
	public int getThickness() {
		return thickness;
	}
	
}
