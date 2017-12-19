package ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ChatHeader extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7760685930390879542L;
	private ChatTitle title_;
	private ForkButton forkButton_;
	
	public ChatHeader(Chat chat) {
		this.title_ = new ChatTitle(chat);
		this.forkButton_ = new ForkButton(chat);
		
		this.setLayout(new BorderLayout());
		
		this.add(this.forkButton_, BorderLayout.LINE_START);
		this.add(this.title_, BorderLayout.CENTER);
		
		this.validate();
	}
	
	public String getTitle() {
		return this.title_.getText();
	}
	
	public void setTitle(String title) {
		this.title_.setText(title);
	}

}
