package ui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ChatArea extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5349808453529387897L;
	private List<Thread> threads_;
	private int maxCols_;
	private int maxRows_;
	
	protected ChatArea() {
		this.threads_ = new ArrayList<Thread>();
		this.maxCols_ = 3; // TODO set from config file
		this.maxRows_ = 2; // TODO set from config file
		
		this.redisplayThreads();
		this.setOpaque(true);
	}
	
	
	
	
	protected void addThread(Thread thread) {
		int maxThreads = this.maxCols_ * this.maxRows_;
		
		if (this.threads_.size() < maxThreads) {
			this.threads_.add(thread);
		} else {
			// find thread that was last active the longest ago and replace it
			int oldestIndex = 0;
			int longestTime = 0; // longest time since last activity
			for (int i = 0; i < this.threads_.size(); i++) {
				if (this.threads_.get(i).timeSinceLastActivity() > longestTime) {
					oldestIndex = i;
					longestTime = this.threads_.get(i).timeSinceLastActivity();
				}
			}
			
			this.threads_.set(oldestIndex, thread);
		}
		
		this.redisplayThreads();
	}
	
	private void redisplayThreads() {
		int numThreads = this.threads_.size();
		int numCols = numThreads < this.maxCols_ ? numThreads : this.maxCols_;
		
		List<JPanel> panels = new LinkedList<JPanel>();
		for (int i = 0; i < numCols; i++) {
			panels.add(new JPanel());
		}
		
		Iterator<Thread> it = this.threads_.iterator();
		while (it.hasNext()) {
			for (JPanel panel : panels) {
				panel.add(it.next());
				
				if (!it.hasNext()) {
					break;
				}
			}
		}
		
		this.removeAll();
		
		for (JPanel panel : panels) {
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setOpaque(true);
			this.add(panel);
		}
		
		this.setLayout(new GridLayout(1, numCols));
		this.validate();
	}

}
