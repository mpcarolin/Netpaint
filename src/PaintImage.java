import java.awt.Image;


public class PaintImage extends PaintObject {
	
	Image image;

	public PaintImage(int initialX, int initialY, int finalX, int finalY, Image image) {
		super(initialX, initialY, finalX, finalY);

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


