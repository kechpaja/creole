package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import backend.Message;
import backend.Sender;

public class ConversationsPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7512355562992395347L;
	
	private List<Conversation> conversations_;
	private Sender sender_;
	
	protected ConversationsPanel(Sender sender) {
		this.conversations_ = new ArrayList<Conversation>();
		this.sender_ = sender;
	}
	
	
	public void deliver(List<Message> messages) {
		for (Message message : messages) {
			// TODO determine conversation ID
			
			// TODO deliver message to correct conversation
		}
	}
	
	
	protected void createNewConversation() {
		Conversation conversation = new Conversation(this.sender_);
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
