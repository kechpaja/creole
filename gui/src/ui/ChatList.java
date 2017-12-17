package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.Message;
import backend.Sender;
import resources.Strings;
import utils.InsertionSortList;

public class ChatList extends JPanel implements ActionListener {

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
		
		// Set up New Thread button
		JButton newThreadButton = new JButton(Strings.getNewThreadButtonText());
		newThreadButton.addActionListener(this);
		newThreadButton.setActionCommand("new thread");
		this.add(newThreadButton);
		
		this.chatListPanel_ = new JPanel();
		this.add(new JScrollPane(this.chatListPanel_));
		
		// List runs from top to bottom
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("new thread")) {
			this.addThread(new Chat(this));
		}
	}
	
	protected void addThread(Chat thread) {
		this.chats_.add(thread);
		this.chatMap_.put(thread.getId(), thread);
		this.parent_.redisplay();
	}
	
	protected InsertionSortList<Chat> getChats() {
		return this.chats_;
	}
	
	protected void redisplay() {
		this.chatListPanel_.removeAll();
		this.chatListPanel_.setLayout(new GridBagLayout());
		
		for (Chat thread : this.chats_) {
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.weightx = 1.0;
			
			this.chatListPanel_.add(thread.getListEntry(), c);
		}
		
		this.validate();
	}
	
	protected String getConversationId() {
		return this.parent_.getId();
	}
	
	protected Sender getSender() {
		return this.parent_.getSender();
	}
	
	protected void deliver(Message message) {
		// TODO 
	}

}
