package utils;

import java.util.Comparator;
import java.util.Vector;

public class InsertionSortList<E extends Comparable<E>> extends Vector<E> {
	
	private class InsertionSortListComparator implements Comparator<E> {

		@Override
		public int compare(E o1, E o2) {
			return o1.compareTo(o2);
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8824725126223261015L;
	
	public void insert(E element) {
		synchronized (this) {
			for (int i = 0; i < this.size(); i++) {
				if (element.compareTo(this.get(i)) <= 0) {
					this.add(i, element);
				}
			}
			
			this.add(this.size(), element);
		}
	}
	
	public void sort() {
		this.sort(new InsertionSortListComparator());
	}

}
