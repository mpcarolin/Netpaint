/*
 *	Authors: Michael Carolin and Jacob Groh 
 * Date: 10/23/15
 * Class: CSC 335
 * Description: Serializable which means it can be
 * saved and reloaded after being closed. Creates a 
 * private ArrayList of Paint Object with methods to
 * get the index, add PaintObject, ask it the list Contains 
 * the PaintObject, asks if empty, asks the size, 
 * and made the list Iterable .
 */
package model;
/*
 * Description: an ArrayList wrapper class for Paint Objects that implements serializable
 * for saving to disk and sending the data over a network for the NetPaint application.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class PaintObjectList implements Serializable, Iterable<PaintObject> {
	
	private ArrayList<PaintObject> pictures = new ArrayList<PaintObject>();
	
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
				PaintObject obj = PaintObjectList.this.get(currentIndex);
				currentIndex++;
				return obj;
		}
		
	}

}
