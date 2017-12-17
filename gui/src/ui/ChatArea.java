package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class ChatArea extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5349808453529387897L;
	private Conversation parent_;
	private int maxCols_;
	private int maxRows_;
	
	protected ChatArea(Conversation parent) {
		this.parent_ = parent;
		this.maxCols_ = 3; // TODO set from config file
		this.maxRows_ = 2; // TODO set from config file
		
		this.redisplay();
		this.setOpaque(true);
	}
	
	protected void redisplay() {
		int numThreads = this.parent_.getThreads().size();
		int numCols = numThreads < this.maxCols_ ? numThreads : this.maxCols_;
		int maxThreads = this.maxCols_ * this.maxRows_;
		
		this.removeAll();
		this.setLayout(new GridBagLayout());
		
		int i = 0;
		for (Thread thread : this.parent_.getThreads()) {
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = i % numCols;
			c.gridy = i / numCols;
			c.weightx = 0.5;
			c.weighty = 0.5;
			
			this.add(thread, c);
			i++;
			
			if (i >= maxThreads) {
				break;
			}
		}
		
		this.validate();
	}

}
