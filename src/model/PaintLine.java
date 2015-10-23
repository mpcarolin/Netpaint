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
