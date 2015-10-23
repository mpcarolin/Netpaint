import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PaintImage extends PaintObject {
	
	private Image image;

	public PaintImage(int initialX, int initialY, int finalX, int finalY, String filepath) {
		super(initialX, initialY, finalX, finalY);
		try {
			image = ImageIO.read(new File(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getPicture() {
		return image;
	}

	@Override
	protected void updatePicture() {
		// use other scale constants for different algorithms for scaling (some prioritize speed)
		image = image.getScaledInstance(
						getCurrentWidth(), getCurrentHeight(),Image.SCALE_DEFAULT);
	}
}


