import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;


public class PadDraw extends JComponent {
	Image image;	//image drawn on
	Graphics2D graphics2D;		//does the drawing
	int currentX, currentY, oldX, oldY;		// mouse coordinates
	private int thickness;

	private int option = -1;
	public final static int LINE = 0;
	public final static int RECT = 1;
	public final static int STRAIGHT_LINE = 2;
	public final static int CIRC = 3;
	public final static int ARC = 4;
	public final static int MOVE = 5;
	
	//Mouse listeners for each shape;
	MouseInputAdapter lineAdapter;
	MouseInputAdapter rectAdapter;
	MouseInputAdapter straightLineAdapter;
	MouseInputAdapter circAdapter;
	MouseInputAdapter arcAdapter;
	MouseInputAdapter moveAdapter;
	MouseInputAdapter current = null;
	
	//for drawing rectangles
	Rectangle currentRect = null;
    Rectangle rectToDraw = null;
    Rectangle previousRectDrawn = new Rectangle();
    
    //for drawing straight lines
    Line2D currentLine = null;
    Line2D lineToDraw = null;
    Line2D previousLineDrawn = new Line2D.Double();
    
    //for drawing circles
    Ellipse2D currentCirc = null;
    Ellipse2D circToDraw = null;
    Ellipse2D previousCircDrawn = new Ellipse2D.Double();
    
    //for drawing arcs
    Arc2D currentArc = null;
    Arc2D arcToDraw = null;
    Arc2D previousArcDrawn = new Arc2D.Double();
    
    private Shape shapeSelected;
    
    private Color current_color;
    private ArrayList<Shape> shapesDrawn;
	
	public PadDraw(){
		current_color = Color.BLACK;
		setDoubleBuffered(false);
		setupAdapters();
		addListeners();
		shapesDrawn = new ArrayList<Shape>();
	}
	
	public void addListeners(){
		removeMouseMotionListener(current);
		removeMouseListener(current);
		switch(option){
			case LINE: addMouseMotionListener(lineAdapter);
				addMouseListener(lineAdapter);
				current = lineAdapter;
				break;
			case RECT: addMouseMotionListener(rectAdapter);
				addMouseListener(rectAdapter);
				current = rectAdapter;
				break;
			case STRAIGHT_LINE: addMouseMotionListener(straightLineAdapter);
				addMouseListener(straightLineAdapter);
				current = straightLineAdapter;
				break;
			case CIRC: addMouseMotionListener(circAdapter);
				addMouseListener(circAdapter);
				current = circAdapter;
				break;
			case ARC: addMouseMotionListener(arcAdapter);
				addMouseListener(arcAdapter);
				current = arcAdapter;
				break;
			case MOVE: addMouseMotionListener(moveAdapter);
				addMouseListener(moveAdapter);
				current = moveAdapter;
				break;
			
		}
	}
	
	public Image exportImage(){
		return image;
	}
	
	public void setOption(int value){
		System.out.println("Set option to " + value);
		option = value;
		addListeners();
	}
	
	public void drawStoredShapes(){
		for (Shape shape : shapesDrawn){
			graphics2D.draw(shape);
		}
	}
	
	public void setupAdapters(){
		lineAdapter = new LineListener();
		rectAdapter = new RectListener();
		straightLineAdapter = new StraightLineListener();
		circAdapter = new CircListener();
		arcAdapter = new ArcListener();
		moveAdapter = new MoveListener();
	}
	
	// method to draw a rectangle using two points on the diagnoal
	public void drawRect(int p1x, int p1y, int p2x, int p2y){
		System.out.println("(p1x, p1y) = " + "(" + p1x + ", " + p1y + ")");
		System.out.println("(p2x, p2y) = " + "(" + p2y + ", " + p2y + ")");
		if (p1x > p2x || p1y > p2y ){
			graphics2D.draw(new Rectangle(p2x, p2y, 
					Math.abs(p1x - p2x), 
					Math.abs(p1y - p2y)));
			repaint();
		}
		else{
			graphics2D.draw(new Rectangle(p1x, p1y, 
					Math.abs(p1x - p2x), 
					Math.abs(p1y - p2y)));
			repaint();
		}
	}
	//this is the painting bit
	//if it has nothing on it then
	//it creates an image the size of the window
	//sets the value of Graphics as the image
	//sets the rendering
	//runs the clear() method
	//then it draws the image
	public void paintComponent(Graphics g2){
		Graphics2D g = (Graphics2D)g2;
		g.setColor(current_color);
		g.setStroke(new BasicStroke(thickness));
		
		if(image == null){
			image = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D)image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			clear();
		}

