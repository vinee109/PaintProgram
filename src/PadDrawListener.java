import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputAdapter;


public class PadDrawListener extends MouseInputAdapter {
	Graphics2D graphics;
	ArrayList<Shape> shapes;
	ArrayList<Connection> connectionPts = new ArrayList<Connection>();
	
	public PadDrawListener(Graphics2D g, ArrayList<Shape> s){
		graphics = g;
		shapes = s;
	}
	
	public void getConnects(){
		if (shapes.size() > 0)
			for ( Shape s : shapes){
				//System.out.println(s);
				//System.out.println(((MyRectangle)s).getConnections());
				if ( s instanceof MyRectangle)
					for(Connection c: ((MyRectangle)s).getConnections() ){
						//System.out.println(c);
						connectionPts.add(c);
					}
			}
	}
	
	public void mouseMoved(MouseEvent e){
		getConnects();
		int x = e.getX();
		int y = e.getY();
		System.out.println("(" + x + ", " + y + ")");
		for (Connection c: connectionPts){
			if ( Math.abs(x - c.getX()) < 20 && Math.abs(y - c.getY()) < 20 ){
				PadDraw.graphics2D.drawOval(c.getX() - 5, c.getY() - 5, 10, 10);
			}
		}
	}
	
	
}
