import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;
import javax.swing.*;
 
public class SnapTo extends JPanel implements ActionListener
{
    List<Shape> shapes;    // generic declaration for j2se 1.5+
    //List shapes;         // j2se 1.4-
    BufferedImage background;
    double xInc;
    double yInc;
    int shapeType;
    final int LINES = 0;
    final int RECTS = 1;
    final int ROWS = 4;
    final int COLS = 4;
    final int PAD = 20;
 
    public SnapTo()
    {
        shapes = new ArrayList<Shape>();  // generic, j2se 1.5+
        //shapes = new ArrayList();       // j2se 1.4-
        shapeType = LINES;
    }
 
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        if(background == null)
            createBackground();
        g2.drawImage(background, 0, 0, this);
        g2.setPaint(Color.red);
        for(int j = 0; j < shapes.size(); j++)
            g2.draw((Shape)shapes.get(j));
    }
 
    public void addShape(Point p)
    {
        Point node = getClosestNode(p);
        if(node.x == -1 || node.y == -1)  // off grid
            return;
        Shape s;
        switch(shapeType)
        {
            case LINES:
                s = new Line2D.Double(node.x-xInc/2, node.y+yInc/2,
                                      node.x+xInc/2, node.y-yInc/2);
                break;
            case RECTS:
                s = new Rectangle2D.Double(node.x-xInc/2, node.y-yInc/2,
                                           xInc, yInc);
                break;
            default:
                System.out.println("unexpected shapeType " + shapeType);
                return;
        }
        shapes.add(s);
        repaint();
    }
 
    private Point getClosestNode(Point p)
    {
        Point gridCenter = new Point(-1, -1);
        if(p.x < PAD || p.y < PAD)       // off grid to the left
            return gridCenter;
        for(int row = 1; row <= ROWS; row++)
        {
            double y = PAD + row*yInc;
            if(p.y < y)
            {
                gridCenter.y = (int)(y - yInc);
                break;
            }
        }
        for(int col = 1; col <= COLS; col++)
        {
            double x = PAD + col*xInc;
            if(p.x < x)
            {
                gridCenter.x = (int)(x - xInc);
                break;
            }
        }
        return gridCenter;
    }
 
    public void clear()
    {
        shapes.clear();
        repaint();
    }
 
    private void createBackground()
    {
        int w = getWidth();
        int h = getHeight();
        background = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = background.createGraphics();
        g2.setPaint(UIManager.getColor("Panel.background"));
        g2.fillRect(0,0,w,h);
        g2.setPaint(new Color(200,220,240));
        xInc = (double)(w - 2*PAD)/COLS;
        yInc = (double)(h - 2*PAD)/ROWS;
        double x1 = PAD, y1 = PAD, x2 = w-PAD, y2 = h-PAD;
        for(int j = 0; j <= COLS; j++)
        {
            g2.draw(new Line2D.Double(x1, y1, x1, y2));
            x1 += xInc;
        }
        x1 = PAD;
        for(int j = 0; j <= COLS; j++)
        {
            g2.draw(new Line2D.Double(x1, y1, x2, y1));
            y1 += yInc;
        }
        g2.dispose();
    }
 
    public static void main(String[] args)
    {
        SnapTo snapTo = new SnapTo();
        snapTo.addComponentListener(snapTo.resizer);
        snapTo.addMouseListener(new ShapePlacer(snapTo));
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(snapTo);
        f.getContentPane().add(snapTo.getUIPanel(), "South");
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
 
    public void actionPerformed(ActionEvent e)
    {
        JRadioButton rb = (JRadioButton)e.getSource();
        String id = rb.getActionCommand();
        if(id.equals("lines"))
            shapeType = LINES;
        if(id.equals("rectangles"))
            shapeType = RECTS;
    }
 
    private JPanel getUIPanel()
    {
        String[] ids = { "lines", "rectangles" };
        ButtonGroup group = new ButtonGroup();
        JPanel panel = new JPanel();
        for(int j = 0; j < ids.length; j++)
        {
            JRadioButton rb = new JRadioButton(ids[j], j==0);
            rb.setActionCommand(ids[j]);
            rb.addActionListener(this);
            group.add(rb);
            panel.add(rb);
        }
        return panel;
    }
 
    private ComponentListener resizer = new ComponentAdapter()
    {
        public void componentResized(ComponentEvent e)
        {
            background = null;
            repaint();
        }
    };
}
 
class ShapePlacer extends MouseAdapter
{
    SnapTo snapTo;
 
    public ShapePlacer(SnapTo st)
    {
        snapTo = st;
    }
 
    public void mousePressed(MouseEvent e)
    {
        if(e.getClickCount() == 2)
            snapTo.clear();
        else
            snapTo.addShape(e.getPoint());
    }
}