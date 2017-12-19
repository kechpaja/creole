package ui;

import javax.swing.JTextField;

import resources.Strings;

public class ChatTitle extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6664989568652251767L;
	private static int count_;
	
	public ChatTitle(Chat chat) {
		this.setText(Strings.getDefaultChatTitle(ChatTitle.count_++));
		this.setEditable(false);
		this.addMouseListener(chat);
	}

}
