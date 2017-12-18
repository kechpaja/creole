package ui;

import javax.swing.JTextArea;

public class ChatTypingArea extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6995004424786514497L;
	
	public ChatTypingArea(Chat chat) {
		super(5, 3);
		this.setEditable(true);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.addKeyListener(chat);
		this.addFocusListener(chat);
	}

}
