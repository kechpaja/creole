package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.Message;
import utils.InsertionSortList;

public class ChatList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8408968042626016488L;
	
	private Conversation parent_;
	private InsertionSortList<Chat> chats_;
	private Map<String, Chat> chatMap_;
	private JPanel chatListPanel_;
	
	protected ChatList(Conversation conversation) {
		this.parent_ = conversation;
		this.chats_ = new InsertionSortList<Chat>();
		this.chatMap_ = new HashMap<String, Chat>();
		
		// Set up New Chat button
		this.add(new NewChatButton(this));
		
		this.chatListPanel_ = new JPanel();
		this.add(new JScrollPane(this.chatListPanel_));
		
		// List runs from top to bottom
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
	}
	
	protected Chat createNewChat() {
		Chat chat = new Chat(this);
		this.chats_.add(chat);
		this.chatMap_.put(chat.getId(), chat);
		this.parent_.redisplay();
		return chat;
	}
	
	protected InsertionSortList<Chat> getChats() {
		return this.chats_;
	}
	
	protected void redisplay() {
		this.chats_.sort();
		this.chatListPanel_.removeAll();
		this.chatListPanel_.setLayout(new GridBagLayout());
		
		for (Chat chat : this.chats_) {
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.weightx = 1.0;
			
			this.chatListPanel_.add(chat.getListEntry(), c);
		}
		
		this.validate();
	}
	
	protected String getConversationId() {
		return this.parent_.getId();
	}
	
	protected void deliver(Message message) {
		if (!this.chatMap_.containsKey(message.getId())) {
			this.createNewChat().deliver(message); // Unrecognized message indicates that a new chat has been started
			
			// TODO eventually, we'll have to check against a list of chats that have been deleted, and ignore if
			// it is one of those, but that's for the future. Or perhaps every message that can start a new chat 
			// should be marked as such. 
		}
		
		this.chatMap_.get(message.getId()).deliver(message);
	}
	
	protected void redisplayConversation() {
		this.parent_.redisplay();
	}

}
