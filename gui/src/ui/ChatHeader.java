package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import resources.Strings;

public class ChatHeader extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7760685930390879542L;
	private ForkButton forkButton_;
	private JComboBox<String> userSelectMenu_;
	private JTextField userList_;
	private Chat chat_;
	
	public ChatHeader(Chat chat) {
		this.forkButton_ = new ForkButton(chat);
		this.userSelectMenu_ = new JComboBox<String>();
		this.userList_ = new JTextField(); // TODO should be its own class, with access to chat and thus users and tags
		this.chat_ = chat;
		
		this.userList_.setEditable(false);
		
		this.setLayout(new BorderLayout());
		
		this.add(this.forkButton_, BorderLayout.LINE_START);
		this.add(this.userSelectMenu_, BorderLayout.CENTER);
		this.add(this.userList_, BorderLayout.PAGE_END);
		
		this.userSelectMenu_.addActionListener(this);
		
		this.updateUserSelectionList();
		this.updateUserList();
		
		this.validate();
	}
	
	public void updateUserList() {
		this.userList_.setText(String.join(", ", this.chat_.getUsersInChatSorted()));
	}
	
	// TODO eventually, this method will be called from an action listener, most likely. 
	public void updateUserSelectionList() {
		this.userSelectMenu_.removeAllItems();
		
		// TODO this is cheating. Learn to do it right. 
		this.userSelectMenu_.addItem(Strings.getAddUserLabel() + "\n");
		for (String user : this.chat_.getConversation().getUsersSorted()) {
			this.userSelectMenu_.addItem(user);
		}
	}
	
	public void redisplay() {
		this.updateUserList();
		this.updateUserSelectionList();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String userToAdd = (String) this.userSelectMenu_.getSelectedItem();
		if (userToAdd != null && !userToAdd.equals("") && !userToAdd.equals(Strings.getAddUserLabel() + "\n")) {
			this.chat_.addUser(userToAdd);
			this.userSelectMenu_.setSelectedIndex(0);
		}
	}

}
