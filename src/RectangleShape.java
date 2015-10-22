import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RectangleShape extends PaintObject {

	Rectangle rectangle;

	public RectangleShape(int xCoordinate, int yCoordinate, int width, int height) {
		super(xCoordinate, yCoordinate, width, height);
		rectangle = new Rectangle(width, height, xCoordinate, yCoordinate);
	}
	
	// draws rectangle at a new location and with a new size
	public void draw(int nextX, int nextY, int nextWidth, int nextHeight, Graphics2D g2) {
		super.setLocation(nextX, nextY);
		super.setDimensions(nextWidth, nextHeight);
		g2.draw(rectangle);
		g2.fill(rectangle);		// fills with rectangle's current color
	}
	
	// draws rectangle using values created at instantiation
	public void draw(Graphics2D g2) {
		g2.draw(rectangle);
		g2.fill(rectangle);		// fills with rectangle's current color
	}
}

