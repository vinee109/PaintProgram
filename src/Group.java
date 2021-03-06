import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Arc2D;
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
			g.setColor(shape.getColor());
			g.setStroke(new BasicStroke(shape.getThickness()));
			g.draw(shape);
		}
	}
	// FIX GROUPING ISSUE
	public void setLocation(int x, int y){
		for (Shape shape: containedShapes){
			int offsetX = shape.getBounds().x - this.x;
			int offsetY = shape.getBounds().y - this.y;
			
			if (shape instanceof Rectangle){
				((Rectangle) shape).setLocation(x + offsetX, y + offsetY);
			}
			if (shape instanceof Circle){
				((Circle) shape).x = x + offsetX;
				((Circle) shape).y = y + offsetY;
				
			}
			if (shape instanceof Arc){
				((Arc) shape).x = x + offsetX;
				((Arc) shape).y = y + offsetY;
			}
			if (shape instanceof Line){
				int offx1, offy1, offx2, offy2;
				offx1 = (int) (shape.getBounds().x - ((Line)shape).getX1());
				offy1 = (int) (shape.getBounds().y - ((Line)shape).getY1());
				offx2 = (int) (shape.getBounds().x - ((Line)shape).getX2());
				offy2 = (int) (shape.getBounds().y - ((Line)shape).getY2());
				((Line)shape).setLine(x - offx1, y - offy1, x - offx2, y - offy2);
				((Line)shape).initPoints();
			}
		}
		super.setLocation(x, y);
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public int getThickness() {
		return thickness;
	}
	
	public ArrayList<Shape> getContainedShapes(){
		return containedShapes;
	}

	@Override
	public ResizeRect[] getPoints() {
		ResizeRect[] empty = new ResizeRect[0];
		return empty;
	}

	@Override
	public void changeResizeRect(int pos, int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
}
