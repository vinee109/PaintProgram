import java.awt.Shape;
import java.util.ArrayList;
import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.event.*;
import java.awt.*;


public class Render3D extends MouseAdapter{
	ArrayList<Shape> shapes;
	private PickCanvas pickCanvas;
	private Frame frame;
	private GraphicsConfiguration config;
	private Canvas3D canvas;
	private SimpleUniverse universe;
	private BranchGroup group;
	
	public Render3D(){
		
	}
	
	public Render3D(ArrayList<Shape> s, int width, int height){
		shapes = s;
		frame = new Frame("Box and Sphere");
		config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Canvas3D(config);
	    canvas.setSize(width, height);
	    universe = new SimpleUniverse(canvas);
	    group = new BranchGroup();
	}
	
	public void render(){
		for (Shape s: shapes){
			if (s instanceof MyRectangle){
			}
		}
	}
	
	public void display(){
	    // create a color cube
	    Vector3f vector = new Vector3f(-0.3f, 0.0f, 0.0f);

	    Transform3D transform = new Transform3D();

	    transform.setTranslation(vector);

	    TransformGroup transformGroup = new TransformGroup(transform);
	    Appearance appearance1 = new Appearance();

	    appearance1.setPolygonAttributes(

	       new PolygonAttributes(PolygonAttributes.POLYGON_LINE,

	       PolygonAttributes.CULL_BACK,0.0f));
	    double x = 0.1;
	    Box cube = new Box((float)x, 0.2f, 0.3f, appearance1);

	    transformGroup.addChild(cube);

	    group.addChild(transformGroup);


	    //create a sphere


	    Vector3f vector2 = new Vector3f(+0.3f, 0.0f, 0.0f);

	    Transform3D transform2 = new Transform3D();

	    transform2.setTranslation(vector2);

	    TransformGroup transformGroup2 = new TransformGroup(transform2);

	    Appearance appearance = new Appearance();

	    appearance.setPolygonAttributes(

	       new PolygonAttributes(PolygonAttributes.POLYGON_LINE,

	       PolygonAttributes.CULL_BACK,0.0f));

	    Sphere sphere = new Sphere(0.3f,appearance);

	    transformGroup2.addChild(sphere);

	    group.addChild(transformGroup2);


	    universe.getViewingPlatform().setNominalViewingTransform();

	    universe.addBranchGraph(group);

	    frame.addWindowListener(new WindowAdapter() {

	       public void windowClosing(WindowEvent winEvent) {

	          System.exit(0);

	       }

	   	 });

	    frame.add(canvas);

	    pickCanvas = new PickCanvas(canvas, group);

	    pickCanvas.setMode(PickCanvas.BOUNDS);

	    canvas.addMouseListener(this);

	    frame.pack();

	    frame.show();
	}
}
