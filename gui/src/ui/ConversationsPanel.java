package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
		this.conversationMap_ = new ConcurrentHashMap<String, Conversation>();
	}
	
	
	public void deliver(List<Message> messages) {
		for (Message message : messages) {
			if (!this.conversationMap_.containsKey(message.getConversationId())) {
				this.createNewConversation(message.getConversationId()).deliver(message);
			} else {
				this.conversationMap_.get(message.getConversationId()).deliver(message);
			}
		}
	}
	
	
	// TODO we shouldn't have two methods with this much in common, but deal with fixing it later
	protected Conversation createNewConversation(String id) {
		Conversation conversation = new Conversation(id);
		this.conversations_.add(conversation);
		this.conversationMap_.put(conversation.getId(), conversation);
		this.addConversation(conversation);
		return conversation;
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
