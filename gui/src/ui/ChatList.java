package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.Message;
import backend.SessionManager;

public class ChatList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8408968042626016488L;
	
	private ChatPanel parent_;
	private Vector<Chat> chats_;
	private Map<String, Chat> chatMap_;
	private JPanel chatListPanel_;
	private Chat prioritizedChat_;
	
	protected ChatList(ChatPanel chatPanel) {
		this.parent_ = chatPanel;
		this.chats_ = new Vector<Chat>();
		this.chatMap_ = new ConcurrentHashMap<String, Chat>();
		
		// Set up New Chat button
		this.add(new NewChatButton(this));
		
		this.chatListPanel_ = new JPanel();
		this.add(new JScrollPane(this.chatListPanel_));
		
		// List runs from top to bottom
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
	}
	
	protected Chat createNewChat(String id) {
		Chat chat = new Chat(this, id);
		this.chats_.add(chat);
		this.chatMap_.put(chat.getId(), chat);
		this.parent_.redisplay();
		return chat;
	}
	
	protected Chat createNewChat() {
		return createNewChat(SessionManager.getCurrentUser() + "-" + System.currentTimeMillis() + "-" + SessionManager.getSessionId());
	}
	
	protected Vector<Chat> getChats() {
		return this.chats_;
	}
	
	protected void redisplay() {
		this.chatListPanel_.removeAll();
		this.chatListPanel_.setLayout(new GridBagLayout());
		
		for (Chat chat : this.chats_) {
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.weightx = 1.0;
			
			this.chatListPanel_.add(chat.getListEntry(), c);
			
			chat.getListEntry().redisplay();
		}
		
		this.validate();
	}
	
	protected ChatPanel getChatPanel() {
		return this.parent_;
	}
	
	public void deliver(Message message) {
		if (!this.chatMap_.containsKey(message.getChatId())) {
			this.createNewChat(message.getChatId()).deliver(message); // Unrecognized message indicates that a new chat has been started
			
			// TODO eventually, we'll have to check against a list of chats that have been deleted, and ignore if
			// it is one of those, but that's for the future. Or perhaps every message that can start a new chat 
			// should be marked as such. 
		} else {
			this.chatMap_.get(message.getChatId()).deliver(message);
		}
	}
	
	protected void redisplayAll() {
		this.parent_.redisplay();
	}
	
	protected void setPrioritizedChat(Chat chat) {
		this.prioritizedChat_= chat;
	}
	
	protected Chat getPrioritizedChat() {
		return this.prioritizedChat_;
	}
	
	protected void bumpToFront(Chat chat) {
		this.chats_.remove(chat);
		this.chats_.add(chat);
	}

}
