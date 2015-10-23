/*
 * Description: an ArrayList wrapper class for Paint Objects that implements serializable
 * for saving to disk and sending the data over a network for the NetPaint application.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class PaintObjectList implements Serializable, Iterable<PaintObject> {
	
	ArrayList<PaintObject> pictures = new ArrayList<PaintObject>();
	
	public PaintObject get(int index) {
		return pictures.get(index);
	}
	
	public void add(PaintObject picture) {
		pictures.add(picture);
	}
	
	public boolean contains(PaintObject picture) {
		return pictures.contains(picture);
	}
	
	public boolean isEmpty() {
		return pictures.isEmpty();
	}
	
	public int size() {
		return pictures.size();
	}
	
	@Override
	public Iterator<PaintObject> iterator() {
		return new paintIterator();
	}
	
	private class paintIterator implements Iterator<PaintObject> {
		
		int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return !PaintObjectList.this.isEmpty() 
						&& (currentIndex < PaintObjectList.this.size());
		}

		@Override
		public PaintObject next() {
				return hasNext() ? PaintObjectList.this.get(currentIndex) : null;
		}
		
	}

}
