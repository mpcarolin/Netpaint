import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

		scrollPanel= new JPanel();
		scrollPanel.setSize(1000, 380);
		scrollPanel.setLocation(0, 0);
		scrollPanel.setPreferredSize(new Dimension(2000,2000));
		scrollPanel.setVisible(true);
		scrollPanel.setBackground(Color.WHITE);
		canvus= new JScrollPane(scrollPanel);
		
		
		// This is the Thing that goes  inside the canvas
		JTextArea testArea= new JTextArea();
		testArea.setVisible(true);
		scrollPanel.add(testArea);

		canvus.setSize(990, 340);
		canvus.setLocation(0, 0);

		canvus.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		canvus.setVisible(true);
		
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
		PaintObjectList objectList= new PaintObjectList();
		objectList.add(new PaintLine(10,10,50,20));
		objectList.add(new PaintRectangle(60,60,100,80));
		objectList.add(new PaintOval(100,100,150,150));
		try {
			Image meme = ImageIO.read(new File("./Image/Funny-Memes.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		objectList.add(new PaintImage(20,20, 50,100,meme));
	
	}
	
}
