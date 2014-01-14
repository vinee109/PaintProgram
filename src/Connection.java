import java.awt.Point;


public class Connection {
	private Point pt;
	
	public Connection(int x, int y){
		pt = new Point(x, y);
	}
	
	public String toString(){
		return pt.toString();
	}
	
	public int getX(){
		return pt.x;
	}
	
	public int getY(){
		return pt.y;
	}
}
