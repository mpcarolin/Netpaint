/*
 *	Authors: Michael Carolin and Jacob Groh 
 * Date: 10/23/15
 * Class: CSC 335
 * Description: PaintRectangle extends PaintObject, 
 * implementing its abstract methods getPicture()
 * and update Picture in order to get correct
 * coordinates necessary to draw the Rectangle.
 */
package model;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PaintRectangle extends PaintObject {

	private Rectangle rectangle;

	public PaintRectangle(int initialX, int initialY, int finalX, int finalY) {
		super(initialX, initialY, finalX, finalY);
		rectangle = new Rectangle(initialX, initialY, getCurrentWidth(), getCurrentHeight());
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
		PaintRectangle rect = new PaintRectangle(0,0, 2,2);
		rect.setDimensions(10, 10);
		Rectangle rectUnder = rect.getPicture();
		System.out.println(rectUnder.getWidth());
	}
}

