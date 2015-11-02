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
import java.io.Serializable;

import javax.swing.ImageIcon;


public class PaintImage extends PaintObject implements Serializable {
	
	private Image image;
	private ImageIcon imageWrapper;
	private boolean changeX = false;
	private boolean changeY = false;

	public PaintImage(int initialX, int initialY, int finalX, int finalY, ImageIcon imageWrapper) {
		super(initialX, initialY, finalX, finalY);
		this.imageWrapper = imageWrapper;
		updatePicture();
	}

	@Override
	public Object getPicture() {
		return imageWrapper;
	}

	@Override
	protected void updatePicture() {}

}


