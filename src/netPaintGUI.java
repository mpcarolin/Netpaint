import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

public class netPaintGUI extends JFrame {
	public netPaintGUI(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000,630);
		this.setLocation(0,0);
		layoutGUI();
	}

	private JColorChooser colors;
	private JRadioButton  line, rectangel, oval, image;
	private ButtonGroup   shapechoices;
	private JPanel panel, scrollPanel;
	private JScrollPane canvus;
	private JScrollBar bar;
	private JViewport viewforscroll;
	private void layoutGUI() {
		panel= new JPanel(null);
		panel.setSize(1000, 630);
		panel.setLocation(0, 0);
		line= new JRadioButton("Line");
		rectangel= new JRadioButton("Rectangel");
		oval= new JRadioButton("Oval");
		image= new JRadioButton("Image");
		line.setSize(70, 40);
		line.setLocation(345, 340);
		rectangel.setSize(100, 40);
		rectangel.setLocation(405, 340);
		oval.setSize(70, 40);
		oval.setLocation(505, 340);
		image.setSize(100, 40);
		image.setLocation(565, 340);
		//add button listeners and actions...

		shapechoices= new ButtonGroup();
		shapechoices.add(line);
		shapechoices.add(rectangel);
		shapechoices.add(oval);
		shapechoices.add(image);

		colors= new JColorChooser();
		colors.setSize(1000, 320);
		colors.setLocation(0, 380);
		colors.setVisible(true);
		//panel.add(shapechoices);
		//bar=new JScrollBar();
		//bar.setSize(30,90);
		//bar.setLocation(980, 0);
		//bar.setVisible(true);
		//viewforscroll= new JViewport();
		scrollPanel= new JPanel();
		scrollPanel.setSize(1000, 380);
		scrollPanel.setLocation(0, 0);
		scrollPanel.setVisible(true);
		scrollPanel.setBackground(Color.WHITE);
		canvus= new JScrollPane(scrollPanel);
		JTextArea testArea= new JTextArea();
		//testArea.setSize(1000, 1000);
		//testArea.setLocation(0,0);
		testArea.setVisible(true);
		scrollPanel.add(testArea, BorderLayout.WEST);

		//canvus = new JScrollPane();
		canvus.setSize(990, 340);
		//canvus.setPreferredSize(new Dimension(990,340));
		canvus.setLocation(0, 0);
		//canvus.createVerticalScrollBar();
		//canvus.add(bar);
		//canvus.
		canvus.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		canvus.setVisible(true);


		//panel.add(scrollPanel);
		panel.add(colors);
		panel.add(canvus);
		this.add(line);
		this.add(rectangel);
		this.add(oval);
		this.add(image);
		this.add(panel);

		this.setVisible(true);

	}
	public static void main(String [] args){
		new netPaintGUI();
	}

}
