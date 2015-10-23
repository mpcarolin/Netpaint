package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.Shape;
import java.awt.geom.Line2D;
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

import model.PaintImage;
import model.PaintLine;
import model.PaintObject;
import model.PaintObjectList;
import model.PaintOval;
import model.PaintRectangle;

public class netPaintGUI extends JFrame {
	
	private Graphics2D g2;
	private PaintObjectList pictures;
	
	public netPaintGUI(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000,630);
		this.setLocation(0,0);
		layoutGUI();
	}
	
	private JColorChooser colors;
	private JRadioButton  line, rectangel, oval, image;
	private ButtonGroup   shapechoices;
	private JPanel panel;
	private DrawingPanel scrollPanel;
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

		scrollPanel = new DrawingPanel();
		scrollPanel.setSize(1000, 380);
		scrollPanel.setLocation(0, 0);
		scrollPanel.setPreferredSize(new Dimension(2000,2000));
		scrollPanel.setVisible(true);
		scrollPanel.setBackground(Color.WHITE);
		canvus= new JScrollPane(scrollPanel);
		
		
		// This is the Thing that goes  inside the canvas
		//JTextArea testArea= new JTextArea();
		//testArea.setVisible(true);
		//scrollPanel.add(testArea);

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
	
	public void drawObjects(PaintObjectList pictures) {
		scrollPanel.setPictures(pictures);
		scrollPanel.repaint();
	}
	
	class DrawingPanel extends JPanel {
		
		private PaintObjectList pictures;
		
		public void setPictures(PaintObjectList pictures){
			this.pictures = pictures;
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			for (PaintObject picture : pictures) {
				Object underObject = picture.getPicture();
				g2.setColor(picture.getColor());
				if (underObject instanceof Image) {
					g2.drawImage((Image)underObject, picture.getInitialX(), picture.getInitialY(), null);
				} else if (underObject instanceof Line2D) {
					g2.draw((Shape)underObject);
				} else if (underObject instanceof Shape) {
					g2.fill((Shape)underObject);
				}
			}
		}
	}

	public static void main(String [] args){
		PaintObjectList objectList= new PaintObjectList();
		objectList.add(new PaintLine(220,10,320,100));
		objectList.get(0).setColor(Color.GREEN);
		objectList.add(new PaintLine(320,10,220,100));
		objectList.get(1).setColor(Color.GREEN);
		objectList.add(new PaintRectangle(10,10,100,100));
		objectList.get(2).setColor(Color.RED);
		objectList.add(new PaintOval(350,10,450,100));
		objectList.get(3).setColor(Color.BLUE);

		try {
			Image meme = ImageIO.read(new File("./Image/Funny-Memes.jpg"));
			objectList.add(new PaintImage(10,110 , 200, 300,meme));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (PaintObject pic : objectList) {
			System.out.println(pic.getCurrentHeight());
		}

		netPaintGUI gui = new netPaintGUI();
		
		gui.drawObjects(objectList);
		
		
		
	
	}
	
}
