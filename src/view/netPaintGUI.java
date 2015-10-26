/*
 *	Authors: Michael Carolin and Jacob Groh 
 * Date: 10/23/15
 * Class: CSC 335
 * Description: a JFrame subclass that manages a PaintObjectList and prints
 * it in the JScroll Panel. Includes numerous gui elements such as
 * JPanel, JRadioButtons, JColorChooser,...ect
 */
package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
	private PaintObjectList pictures = new PaintObjectList();
	private Image imageToDraw;
	private String currentPictureString = "";


	public netPaintGUI(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000,630);
		this.setLocation(0,0);
		layoutGUI();
		
		// obtain the picture for the image option
		try {
			imageToDraw = ImageIO.read(new File("./Image/Funny-Memes.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JColorChooser colors;
	private JRadioButton  line, rectangle, oval, image;
	private ButtonGroup   shapechoices;
	private JPanel panel, southPanel;
	private DrawingPanel scrollPanel;
	private JScrollPane canvus;
	private JButton  colorChooser;
	private PaintObject currentPicture;
	private Color currentColor;
	private boolean hasClickedOnce = false;
	private boolean skipFirst = false;

	//Sets up the GUI to contain a JScrollPanel, ButtonGroup, and JColorChooser
	private void layoutGUI() {
		panel			= new JPanel(new BorderLayout());
		southPanel	= new JPanel(null);
		southPanel.setLocation(0, 0);

		// shapes and image options 
		line			= new JRadioButton("Line");
		rectangle = new JRadioButton("Rectangle");
		oval			= new JRadioButton("Oval");
		image		= new JRadioButton("Image");
		
		// set action commands so listener knows which button is selected
		line.setActionCommand("line");
		rectangle.setActionCommand("rectangle");
		oval.setActionCommand("oval");
		image.setActionCommand("image");
		
		// set action listeners
		line.addActionListener(new pictureButtonListener());
		rectangle.addActionListener(new pictureButtonListener());
		oval.addActionListener(new pictureButtonListener());
		image.addActionListener(new pictureButtonListener());
		
		// color chooser 
		colorChooser= new JButton("Change Colors");

		// add button listeners and actions
		line.setSize(70, 40);
		line.setLocation(215, 20);
		rectangle.setSize(100, 40);
		rectangle.setLocation(275, 20);
		oval.setSize(70, 40);
		oval.setLocation(375, 20);
		image.setSize(100, 40);
		image.setLocation(435, 20);
		colorChooser.setSize(140, 40);
		colorChooser.setLocation(20, 20);
		colorChooser.setVisible(true);

		colorChooser.addActionListener(new colorButtonListener());
		shapechoices = new ButtonGroup();
		shapechoices.add(line);
		shapechoices.add(rectangle);
		shapechoices.add(oval);
		shapechoices.add(image);

		southPanel.add(line);
		southPanel.add(rectangle);
		southPanel.add(oval);
		southPanel.add(image);
		southPanel.add(colorChooser);
		southPanel.setPreferredSize(new Dimension(1000,100));
		scrollPanel = new DrawingPanel(pictures);
		scrollPanel.setSize(1000, 380);
		scrollPanel.setLocation(0, 0);
		scrollPanel.setPreferredSize(new Dimension(2000,2000));
		scrollPanel.setVisible(true);
		scrollPanel.setBackground(Color.WHITE);
		
		canvus = new JScrollPane(scrollPanel);
		canvus.setSize(990, 340);

		canvus.setLocation(0, 0);
		canvus.setVisible(true);
		scrollPanel.addMouseListener(new mouseLocationListener());
		scrollPanel.addMouseMotionListener(new motionListener());
		southPanel.setVisible(true);
		panel.add(BorderLayout.SOUTH,southPanel);
		panel.add(BorderLayout.CENTER,canvus);
		this.add(panel);

		this.setVisible(true);
	}
	
	private class colorButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = scrollPanel.getBackground();
			color = JColorChooser.showDialog(null, "Choose a Color", color );
			currentColor = color;
		}
	}
	
	private class pictureButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String receivedCommand = e.getActionCommand();
			currentPictureString = receivedCommand;
		}
	}
	
	private class mouseLocationListener implements MouseListener {
		private int initX;
		private int initY;

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()%3!=0){
				if(e.getClickCount()%2==1&& !hasClickedOnce){
					hasClickedOnce = true;
				    initX = e.getX();
				    initY = e.getY();
				    // determine which shape or image to draw
					switch (currentPictureString) {
						case "line":
							currentPicture = new PaintLine(initX, initY, initX, initY);
							break;
						case "oval":
							currentPicture = new PaintOval(initX, initY, initX, initY);
							break;
						case "image":
							currentPicture = new PaintImage(initX, initY, initX, initY, imageToDraw);
							break;
						default: 	// if a rectangle or an unsupported option, draw a rectangle
							currentPicture = new PaintRectangle(initX, initY, initX, initY);
							break;
					}

					currentPicture.setLocation(initX, initY);	
					currentPicture.setFinalX(e.getX());
					currentPicture.setFinalY(e.getY());
					currentPicture.setColor(currentColor);
				} else{
					currentPicture.setFinalX(e.getX());
					currentPicture.setFinalY(e.getY());
					hasClickedOnce = false;
				}
				pictures.add(currentPicture);
				drawObjects(pictures);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

			
		}
	}
	
	
	private class motionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// if the user hasn't clicked again, resize PaintObject
			if (hasClickedOnce) {
				currentPicture.setFinalX(e.getX());
				currentPicture.setFinalY(e.getY());
				drawObjects(pictures);
			}
		}
	}
	
	// Calls repaint on a list of picture objects
	public void drawObjects(PaintObjectList pictures) {
		scrollPanel.setPictures(pictures);
		scrollPanel.repaint();
	}

	// inner class that contains the paintComponent method that draws 
	// the images onto the JScrollPanel
	class DrawingPanel extends JPanel {
		
		private PaintObjectList pictures;
		
		public DrawingPanel(PaintObjectList pictures) {
			this.pictures = pictures; 
		}
		
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
					g2.drawImage((Image)underObject, picture.getInitialX(), picture.getInitialY(), 
											picture.getCurrentWidth(), picture.getCurrentHeight(),  null);
				} else if (underObject instanceof Line2D) {
					g2.draw((Shape)underObject);
				} else if (underObject instanceof Shape) {
					g2.fill((Shape)underObject);
				}
			}

		} 
	}
	
	public static void main(String [] args){
		netPaintGUI gui = new netPaintGUI();
	}
	
}