		g.drawImage(image, 0, 0, null);
		
		if (option == RECT)
			if(currentRect!= null){
				g.drawRect(rectToDraw.x, rectToDraw.y, 
	                    rectToDraw.width - 1, rectToDraw.height - 1);
			}
		if (option == STRAIGHT_LINE)
			if(currentLine != null){
				g.drawLine((int)lineToDraw.getX1(), (int)lineToDraw.getY1(), 
						(int)lineToDraw.getX2(), (int)lineToDraw.getY2());
			}
		if (option == CIRC)
			if (currentCirc != null){
				g.drawOval((int)circToDraw.getX(), (int)circToDraw.getY(), 
						(int)circToDraw.getWidth(), (int)circToDraw.getHeight());
				/*
				System.out.println("Paint");
				System.out.println("x = " + circToDraw.getX());
				System.out.println("y = " + circToDraw.getY());
				System.out.println("height = " + circToDraw.getHeight());
				System.out.println("width = " + circToDraw.getWidth());
				*/
			}
		if( option == ARC){
			if ( currentArc != null){
				g.drawArc((int)arcToDraw.getX(),(int) arcToDraw.getY(), (int)arcToDraw.getWidth(), 
						(int)arcToDraw.getHeight(), (int)arcToDraw.getAngleStart(), (int)arcToDraw.getAngleExtent());
			}
		}
		
