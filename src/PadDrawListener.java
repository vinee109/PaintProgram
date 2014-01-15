import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.MouseInputAdapter;


public class PadDrawListener extends MouseInputAdapter {
	Graphics2D graphics;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	ArrayList<Connection> connectionPts = new ArrayList<Connection>();
	boolean snapped;
	boolean snapEnabled = false;
	
	public PadDrawListener(){
		snapped = false;
	}
	
	public void changeSnapEnabled(boolean s){
		snapEnabled = s;
	}
	
	public void getConnects(){
		if (shapes.size() > 0)
			for ( Shape s : shapes){
				if ( s instanceof MyRectangle)
					for(Connection c: ((MyRectangle)s).getConnections() ){
						//System.out.println(c);
						connectionPts.add(c);
					}
				if ( s instanceof Line)
					for(Connection c: ((Line)s).getConnections() ){
						//System.out.println(c);
						connectionPts.add(c);
					}
				if ( s instanceof Circle)
					for(Connection c: ((Circle)s).getConnections() ){
						//System.out.println(c);
						connectionPts.add(c);
					}
			}
	}
	
	public void mouseMoved(MouseEvent e){
		System.out.println(snapEnabled);
    	if ( snapEnabled){
    		getConnects();
    		int x = e.getX();
    		int y = e.getY();
    		Robot robot;
			
    		Point screenLoc = MouseInfo.getPointerInfo().getLocation();
    		int xOff = screenLoc.x - x;
    		int yOff = screenLoc.y - y;
    		//System.out.println("(" + x + ", " + y + ")");
    		//System.out.println(connectionPts);
    		for (int i = 0; i < connectionPts.size(); i++){
    			Connection c = connectionPts.get(i);
    			//System.out.println(snapped);
    			if ( Math.abs(x - c.getX()) < 5 && Math.abs(y - c.getY()) < 5 && !snapped){
    				//System.out.println(c);
    				//drawAllWhiteBut(i);
    				snapped = true;
    				try {
    					robot = new Robot();
    					robot.mouseMove(c.getX() + xOff, c.getY() + yOff);
    				} catch (AWTException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    			}
    			else if (Math.abs(x - c.getX()) >= 10 || Math.abs(y - c.getY()) >= 10 ){
     				snapped = false;
     			}
    			
    		}
    	}
	}
	/*
	public void mouseDragged(MouseEvent e){
		 Robot robot;
		try {
			robot = new Robot();
			robot.mouseMove(300, 550);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	*/
	
	public Point snap(int x, int y){
		getConnects();
 		//System.out.println("(" + x + ", " + y + ")");
 		//System.out.println(connectionPts);
 		for (int i = 0; i < connectionPts.size(); i++){
 			Connection c = connectionPts.get(i);
 			if ( Math.abs(x - c.getX()) < 5 && Math.abs(y - c.getY()) < 5 ){
 				//System.out.println(c);
 				//drawAllWhiteBut(i);
 				return new Point(c.getX(), c.getY());
 			}
 			
 		}
 		return new Point(x, y);
	}
	
	 public void mouseDragged(MouseEvent e){
 		getConnects();
 		int x = e.getX();
 		int y = e.getY();
 		Robot robot;
			
 		Point screenLoc = MouseInfo.getPointerInfo().getLocation();
 		int xOff = screenLoc.x - x;
 		int yOff = screenLoc.y - y;
 		//System.out.println("(" + x + ", " + y + ")");
 		//System.out.println(connectionPts);
 		for (int i = 0; i < connectionPts.size(); i++){
 			Connection c = connectionPts.get(i);
 			if ( Math.abs(x - c.getX()) < 5 && Math.abs(y - c.getY()) < 5 && !snapped){
 				//System.out.println(c);
 				//drawAllWhiteBut(i);
 				snapped = true;
 				try {
 					robot = new Robot();
 					robot.mouseMove(c.getX() + xOff, c.getY() + yOff);
 				} catch (AWTException e1) {
 					// TODO Auto-generated catch block
 					e1.printStackTrace();
 				}
 			}
 			else if (Math.abs(x - c.getX()) >= 10 || Math.abs(y - c.getY()) >= 10 ){
 				snapped = false;
 			}
 			
 		}
 	}
	 
	public void setShapes(ArrayList<Shape> s){
		shapes = s;
	}
	
	public void clearConnectPts(){
		connectionPts = new ArrayList<Connection>();
	}
	
	
}
