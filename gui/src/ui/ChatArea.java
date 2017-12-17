package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

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
			long longestTime = 0; // longest time since last activity
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
		
		this.removeAll();
		this.setLayout(new GridBagLayout());
		
		int i = 0;
		for (Thread thread : this.threads_) {
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = i % numCols;
			c.gridy = i / numCols;
			c.weightx = 0.5;
			c.weighty = 0.5;
			
			this.add(thread, c);
			i++;
		}
		
		this.validate();
	}

}