		if(option == MOVE){
			if (shapeSelected != null){
				//System.out.println(shapesSaved.size());
				for (int i = 0; i < shapesDrawn.size(); i++){
					Shape shape = shapesDrawn.get(i);
					g.draw(shape);
				}
			}
		}
	}

	//this is the clear
	//it sets the colors as white
	//then it fills the window with white
	//thin it sets the color back to black
	public void clear(){
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(current_color);
		repaint();
	}

	public void changeColor(Color theColor){
		graphics2D.setPaint(theColor);
		current_color = theColor;
		repaint();
	}
	
	public Color getColor(){
		return current_color;
	}
	
	public void setThickness(int value){
		thickness = value;
		graphics2D.setStroke(new BasicStroke(thickness));
	}
	
	public void clearAll(){
		clear();
		shapesDrawn = new ArrayList<Shape>();
	}
	
	// *******************Rectangle*************************************
	public void updateDrawableRect(int compWidth, int compHeight){
		int x = currentRect.x;
        int y = currentRect.y;
        int width = currentRect.width;
        int height = currentRect.height;
 
        //Make the width and height positive, if necessary.
        if (width < 0) {
            width = 0 - width;
            x = x - width + 1; 
            if (x < 0) {
                width += x; 
                x = 0;
            }
        }
        if (height < 0) {
            height = 0 - height;
            y = y - height + 1; 
            if (y < 0) {
                height += y; 
                y = 0;
            }
        }
 
        //The rectangle shouldn't extend past the drawing area.
        if ((x + width) > compWidth) {
            width = compWidth - x;
        }
        if ((y + height) > compHeight) {
            height = compHeight - y;
        }
       
        //Update rectToDraw after saving old value.
        if (rectToDraw != null) {
            previousRectDrawn.setBounds(
                        rectToDraw.x, rectToDraw.y, 
                        rectToDraw.width, rectToDraw.height);
            rectToDraw.setBounds(x, y, width, height);
        } else {
            rectToDraw = new Rectangle(x, y, width, height);
        }
    }
	
	private class RectListener extends MouseInputAdapter{
		public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            currentRect = new Rectangle(x, y, 0, 0);
            updateDrawableRect(getWidth(), getHeight());
            repaint();
        }
 
        public void mouseDragged(MouseEvent e) {
            updateSize(e);
            
        }
 
        public void mouseReleased(MouseEvent e) {
            updateSize(e);
            if(currentRect!= null){
    			graphics2D.drawRect(rectToDraw.x, rectToDraw.y, 
                        rectToDraw.width - 1, rectToDraw.height - 1);
    			shapesDrawn.add(new Rectangle(rectToDraw));
    		}
            mousePressed(e);
        }
 
        /* 
         * Update the size of the current rectangle
         * and call repaint.  Because currentRect
         * always has the same origin, translate it
         * if the width or height is negative.
         * 
         * For efficiency (though
         * that isn't an issue for this program),
         * specify the painting region using arguments
         * to the repaint() call.
         * 
         */
        void updateSize(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            currentRect.setSize(x - currentRect.x,
                                y - currentRect.y);
            updateDrawableRect(getWidth(), getHeight());
            Rectangle totalRepaint = rectToDraw.union(previousRectDrawn);
            repaint(totalRepaint.x - thickness, totalRepaint.y - thickness,
                    totalRepaint.width + 2*thickness, totalRepaint.height + 2*thickness);
        }
	}
	// ********************* Line ******************************************
	private class LineListener extends MouseInputAdapter{
		public void mousePressed(MouseEvent e){
			oldX = e.getX();
			oldY = e.getY();
		}
		
		public void mouseDragged(MouseEvent e){
			currentX = e.getX();
			currentY = e.getY();
	
			graphics2D.drawLine(oldX, oldY, currentX, currentY);
			repaint();
	
			oldX = currentX;
			oldY = currentY;
		}
		
		public void mouseReleased(MouseEvent e){
			currentX = e.getX();
			currentY = e.getY();
		}
	}
	// ********************** StraightLine **********************************
	public void updateDrawableStraightLine(int compWidth, int compHeight){
		Point2D p1 = currentLine.getP1();
		Point2D p2 = currentLine.getP2();
		double x1 = p1.getX();
		double x2 = p2.getX();
		double y1 = p1.getY();
		double y2 = p2.getY();
		
		//the line should not exceed the bounds of the window
		if (x1 > compWidth)
			x1 = compWidth;
		if (x2 > compWidth)
			x2 = compWidth;
		if (y1 > compHeight)
			y1 = compHeight;
		if (y2 > compHeight)
			y2 = compHeight;
		
		if (x1 < 0)
			x1 = 0;
		if (x2 < 0)
			x2 = 0;
		if (y1 < 0)
			y1 = 0;
		if (y2 < 0)
			y2 = 0;
		currentLine.setLine(x1, y1, x2, y2);
		//update lineToDraw after saving the old value
		
		if (lineToDraw != null){
			previousLineDrawn.setLine(lineToDraw);
			lineToDraw.setLine(x1, y1, x2 , y2);
		}else
			lineToDraw = new Line2D.Double(x1, y1, x2, y2);
	}
	
	private class StraightLineListener extends MouseInputAdapter{
		public void mousePressed(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			currentLine = new Line2D.Double(x,y,x,y);
			updateDrawableStraightLine(getWidth(), getHeight());
            repaint();
		}
		
		public void mouseDragged(MouseEvent e){
			updateSize(e);
		}
		
		public void mouseReleased(MouseEvent e){
			updateSize(e);
			if (currentLine != null){
				graphics2D.drawLine((int)lineToDraw.getX1(), (int)lineToDraw.getY1(),
						(int)lineToDraw.getX2(), (int)lineToDraw.getY2());
				shapesDrawn.add(new Line2D.Double((int)lineToDraw.getX1(), (int)lineToDraw.getY1(),
						(int)lineToDraw.getX2(), (int)lineToDraw.getY2()));
			}
			mousePressed(e);
		}
		
		void updateSize(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            currentLine.setLine(currentLine.getP1(), new Point2D.Double(x, y));
            updateDrawableStraightLine(getWidth(), getHeight());
            Rectangle totalRepaint = previousLineDrawn.getBounds().union(lineToDraw.getBounds());
            repaint(totalRepaint.x - thickness, totalRepaint.y - thickness,
                    totalRepaint.width*2 + 2*thickness, totalRepaint.height*2 + 2*thickness);
        }
	}
	
	// ***************************** Circle *******************************************
	public void updateDrawableCirc(int compWidth, int compHeight){
		int center_x = (int)currentCirc.getCenterX();
        int center_y = (int)currentCirc.getCenterY();
        int x = (int)currentCirc.getX();
        int y = (int)currentCirc.getY();
        int width = (int)currentCirc.getWidth();
        int height = (int)currentCirc.getHeight();
        /*
        System.out.println("CenterX = " + center_x);
        System.out.println("CenterY = " + center_y);
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("width = " + width);
        System.out.println("height = " + height);
        */
        
        //Make the width and height positive, if necessary.
        if (width < 0) {
            width = 0 - width;
            x = x - width + 1; 
            if (x < 0) {
                width += x; 
                x = 0;
            }
        }
        if (height < 0) {
            height = 0 - height;
            y = y - height + 1; 
            if (y < 0) {
                height += y; 
                y = 0;
            }
        }
 
        //The rectangle shouldn't extend past the drawing area.
        if ((x + width) > compWidth) {
            width = compWidth - x;
            height = width;
        }
        if ((y + height) > compHeight) {
            height = compHeight - y;
            width = height;
        }
       
        
        //Update rectToDraw after saving old value.
        if (circToDraw != null) {
            previousCircDrawn.setFrame(
                        circToDraw.getX(), circToDraw.getY(), 
                        circToDraw.getWidth(), circToDraw.getHeight());
            circToDraw.setFrame(x, y, width, height);
        } else {
            circToDraw = new Ellipse2D.Double(x, y, width, height);
        }
	}
	
	private class CircListener extends MouseInputAdapter{
		public void mousePressed(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			System.out.println("(" + x + ", " + y + ")");
			currentCirc = new Ellipse2D.Double(x, y, 0, 0);
			
			/*
			Ellipse2D.Double circ = new Ellipse2D.Double(x, y, 50, 50);
			System.out.println("(x,y) = ( " + x + ", " + y + " )");
			System.out.println("Center = ( " + circ.getCenterX() + ", " + circ.getCenterY() + " )");
			graphics2D.draw(circ);
			*/
			updateDrawableCirc(getWidth(), getHeight());
			repaint();
		}
		
		public void mouseDragged(MouseEvent e){
			updateSize(e);
		}
		
		public void mouseReleased(MouseEvent e){
			updateSize(e);
			/*
			int center_x = (int)currentCirc.getCenterX();
	        int center_y = (int)currentCirc.getCenterY();
	        int x = (int)currentCirc.getX();
	        int y = (int)currentCirc.getY();
	        int width = (int)currentCirc.getWidth();
	        int height = (int)currentCirc.getHeight();
	        
	        System.out.println("CenterX = " + center_x);
	        System.out.println("CenterY = " + center_y);
	        System.out.println("x = " + x);
	        System.out.println("y = " + y);
	        System.out.println("width = " + width);
	        System.out.println("height = " + height);
	        */
			if(currentCirc!= null){
				//graphics2D.draw(currentCirc);
    			graphics2D.draw(new Ellipse2D.Double(circToDraw.getX(), circToDraw.getY(), 
                        circToDraw.getWidth(), circToDraw.getHeight()));
    			shapesDrawn.add(new Ellipse2D.Double(circToDraw.getX(), circToDraw.getY(), 
                        circToDraw.getWidth(), circToDraw.getHeight()));
    			repaint();
    			
    		}
			mousePressed(e);
			//System.out.println(shapesDrawn);
		}
		
		public void updateSize(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			double center_x = currentCirc.getCenterX();
			double center_y = currentCirc.getCenterY();
			//System.out.println("(" + center_x + ", " + center_y + ")");
			double radius = Math.sqrt(Math.pow(center_x - x, 2) + Math.pow(center_y - y, 2));
			//System.out.println(radius);
			currentCirc.setFrame(center_x - radius, center_y - radius, radius*2, radius*2);
			updateDrawableCirc(getWidth(), getHeight());
			Rectangle totalRepaint = previousCircDrawn.getBounds().union(circToDraw.getBounds());
			repaint(totalRepaint.x - thickness, totalRepaint.y - thickness, totalRepaint.width + 2*thickness + 1, totalRepaint.height + 2*thickness + 1);
			//graphics2D.draw(totalRepaint);
		}
	}
	
	//******************************* Arc ********************************************************
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
		
		System.out.println("s = " + s);
		System.out.println("mid = " + mid);
		System.out.println("e = " + e);
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
	
	public void updateDrawableArc(int compWidth, int compHeight){
		double x = currentArc.getX();
        double y = currentArc.getY();
        double width = currentArc.getWidth();
        double height = currentArc.getHeight();
        double startAngle = currentArc.getAngleStart();
        double extentAngle = currentArc.getAngleExtent();
        int type = currentArc.getArcType();
 
        //Make the width and height positive, if necessary.
        if (width < 0) {
            width = 0 - width;
            x = x - width + 1; 
            if (x < 0) {
                width += x; 
                x = 0;
            }
        }
        if (height < 0) {
            height = 0 - height;
            y = y - height + 1; 
            if (y < 0) {
                height += y; 
                y = 0;
            }
        }
 
        //The rectangle shouldn't extend past the drawing area.
        if ((x + width) > compWidth) {
            width = compWidth - x;
        }
        if ((y + height) > compHeight) {
            height = compHeight - y;
        }
       
        //Update rectToDraw after saving old value.
        if (arcToDraw != null) {
            previousArcDrawn.setArc(arcToDraw.getX(), arcToDraw.getY(), arcToDraw.getWidth(),
                        arcToDraw.getHeight(), arcToDraw.getAngleStart(), arcToDraw.getAngleExtent(), 
                        arcToDraw.getArcType());
            arcToDraw.setArc(x, y, width, height, startAngle, extentAngle, type);
        } else {
            arcToDraw = new Arc2D.Double(x, y, width, height, startAngle, extentAngle, type);
        }
	}
	
	private class ArcListener extends MouseInputAdapter{
		private int initialX;
		private int initialY;
		
		public void mousePressed(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			initialX = x;
			initialY = y;
			System.out.println("x = " + x);
			System.out.println("y = " + y);
			currentArc = new Arc2D.Double(x, y, 0, 0, 0, 0, Arc2D.OPEN);
			System.out.println("arc x = " + currentArc.getX());
			System.out.println("arc y = " + currentArc.getY() + "\n" );
			updateDrawableArc(getWidth(), getHeight());
			repaint();
		}
		
		public void mouseDragged(MouseEvent e){
			updateSize(e);
		}
		
		public void mouseReleased(MouseEvent e){
			updateSize(e);
			if(currentArc!= null){
				Arc2D arc = new Arc2D.Double(arcToDraw.getX(),
						arcToDraw.getY(),
						arcToDraw.getWidth(),
						arcToDraw.getHeight(),
						arcToDraw.getAngleStart(),
						arcToDraw.getAngleExtent(),
						arcToDraw.getArcType());
    			graphics2D.draw(arc);
    			repaint();
    			shapesDrawn.add(arc);
    		}
            mousePressed(e);
		}
		
		public void updateSize(MouseEvent e){
			int x = e.getX();
            int y = e.getY();
            /*
            System.out.println("end x = " + x);
            System.out.println("end y = " + y);
            System.out.println("initialx = " + initialX);
            System.out.println("initialy = " + initialY);
            System.out.println(currentArc.getX());
            System.out.println(currentArc.getY());
            */
            
            Arc2D dim = makeArc(new Point(initialX, initialY), 
            		new Point( (x+initialX)/2, y), new Point(x, initialY));
            /*
            System.out.println("width = " + dim.getWidth());
            System.out.println("height = " + dim.getHeight());
            System.out.println("start x = " + dim.getX());
            System.out.println(" stary y = " + dim.getY());
            System.out.println();
            */
            currentArc.setArc(dim);
            updateDrawableArc(getWidth(), getHeight());
            Rectangle totalRepaint = arcToDraw.getBounds().union(previousArcDrawn.getBounds());
            repaint(totalRepaint.x - thickness, totalRepaint.y - thickness,
                    totalRepaint.width + 2*thickness, totalRepaint.height + 2*thickness);
		}
	}
	//******************************* Move ********************************************
	private class MoveListener extends MouseInputAdapter{
		int preX;
		int preY;
		
		public void mousePressed(MouseEvent e){
			System.out.println("mouse pressed");
			int x = e.getX();
			int y = e.getY();
			//System.out.println(shapesDrawn);
			
			//checks if a shape is selected
			int i = 0;
			while ( i < shapesDrawn.size() && !shapesDrawn.get(i).contains(x, y)){
				if (shapesDrawn.get(i).contains(x, y))
					shapeSelected = shapesDrawn.get(i);
				i++;
			}
			
			shapeSelected = shapesDrawn.get(i);
			if (shapeSelected != null){
				preX = shapeSelected.getBounds().x - x;
				preY = shapeSelected.getBounds().y - y;
				updateLocation(e);
			}
			System.out.println("selected shape: " + shapeSelected);
		}
		
		public void mouseDragged(MouseEvent e){
			System.out.println("mouse dragged");
			int x = e.getX();
			int y = e.getY();
			if (x < getWidth() && y < getHeight())
				updateLocation(e);
			clear();
		}
		
		public void mouseReleased(MouseEvent e){
			System.out.println("mouse released");
			if ( shapeSelected.contains(e.getPoint()))
				updateLocation(e);
			graphics2D.draw(shapeSelected);
			
			System.out.println("size of shapesDrawn = " + shapesDrawn.size() );
			for ( int i = 0; i < shapesDrawn.size(); i++ ){
				System.out.println(shapesDrawn.get(i));
				graphics2D.draw(shapesDrawn.get(i));
			}
			
			repaint();
		}
		
		public void updateLocation(MouseEvent e){
			if (shapeSelected instanceof Rectangle){
				((Rectangle) shapeSelected).setLocation(preX + e.getX(), preY + e.getY());
			}
			if (shapeSelected instanceof Ellipse2D){
				((Ellipse2D.Double) shapeSelected).x = preX + e.getX();
				((Ellipse2D.Double) shapeSelected).y = preY + e.getY();
			}
			if (shapeSelected instanceof Arc2D){
				((Arc2D.Double) shapeSelected).x = preX + e.getX();
				((Arc2D.Double) shapeSelected).y = preY + e.getY();
			}
			if (shapeSelected instanceof Line2D.Double){
				//((Line2D.Double) shapeSelected).
			}
		}
	}
}
