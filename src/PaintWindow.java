import java.awt.*;
import java.awt.event.*;
import java.awt.image.RenderedImage;
import java.io.File;

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
	
	public PaintWindow(){
		setTitle("Paint it");
		setSize(1100, 700);

		panel = new JPanel();
		drawPad = new PadDraw();
		bottomPanel = new JPanel();
		colorPanel = new JPanel();
		thicknessText = new JTextField("1");
		thicknessSlider = new JSlider(1, 100);
		colorChooser = new JColorChooser();
		
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
		
		panel.setPreferredSize(new Dimension(32, 68));
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
		
		JButton lineButton = new JButton(new ImageIcon("line.gif"));
		lineButton.setPreferredSize(new Dimension(20,20));
		lineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.LINE);
				//System.out.println(drawPad.getLocationOnScreen());
			}
		});
		panel.add(lineButton);
		
		JButton straightLineButton = new JButton(new ImageIcon("straightline.gif"));
		straightLineButton.setPreferredSize(new Dimension(20,20));
		straightLineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.STRAIGHT_LINE);
			}
		});
		panel.add(straightLineButton);
		
		JButton rectButton = new JButton(new ImageIcon("rect.gif"));
		rectButton.setPreferredSize(new Dimension(20,20));
		rectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.RECT);
			}
		});
		panel.add(rectButton);
		
		JButton circButton = new JButton(new ImageIcon("circ.gif"));
		circButton.setPreferredSize(new Dimension(20,20));
		circButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.CIRC);
			}
		});
		panel.add(circButton);
		
		//arc button
		JButton arcButton = new JButton("Arc");
		arcButton.setPreferredSize(new Dimension(20,20));
		arcButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.ARC);
			}
		});
		panel.add(arcButton);
		
		//move button
		JButton moveButton = new JButton("Move");
		moveButton.setPreferredSize(new Dimension(20, 20));
		moveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.setOption(PadDraw.MOVE);
			}
		});
		panel.add(moveButton);
		
		//creates the clear button
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.clearAll();
			}
		});
		panel.add(clearButton);
	}
	
	public void addMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenu = new JMenuItem("New");
		JMenuItem saveMenu = new JMenuItem("Save");
		JMenuItem saveAsMenu = new JMenuItem("Save As");
		JMenuItem openMenu = new JMenuItem("Open");
		JMenuItem exportMenu = new JMenuItem("Export Image");
		exportMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				export();
			}
		});
		fileMenu.add(newMenu);
		fileMenu.add(openMenu);
		fileMenu.add(saveMenu);
		fileMenu.add(saveAsMenu);
		fileMenu.add(exportMenu);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}
	
	public void export(){
		JFileChooser fc = new JFileChooser();
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
