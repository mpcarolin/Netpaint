import java.awt.Shape;

public abstract class PaintObject {
	
	private int currentHeight, currentWidth;
	private int X, Y;	// X and Y coordinates
	private Shape underlyingShape;	// each PaintObject subclass should use a Java Shape Object 
															// for drawing
	
	
	// implemented methods common to all subclasses
	public int getXCoordinate() {
		return X;
	}

	public int getYCoordinate() {
		return Y;
	}
	
	public int getCurrentHeight() {
		return currentHeight;
	}
	
	public int getCurrentWidth() {
		return currentWidth;
	}
	
	// abstract classes that each sub-class should implement differently
	public abstract void setDimensions(int newHeight, int newWidth);
	
	public abstract void setLocation(int xCoordinate, int yCoordinate);
	
	
	public abstract void draw(int xCoordinate, int yCoordinate);
}
