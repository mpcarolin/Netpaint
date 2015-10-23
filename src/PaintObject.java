import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class PaintObject {
	
	private int initialX, initialY;						
	private int finalX, finalY;
	private Color color;

	
	// initial X and Y refer to the upper left coordinate of the PaintObject's framing rectangle,
	// and final X and Y refer to that framing rectangle's bottom right coordinate
	public PaintObject(int initialX, int initialY, int finalX, int finalY) {
		this.initialX 	= initialX;
		this.initialY 	= initialY;
		this.finalX  	= finalX;
		this.finalY  	= finalY;
		color 			= Color.BLACK;	// default
	}
	
	/* ====================== Methods ========================= */
	
	public int getInitialX() {
		return initialX;
	}

	public int getInitialY() {
		return initialY;
	}

	public int getFinalX() {
		return finalX;
	}

	public int getFinalY() {
		return finalY;
	}
	
	public int getCurrentHeight() {
		return finalY - initialY;
	}
	
	public int getCurrentWidth() {
		return finalX - initialX;
	}
	
	public Color getColor() {
		return color;
	}

	public void setDimensions(int newWidth, int newHeight) {
		finalX = initialX + newWidth;
		finalY = initialY + newHeight;
		updatePicture();
	}
	
	public void setLocation(int xCoordinate, int yCoordinate) {
		// gather current dimensions
		int width = getCurrentWidth();
		int height = getCurrentHeight();
		
		// shift upper left corner of framing rectangle
		initialX = xCoordinate;
		initialY = yCoordinate;
		
		// shift bottom right corner of framing rectangle
		finalX = initialX + width;
		finalY = initialY + height;

		updatePicture();
	}
	
	public void setColor(Color color) {
		this.color = color;
	}


	/* ================== Abstract Methods ===================== */
	
	/* Returns the underlying shape or image object upon which any class that 
	 * extends PaintObject is built. Should be an object that a Graphics2D object
	 * can draw onto a canvas, such as Shape or Image.
	 */
	public abstract Object getPicture();	
	
	/* Updates the underlying shape or image object to align with any changes
	 * executed by the setter methods
	 */
	protected abstract void updatePicture();
	
	
																	
												
	
}
