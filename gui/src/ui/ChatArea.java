package ui;

import java.awt.GridLayout;
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
	
	protected ChatArea(Conversation parent) {
		this.parent_ = parent;
		this.threadsDisplayed_ = new ArrayList<Chat>();
		
		this.redisplay();
		this.setOpaque(true);
	}
	
	protected void redisplay() {
		// TODO I'm sure it's possible to do this in a more idiomatic way, but I'll worry about that later. 
		
		int numThreads = this.parent_.getChats().size();
		int numCols = numThreads < UiConfigData.getChatAreaMaxColumns() ? numThreads : UiConfigData.getChatAreaMaxColumns();
		int maxThreads = UiConfigData.getChatAreaMaxColumns() * UiConfigData.getChatAreaMaxRows();
		
		// Figure out which threads need to be added and which are already displayed
		List<Chat> chatsToAdd = new ArrayList<Chat>();
		List<Chat> chatsToLeaveBe = new ArrayList<Chat>();
		Iterator<Chat> it = this.parent_.getChats().iterator();
		for (int i = 0; i < maxThreads; i++) {
			if (!it.hasNext()) {
				break;
			}
			
			Chat thread = it.next();
			if (this.threadsDisplayed_.contains(thread)) {
				chatsToLeaveBe.add(thread);
			} else {
				chatsToAdd.add(thread);
			}
		}
		
		Chat prioritizedChat = this.parent_.getPrioritizedChat();
		if (prioritizedChat != null && !chatsToLeaveBe.contains(prioritizedChat) && !chatsToAdd.contains(prioritizedChat)) {
			if (chatsToAdd.size() > 0) {
				chatsToAdd.remove(chatsToAdd.size() - 1);
			} else {
				chatsToLeaveBe.remove(chatsToLeaveBe.size() - 1);
			}

			chatsToAdd.add(prioritizedChat);
		}
		
		// Replace ones which need to be swapped out
		for (int i = 0; i < maxThreads; i++) {
			if (chatsToAdd.size() <= 0) {
				break;
			}
			
			if (i >= this.threadsDisplayed_.size()) {
				this.threadsDisplayed_.add(chatsToAdd.remove(0));
			} else if (!chatsToLeaveBe.contains(this.threadsDisplayed_.get(i))) {
				this.threadsDisplayed_.set(i, chatsToAdd.remove(0));
			}
		}
		
		// Redisplay
		this.removeAll();
		
		if (numCols == 0) {
			return;
		}
		
		int numRows = (this.threadsDisplayed_.size() - 1)/numCols + 1;
		if (numRows < 1) {
			numRows = 1;
		}
		this.setLayout(new GridLayout(numRows, numCols));
		
		for (Chat chat : this.threadsDisplayed_) {
			this.add(chat);
		}
		
		this.revalidate();
		this.repaint();
	}

}
