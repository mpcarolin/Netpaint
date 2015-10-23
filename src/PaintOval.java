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
		oval.setFrame((double)getInitialX(), (double)getInitialY(), getCurrentWidth(), getCurrentHeight());
	}

}
