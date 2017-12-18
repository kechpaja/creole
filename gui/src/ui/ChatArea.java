package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

public class ChatArea extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5349808453529387897L;
	private Conversation parent_;
	private List<Chat> threadsDisplayed_;
	private int maxCols_;
	private int maxRows_;
	
	protected ChatArea(Conversation parent) {
		this.parent_ = parent;
		this.threadsDisplayed_ = new ArrayList<Chat>();
		this.maxCols_ = 3; // TODO set from config file
		this.maxRows_ = 2; // TODO set from config file
		
		this.redisplay();
		this.setOpaque(true);
	}
	
	protected void redisplay() {
		//this.parent_.getChats().sort();
		int numThreads = this.parent_.getChats().size();
		int numCols = numThreads < this.maxCols_ ? numThreads : this.maxCols_;
		int maxThreads = this.maxCols_ * this.maxRows_;
		
		// Figure out which threads need to be added and which are already displayed
		List<Chat> threadsToAdd = new ArrayList<Chat>();
		List<Chat> threadsToLeaveBe = new ArrayList<Chat>();
		Iterator<Chat> it = this.parent_.getChats().iterator();
		for (int i = 0; i < maxThreads; i++) {
			if (!it.hasNext()) {
				break;
			}
			
			Chat thread = it.next();
			if (this.threadsDisplayed_.contains(thread)) {
				threadsToLeaveBe.add(thread);
			} else {
				threadsToAdd.add(thread);
			}
		}
		
		// Replace ones which need to be swapped out
		for (int i = 0; i < maxThreads; i++) {
			if (threadsToAdd.size() <= 0) {
				break;
			}
			
			if (i >= this.threadsDisplayed_.size()) {
				this.threadsDisplayed_.add(threadsToAdd.remove(0));
			} else if (!threadsToLeaveBe.contains(this.threadsDisplayed_.get(i))) {
				this.threadsDisplayed_.set(i, threadsToAdd.remove(0));
			}
		}
		
		// Redisplay
		this.removeAll();
		this.setLayout(new GridBagLayout());
		
		int i = 0;
		for (Chat thread : this.threadsDisplayed_) {
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
		
		this.revalidate();
		this.repaint();
	}

}
