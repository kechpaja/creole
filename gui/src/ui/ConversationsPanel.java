package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

public class ConversationsPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7512355562992395347L;
	
	private List<Conversation> conversations_;
	
	protected ConversationsPanel() {
		conversations_ = new ArrayList<Conversation>();
	}
	
	
	
	protected void createNewConversation() {
		Conversation conversation = new Conversation();
		conversations_.add(conversation);
		this.addConversation(conversation);
	}
	
	
	/*
	 * Add a new conversation as a tab with title "New Conversation"
	 */
	private void addConversation(Conversation conversation) {
		this.addTab(conversation.getTitle(), conversation);
	}

}
