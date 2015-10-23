import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RectangleShape extends PaintObject {

	private Rectangle rectangle;

	public RectangleShape(int initialX, int initialY, int finalX, int finalY) {
		super(initialX, initialY, finalX, finalY);
		rectangle = new Rectangle(getCurrentWidth(), getCurrentHeight(), initialX, initialY);
	}
	
	@Override
	public Rectangle getPicture() {
		return rectangle;
	}

	@Override
	protected void updatePicture() {
		rectangle.setLocation(getInitialX(), getInitialY());
		rectangle.setSize(getCurrentWidth(), getCurrentHeight());
	}
	
	// testing
	public static void main(String[] args) {
		RectangleShape rect = new RectangleShape(0,0, 2,2);
		rect.setDimensions(10, 10);
		Rectangle rectUnder = rect.getPicture();
		System.out.println(rectUnder.getWidth());
	}
}

