package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import resources.Strings;

public class NewChatButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5491846971990840615L;
	private ChatList chatList_;
	
	public NewChatButton(ChatList chatList) {
		super(Strings.getNewChatButtonText());
		this.addActionListener(this);
		this.setActionCommand("new chat");
		this.chatList_ = chatList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("new chat")) {
			this.chatList_.createNewChat();
		}
	}

}
