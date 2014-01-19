import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;


public class Arc extends Arc2D.Double implements BasicShape{
	private Color color;
	private int thickness;
	private Point initial;
	private int myWidth;
	private int myHeight;
	private ResizeRect [] points;
	
	public Arc(){
	}

	public Arc(double x, double y, double w, double h, double start, double extent, int open) {
		super(x, y, w, h, start, extent, open);
	}
	
	public Arc(double x, double y, double w, double h, double start, double extent, int open, Color c, int t) {
		super(x, y, w, h, start, extent, open);
		color = c;
		thickness = t;
	}
	
	public Rectangle getBounds(){
		try{
			//return new Rectangle(initial.x, initial.y, myWidth, myHeight);
			/*
			return new Rectangle(points[0].getCenter().x,
					points[0].getCenter().y,
					(int)Math.abs(points[0].getCenterX() - points[2].getCenterX()),
					(int)Math.abs(points[0].getCenterY() - points[2].getCenterY())
					);
					*/
			Rectangle rect01 = new Rectangle();
			Rectangle rect12 = new Rectangle();
			Rectangle rect02 = new Rectangle();
			
			rect01.setFrameFromDiagonal(points[0].getCenter(), points[1].getCenter());
			rect12.setFrameFromDiagonal(points[1].getCenter(), points[2].getCenter());
			rect02.setFrameFromDiagonal(points[0].getCenter(), points[2].getCenter());
			return rect01.union(rect12).union(rect02);
			/*
			if (points[0].getCenter().y == points[2].getCenter().y)
				return new Rectangle(initial.x, initial.y, myWidth, myHeight);
			
			Rectangle rect = new Rectangle();
			rect.setFrameFromDiagonal(points[0].getCenter(), points[2].getCenter());
			return rect;
			*/
		}
		catch(Exception e){
			return super.getBounds();
		}
	}
	
	public void setInitial(Point p){
		initial = p;
	}
	
	public void setWidth(int w){
		myWidth = w;
	}
	
	public void setHeight(int h){
		myHeight = h;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getThickness(){
		return thickness;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public void setThickness(int t){
		thickness = t;
	}

	public void setPoints(ResizeRect [] p){
		points = p;
	}
	
	public ResizeRect[] getPoints() {
		return points;
	}

	public void changeResizeRect(int pos, int x, int y) {
		points[pos].setRect(x, y);
		updateArc();
	}
	
	public void updateArc(){
		Arc2D newArc = makeArc(points[0].getCenter(), points[1].getCenter(), points[2].getCenter());
		this.setArc(newArc);
	}
	
	public Point getCircleCenter(Point a, Point b, Point c){
		double ax = a.getX();
		double ay = a.getY();
		double bx = b.getX();
		double by = b.getY();
		double cx = c.getX();
		double cy = c.getY();

		double A = bx - ax;
		double B = by - ay;
		double C = cx - ax;
		double D = cy - ay;

		double E = A * (ax + bx) + B * (ay + by);
		double F = C * (ax + cx) + D * (ay + cy);

		double G = 2 * (A * (cy - by) - B * (cx - bx));
		/*
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println("c = " + c);
		*/
		if (G == 0.0)
			return null; // a, b, c must be collinear

		double px = (D * E - B * F) / G;
		double py = (A * F - C * E) / G;
		return new Point((int)px, (int)py);
	}
	
	public double makeAnglePos(double angle){
		if ( angle < 0)
			return 360 + angle;
		else
			return angle;
	}
	
	public double getNearestAnglePhase(double limit, double source, int dir){
		double value = source;
		if (dir > 0){
			while (value < limit)
				value += 360.0;
		}
		else if (dir < 0){
			while ( value > limit )
				value -= 360.0;
		}
		return value;
	}
	
	public Arc2D makeArc(Point s, Point mid, Point e){
		/*
		System.out.println("s = " + s);
		System.out.println("mid = " + mid);
		System.out.println("e = " + e);
		*/
		Point c = getCircleCenter(s, mid, e);
		  double radius = c.distance(s);

		  double startAngle = makeAnglePos(Math.toDegrees(-Math
		      .atan2(s.y - c.y, s.x - c.x)));
		  double midAngle = makeAnglePos(Math.toDegrees(-Math.atan2(mid.y - c.y, mid.x
		      - c.x)));
		  double endAngle = makeAnglePos(Math.toDegrees(-Math.atan2(e.y - c.y, e.x - c.x)));

		  // Now compute the phase-adjusted angles begining from startAngle, moving positive and negative.
		  double midDecreasing = getNearestAnglePhase(startAngle, midAngle, -1);
		  double midIncreasing = getNearestAnglePhase(startAngle, midAngle, 1);
		  double endDecreasing = getNearestAnglePhase(midDecreasing, endAngle, -1);
		  double endIncreasing = getNearestAnglePhase(midIncreasing, endAngle, 1);

		  // Each path from start -> mid -> end is technically, but one will wrap around the entire
		  // circle, which isn't what we want. Pick the one that with the smaller angular change.
		  double extent = 0;
		  if (Math.abs(endDecreasing - startAngle) < Math.abs(endIncreasing - startAngle)) {
		    extent = endDecreasing - startAngle;
		  } else {
		    extent = endIncreasing - startAngle;
		  }

		  return new Arc2D.Double(c.x - radius, c.y - radius, radius * 2, radius * 2, startAngle, extent,
		      Arc2D.OPEN);
	}
}
