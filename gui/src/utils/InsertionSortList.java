package utils;

import java.util.Vector;

public class InsertionSortList<E extends Comparable<E>> extends Vector<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8824725126223261015L;
	
	public void insert(E element) {
		synchronized (this) {
			for (int i = this.size(); i > 0; i--) {
				if (element.compareTo(this.get(i - 1)) >= 0) {
					this.add(i, element);
					return;
				}
			}
			
			this.add(0, element);
		}
	}

}
