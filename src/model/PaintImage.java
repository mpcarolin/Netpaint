/*
 *	Authors: Michael Carolin and Jacob Groh 
 * Date: 10/23/15
 * Class: CSC 335
 * Description: Paint Image extends PaintObject, 
 * implementing its abstract methods getPicture()
 * and update Picture in order to get correct
 * coordinates necessary to draw the Image.
 */

package model;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class PaintImage extends PaintObject {
	
	private Image image;
	private boolean changeX = false;
	private boolean changeY = false;

	public PaintImage(int initialX, int initialY, int finalX, int finalY, Image image) {
		super(initialX, initialY, finalX, finalY);
		this.image = image;
		updatePicture();
	}

	@Override
	public Object getPicture() {
		return image;
	}

	@Override
	protected void updatePicture() {}

}


