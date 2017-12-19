package ui;

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
		
		this.add(this.title_);
		this.add(this.forkButton_);
	}
	
	public String getTitle() {
		return this.title_.getText();
	}
	
	public void setTitle(String title) {
		this.title_.setText(title);
	}

}
