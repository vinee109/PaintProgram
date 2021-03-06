import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PaintWindow extends JFrame{
	private JPanel panel;
	private PadDraw drawPad;
	private JPanel bottomPanel;
	private JPanel colorPanel;
	private JSlider thicknessSlider;
	private JTextField thicknessText;
	private JColorChooser colorChooser;
	private JFileChooser fc;
	
	public final static int CIRCLE_CONST = 0;
	public final static int RECTANGLE_CONST = 1;
	public final static int LINE2D_DOUBLE_CONST = 2;
	public final static int ARC2D_DOUBLE_CONST = 3;
	
	public PaintWindow(){
		setTitle("Paint it");
		setSize(1100, 700);
		
		fc = new JFileChooser();
		panel = new JPanel();
		drawPad = new PadDraw();
		bottomPanel = new JPanel();
		colorPanel = new JPanel();
		thicknessText = new JTextField("1");
		thicknessSlider = new JSlider(1, 100);
		colorChooser = new JColorChooser();
		
		panel.setLayout(new GridLayout(2, 1));
		JPanel topPart = new JPanel(new FlowLayout());
		panel.add(topPart);
		thicknessText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					int value = Integer.parseInt(thicknessText.getText());
					thicknessSlider.setValue(value);
				}
				catch(Exception exception){
					thicknessSlider.setValue(1);
				}
			}
		});
		
		thicknessSlider.setValue(1);
		//thicknessSlider.setPaintTicks(true);
		thicknessSlider.setSnapToTicks(true);
		thicknessSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				int val = source.getValue();
				thicknessText.setText(val + "");
				drawPad.setThickness(val);
			}
		});
		
		panel.setPreferredSize(new Dimension(125, 68));
		bottomPanel.setPreferredSize(new Dimension(68, 58));

		//Creates a new container
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());

		//sets the panel to the left, padDraw in the center
		content.add(panel, BorderLayout.WEST);
		content.add(drawPad, BorderLayout.CENTER);
		content.add(bottomPanel, BorderLayout.SOUTH);
		
		bottomPanel.setLayout(new BorderLayout());
		//panel for colorChooser
		final JPanel cPanel = new JPanel();
		cPanel.setPreferredSize(new Dimension(30, 30));
		cPanel.setBackground(Color.BLACK);
		JButton colorButton = new JButton("Change Color");
		
		colorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Select Color", drawPad.getColor());
				if (color == null){
					color = drawPad.getColor();
				}
				drawPad.changeColor(color);
				cPanel.setBackground(color);
			}
		});
		colorPanel.add(cPanel);
		colorPanel.add(colorButton);
		colorPanel.setAlignmentX(LEFT_ALIGNMENT);

		//panel for thickness slider
		JPanel thicknessPanel = new JPanel();
		thicknessPanel.setLayout(new BoxLayout(thicknessPanel, BoxLayout.Y_AXIS));
		JLabel thick = new JLabel("Thickness");
		thick.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel sliderPanel = new JPanel();
		thicknessText.setColumns(2);
		sliderPanel.setLayout(new FlowLayout());
		sliderPanel.add(thicknessSlider);
		sliderPanel.add(thicknessText);
		thicknessPanel.add(thick);
		thicknessPanel.add(sliderPanel);
		
		colorChooser.setPreviewPanel(new JPanel());
		JPanel panel1 = new JPanel();
		panel1.add(colorPanel);
		panel1.add(thicknessPanel);
		bottomPanel.add(panel1, BorderLayout.WEST);
		
		addMenuBar();
		
		JButton lineButton = new JButton(new ImageIcon("icons/line.gif"));
		lineButton.setPreferredSize(new Dimension(20,20));
		lineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.LINE);
				//System.out.println(drawPad.getLocationOnScreen());
			}
		});
		topPart.add(lineButton);
		
		JButton straightLineButton = new JButton(new ImageIcon("icons/straightline.gif"));
		straightLineButton.setPreferredSize(new Dimension(20,20));
		straightLineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.STRAIGHT_LINE);
			}
		});
		topPart.add(straightLineButton);
		
		JButton rectButton = new JButton(new ImageIcon("icons/rect.gif"));
		rectButton.setPreferredSize(new Dimension(20,20));
		rectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.RECT);
			}
		});
		topPart.add(rectButton);
		
		JButton circButton = new JButton(new ImageIcon("icons/circ.gif"));
		circButton.setPreferredSize(new Dimension(20,20));
		circButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.CIRC);
			}
		});
		topPart.add(circButton);
		
		//arc button
		JButton arcButton = new JButton(new ImageIcon("icons/arc.gif"));
		arcButton.setPreferredSize(new Dimension(20,20));
		arcButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.ARC);
			}
		});
		topPart.add(arcButton);
		
		//move button
		JButton moveButton = new JButton("Move");
		moveButton.setPreferredSize(new Dimension(20, 20));
		moveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.MOVE);
			}
		});
		topPart.add(moveButton);
		
		//resize button
		JButton resizeButton = new JButton("Resize");
		resizeButton.setPreferredSize(new Dimension(20,20));
		resizeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.RESIZE);
				drawPad.drawResizeRects();
			}
		});
		topPart.add(resizeButton);
		
		JButton selectButton = new JButton(new ImageIcon("icons/selection.gif"));
		selectButton.setPreferredSize(new Dimension(20,20));
		selectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.SELECT);
			}
		});
		topPart.add(selectButton);
		
		JButton eraseButton = new JButton(new ImageIcon("icons/eraser.gif"));
		eraseButton.setPreferredSize(new Dimension(20,20));
		eraseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.ERASE);
			}
		});
		topPart.add(eraseButton);
		
		//creates the clear button
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.clearAll();
			}
		});
		topPart.add(clearButton);
		
		//panel.add(new DimensionPanel());
	}
	
	public void addMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenu = new JMenuItem("New");
		newMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.newOp();
			}
		});
		JMenuItem saveMenu = new JMenuItem("Save");
		saveMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				save();
			}
		});
		JMenuItem saveAsMenu = new JMenuItem("Save As");
		JMenuItem openMenu = new JMenuItem("Open");
		openMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				open();
			}
		});
		JMenuItem exportMenu = new JMenuItem("Export Image");
		exportMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				export();
			}
		});
		JMenuItem renderMenu = new JMenuItem("Render3D");
		renderMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Render3D render = new Render3D(drawPad.getSavedShapes(), drawPad.getWidth(), drawPad.getHeight());
				render.display();
			}
		});
		fileMenu.add(newMenu);
		fileMenu.add(openMenu);
		fileMenu.add(saveMenu);
		fileMenu.add(saveAsMenu);
		fileMenu.add(exportMenu);
		fileMenu.add(renderMenu);
		menuBar.add(fileMenu);
		
		JMenu drawOptionsMenu = new JMenu("Edit");
		
		JCheckBoxMenuItem snapItem = new JCheckBoxMenuItem("Enable Snap to Shapes");
		snapItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				drawPad.changeSnapEnabled();
			}
		});
		drawOptionsMenu.add(snapItem);
		
		JMenuItem select = new JMenuItem("Add Group");
		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.addGroup();
			}
		});
		drawOptionsMenu.add(select);
		menuBar.add(drawOptionsMenu);
		
		setJMenuBar(menuBar);
	}
	
	public void save(){
		int val = fc.showSaveDialog(new JFrame());
		if ( val == JFileChooser.APPROVE_OPTION){
			String filePath = fc.getSelectedFile().getAbsolutePath();
			convertShapesList(new File(filePath));
			
		}
	}
	
	// displays JFileChooser returns the file selected by the user
	public File open(){
		int val = fc.showOpenDialog(new JFrame());
		if( val == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			//if the file is null it displays an error dialog
			if (file == null){
				JOptionPane.showMessageDialog(new JFrame(),
					    "An error occurred, the file you selected could not be located.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
			else{
				drawPad.openPreviousFile(file);
			}
		}
		return null;
	}
	
	public void convertShapesList(File file){
		FileWriter out;
		try{
			out = new FileWriter(file);
			ArrayList<Shape> shapes = drawPad.getSavedShapes();
			for ( int i = 0; i < shapes.size(); i++ ){
				
				Shape shape = shapes.get(i);
				if ( shape instanceof Circle){
					out.write(CIRCLE_CONST + "\r\n");
					double [] args = {
						((Circle)shape).getX(),
						((Circle)shape).getY(),
						((Circle)shape).getWidth(),
						((Circle)shape).getHeight(),
					};
					writeToFile(out, args, ((Circle)shape).getColor(), ((Circle)shape).getThickness());
				}
				else if ( shape instanceof MyRectangle){
					out.write(RECTANGLE_CONST + "\r\n");
					double [] args = {
						((MyRectangle)shape).getX(),
						((MyRectangle)shape).getY(),
						((MyRectangle)shape).getWidth(),
						((MyRectangle)shape).getHeight(),
					};
					writeToFile(out, args, ((MyRectangle)shape).getColor(), ((MyRectangle)shape).getThickness());
				}
				else if ( shape instanceof Line2D.Double){
					out.write(LINE2D_DOUBLE_CONST + "\r\n");
					double [] args = {
						((Line2D.Double)shape).getX1(),
						((Line2D.Double)shape).getY1(),
						((Line2D.Double)shape).getX2(),
						((Line2D.Double)shape).getY2(),
					};
					writeToFile(out,args, ((Line)shape).getColor(), ((Line)shape).getThickness());
				}
				else if ( shape instanceof Arc2D.Double){
					out.write(ARC2D_DOUBLE_CONST + "\r\n");
					double [] args = {
						((Arc)shape).getX(),
						((Arc)shape).getY(),
						((Arc)shape).getWidth(),
						((Arc)shape).getHeight(),
						((Arc)shape).getAngleStart(),
						((Arc)shape).getAngleExtent(),
						((Arc)shape).getArcType(),
					};
					writeToFile(out, args, ((Arc)shape).getColor(), ((Arc)shape).getThickness());
				}
			}
			out.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeToFile(FileWriter writer, double [] args, Color c, int thickness ){
		try {
			for ( int i = 0; i < args.length; i++){
				writer.write(args[i] + "\r\n");
			}
			writer.write(c.getRGB() + "\r\n");
			writer.write(thickness + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void export(){
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileNameExtensionFilter("Picture Format (.png)", "png"));
		int val = fc.showSaveDialog(new JFrame());
		if ( val == JFileChooser.APPROVE_OPTION){
			String name = fc.getSelectedFile().getAbsolutePath();
			try{
				Image image = drawPad.exportImage();
				File outputFile = new File(name + ".png");
				ImageIO.write((RenderedImage) image, "png", outputFile);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
