package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTabbedPane;

import backend.Message;

public class ConversationsPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7512355562992395347L;
	
	private List<Conversation> conversations_;
	private Map<String, Conversation> conversationMap_;
	
	protected ConversationsPanel() {
		this.conversations_ = new ArrayList<Conversation>();
		this.conversationMap_ = new HashMap<String, Conversation>();
	}
	
	
	public void deliver(List<Message> messages) {
		for (Message message : messages) {
			if (!this.conversationMap_.containsKey(message.getConversationId())) {
				this.createNewConversation().deliver(message);
			}
			
			this.conversationMap_.get(message.getConversationId()).deliver(message);
		}
	}
	
	
	protected Conversation createNewConversation() {
		Conversation conversation = new Conversation();
		this.conversations_.add(conversation);
		this.conversationMap_.put(conversation.getId(), conversation);
		this.addConversation(conversation);
		return conversation;
	}
	
	
	/*
	 * Add a new conversation as a tab with title "New Conversation"
	 */
	private void addConversation(Conversation conversation) {
		this.addTab(conversation.getTitle(), conversation);
	}

}
