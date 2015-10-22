import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class PaintObject {
	
	private int currentHeight, currentWidth;
	private int initialHeight, initialWidth;			
	private int initialX, initialY;						
	private Color color;

	
	public PaintObject(int xCoordinate, int yCoordinate, int width, int height) {
		initialHeight = height;
		initialWidth 	= width;
		initialX 			= xCoordinate;
		initialY 			= yCoordinate;
		color 			= Color.BLACK;	// default
	}
	
	/* ====================== Methods ========================= */
	
	public int getXCoordinate() {
		return initialX;
	}

	public int getYCoordinate() {
		return initialY;
	}
	
	public int getCurrentHeight() {
		return initialHeight;
	}
	
	public int getCurrentWidth() {
		return initialWidth;
	}
	
	public Color getColor() {
		return color;
	}

	public void setDimensions(int newWidth, int newHeight) {
		currentHeight 	= newHeight;
		currentWidth 	= newWidth;
	}
	
	public void setLocation(int xCoordinate, int yCoordinate) {
		initialX = xCoordinate;
		initialY = yCoordinate;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}


	/* ================== Abstract Methods ===================== */
	
	public abstract void draw(int nextX, int nextY, int nextWidth, int nextHeight, Graphics2D g2);	
	
}
