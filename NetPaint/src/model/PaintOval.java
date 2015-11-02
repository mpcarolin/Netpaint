/*
 *	Authors: Michael Carolin and Jacob Groh 
 * Date: 10/23/15
 * Class: CSC 335
 * Description: PaintOval extends PaintObject, 
 * implementing its abstract methods getPicture()
 * and update Picture in order to get correct
 * coordinates necessary to draw the oval.
 */
package model;
import java.awt.geom.Ellipse2D;

public class PaintOval extends PaintObject {
	
	private Ellipse2D oval;

	public PaintOval(int initialX, int initialY, int finalX, int finalY) {
		super(initialX, initialY, finalX, finalY);
		
		
		oval 	= new Ellipse2D.Double((double)initialX, (double)initialY, (double)getCurrentWidth(), (double)getCurrentHeight());
	}

	@Override
	public Object getPicture() {
		return oval;
	}

	@Override
	protected void updatePicture() {
			int initX = getInitialX();
			int initY = getInitialY();
			int finalX = getFinalX();
			int finalY = getFinalY();
			int width = Math.abs(getCurrentWidth());
			int height = Math.abs(getCurrentHeight());

			if (finalX - initX < 0) {
				int temp = finalX;
				finalX = initX;
				initX = temp;
			}
			
			if (finalY - initY < 0) {
				int temp = finalY;
				finalY = initY;
				initY = temp;
			}

		
		oval.setFrame((double)initX, (double)initY, width, height);
	}

}
