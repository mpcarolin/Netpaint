/*
 *	Authors: Michael Carolin and Jacob Groh 
 * Date: 10/23/15
 * Class: CSC 335
 * Description: PaintLine extends PaintObject, 
 * implementing its abstract methods getPicture()
 * and update Picture in order to get correct
 * coordinates necessary to draw the Line.
 */
package model;
import java.awt.geom.Line2D;

public class PaintLine extends PaintObject {

	private Line2D line;
	
	public PaintLine(int initialX, int initialY, int finalX, int finalY) {
		super(initialX, initialY, finalX, finalY);
		line = new Line2D.Double(initialX, initialY, finalX, finalY);
	}

	@Override
	public Object getPicture() {
		return line;
	}
	
	@Override
	protected void updatePicture() {
		line.setLine(getInitialX(), getInitialY(), getFinalX(), getFinalY());
	}
}
