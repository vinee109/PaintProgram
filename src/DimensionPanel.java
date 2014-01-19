import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DimensionPanel extends JPanel{
	JPanel titlePanel = new JPanel();
	JPanel rectPanel = new JPanel();
	JPanel circPanel = new JPanel();
	
	public DimensionPanel(){
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initComponents();
	}
	
	public void initComponents(){
		JLabel title = new JLabel("Dimensions");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		titlePanel.add(title);
		titlePanel.setPreferredSize(new Dimension(125,25));
		setRectPanel();
		setCircPanel();
		add(titlePanel);
		//add(rectPanel);
		add(circPanel);
	}
	
	public void setRectPanel(){
		rectPanel.setLayout(new GridLayout(2, 1));
		JPanel widthPanel = new JPanel();
		widthPanel.setLayout(new BoxLayout(widthPanel, BoxLayout.X_AXIS));
		JLabel widthLabel = new JLabel("Width: ");
		JTextField widthText = new JTextField("0");
		widthText.setColumns(2);
		widthPanel.add(widthLabel);
		widthPanel.add(widthText);
		widthPanel.add(new JLabel("cm"));
		
		JPanel heightPanel = new JPanel();
		heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.X_AXIS));
		JLabel heightLabel = new JLabel("Height: ");
		JTextField heightText = new JTextField("0");
		heightText.setColumns(2);
		heightPanel.add(heightLabel);
		heightPanel.add(heightText);
		heightPanel.add(new JLabel("cm"));
		
		rectPanel.add(widthPanel);
		rectPanel.add(heightPanel);
	}
	
	public void setCircPanel(){
		circPanel.setLayout(new GridLayout(2,1));
		JPanel radiusPanel = new JPanel();
		//radiusPanel.setLayout(new BoxLayout(radiusPanel, BoxLayout.X_AXIS));
		JLabel radiusLabel = new JLabel("Radius:");
		JTextField radiusText = new JTextField("0");
		radiusText.setColumns(2);
		radiusPanel.add(radiusLabel);
		radiusPanel.add(radiusText);
		radiusPanel.add(new JLabel("cm"));
		
		JPanel centerPanel = new JPanel();
		//centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		JLabel centerLabel = new JLabel("Center:");
		JTextField xText = new JTextField("0");
		xText.setColumns(1);
		JTextField yText = new JTextField("0");
		yText.setColumns(1);
		centerPanel.add(centerLabel);
		centerPanel.add(xText);
		centerPanel.add(new JLabel(","));
		centerPanel.add(yText);
		
		circPanel.add(radiusPanel);
		circPanel.add(centerPanel);
	}
}
