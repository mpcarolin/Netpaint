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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
import model.PaintServer;

public class NetPaintClient extends JFrame {

	// Client and server socket configuration
	private static final String ADDRESS = "localhost";
	private Socket server;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;

	// graphics variables
	private Graphics2D g2;
	private PaintObjectList pictures = new PaintObjectList();
	private Image imageToDraw;
	private String currentPictureString = "";
	private volatile ImageIcon imageWrapper;
	private Vector<Point> pointList=new Vector<Point>();

	public NetPaintClient() throws UnknownHostException, IOException {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 630);
		this.setLocation(0, 0);
		layoutGUI();
		

		// obtain the picture for the image option
		try {
			imageToDraw = ImageIO.read(new File("./Image/Funny-Memes.jpg"));
			imageWrapper = new ImageIcon(imageToDraw);
		} catch (IOException e) {
			e.printStackTrace();
		}

		openConnection();
		ServerListener serverListener = new ServerListener();
		serverListener.start();

	}

	private void openConnection() {
		try {
			server = new Socket(ADDRESS, PaintServer.SERVER_PORT);
			toServer = new ObjectOutputStream(server.getOutputStream());
			fromServer = new ObjectInputStream(server.getInputStream());
		//	System.out.println("Connect to server at " + ADDRESS + ": " + PaintServer.SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private Vector<PaintObject> serverPictures;
	private JColorChooser colors;
	private JRadioButton line, rectangle, oval, image;
	private JButton undo,clear;
	private ButtonGroup shapechoices;
	private JPanel panel, southPanel;
	private DrawingPanel scrollPanel;
	private JScrollPane canvus;
	private JButton colorChooser;
	private PaintObject currentPicture;
	private Color currentColor;
	private boolean hasClickedOnce = false;

	// Sets up the GUI to contain a JScrollPanel, ButtonGroup, and JColorChooser
	private void layoutGUI() {
		panel = new JPanel(new BorderLayout());
		southPanel = new JPanel(null);
		southPanel.setLocation(0, 0);

		// shapes and image options
		line = new JRadioButton("Line");
		rectangle = new JRadioButton("Rectangle");
		oval = new JRadioButton("Oval");
		image = new JRadioButton("Image");
		undo= new JButton("Undo");
		clear= new JButton("Clear");
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
		undo.addActionListener(new undoButtonListener());
		clear.addActionListener(new clearButtonListener());

		// color chooser s
		colorChooser = new JButton("Change Colors");

		// add button listeners and actions
		line.setSize(70, 40);
		line.setLocation(215, 20);
		rectangle.setSize(100, 40);
		rectangle.setLocation(275, 20);
		oval.setSize(70, 40);
		oval.setLocation(375, 20);
		image.setSize(100, 40);
		image.setLocation(435, 20);
		undo.setSize(80,40);
		undo.setLocation(520, 20);
		clear.setSize(80,40);
		clear.setLocation(610,20);
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
		southPanel.add(undo);
		southPanel.add(clear);
		southPanel.setPreferredSize(new Dimension(1000, 100));
		scrollPanel = new DrawingPanel(pictures);
		scrollPanel.setSize(1000, 380);
		scrollPanel.setLocation(0, 0);
		scrollPanel.setPreferredSize(new Dimension(2000, 2000));
		scrollPanel.setVisible(true);
		scrollPanel.setBackground(Color.WHITE);

		canvus = new JScrollPane(scrollPanel);
		canvus.setSize(990, 340);

		canvus.setLocation(0, 0);
		canvus.setVisible(true);
		scrollPanel.addMouseListener(new mouseLocationListener());
		scrollPanel.addMouseMotionListener(new motionListener());
		scrollPanel.addMouseListener(new mouseReleasedListener());

		southPanel.setVisible(true);
		panel.add(BorderLayout.SOUTH, southPanel);
		panel.add(BorderLayout.CENTER, canvus);
		this.add(panel);

		this.setVisible(true);
	}
	private class clearButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(serverPictures.size()>0){

				serverPictures.clear();
				try {
					toServer.writeObject(serverPictures);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	private class undoButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(serverPictures.size()>0){

				serverPictures.remove(serverPictures.size()-1);
				try {
					toServer.writeObject(serverPictures);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	private class colorButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = scrollPanel.getBackground();
			color = JColorChooser.showDialog(null, "Choose a Color", color);
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
			if (e.getClickCount() % 3 != 0) {
				if (e.getClickCount() % 2 == 1 && !hasClickedOnce) {
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
						currentPicture = new PaintImage(initX, initY, initX, initY, imageWrapper);
						break;
					case "rectangle": // if a rectangle or an unsupported
										// option, draw a rectangle
						currentPicture = new PaintRectangle(initX, initY, initX, initY);
						break;
					default: 
						currentPicture = new PaintLine(initX, initY, initX, initY);
						break;

					}

					currentPicture.setLocation(initX, initY);
					currentPicture.setFinalX(e.getX());
					currentPicture.setFinalY(e.getY());
					currentPicture.setColor(currentColor);

				} else {
					currentPicture.setFinalX(e.getX());
					currentPicture.setFinalY(e.getY());
					hasClickedOnce = false;

					try {
						toServer.writeObject(currentPicture);
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
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

	private class mouseReleasedListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

			for (int i = 0; i < pointList.size() - 1; i++) {
				currentPicture = new PaintLine((int) pointList.get(i).getX(), (int) pointList.get(i).getY(),
						(int) pointList.get(i + 1).getX(), (int) pointList.get(i + 1).getY());
				pictures.add(currentPicture);
				currentPicture.setColor(currentColor);
				drawObjects(pictures);
				try {
					toServer.writeObject(currentPicture);
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}

			}
			pointList.clear();

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

	public void addpoint(int x, int y) {
		// System.out.println(x+" "+y);
		pointList.add(new Point(x, y));
	}

	private class motionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			int x = (int) e.getX();
			int y = (int) e.getY();
			currentPicture = new PaintLine(x, y, x, y);
			addpoint(x, y);

			pictures.add(currentPicture);
			drawObjects(pictures);
			try {
				toServer.writeObject(currentPicture);
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}


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

		public void setPictures(PaintObjectList pictures) {
			this.pictures = pictures;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			for (PaintObject picture : pictures) {
				Object underObject = picture.getPicture();
				g2.setColor(picture.getColor());
				if (underObject instanceof ImageIcon) {
					ImageIcon underObjectImage = (ImageIcon) underObject;
					g2.drawImage((Image) underObjectImage.getImage(), picture.getInitialX(), picture.getInitialY(),
							picture.getCurrentWidth(), picture.getCurrentHeight(), null);
				} else if (underObject instanceof Line2D) {
					g2.draw((Shape) underObject);
				} else if (underObject instanceof Shape) {
					g2.fill((Shape) underObject);
				}
			}
		}
	}

	private class ServerListener extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					serverPictures = (Vector<PaintObject>) fromServer.readObject();
					NetPaintClient.this.pictures.setVector(serverPictures);
					NetPaintClient.this.drawObjects(pictures);
				} catch (ClassNotFoundException | IOException e) {
					//e.printStackTrace();
					return;
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			NetPaintClient gui = new NetPaintClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
