package ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Thread extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6053838067760788383L;
	private String title_;
	private int count_ = 0;
	private JTextArea chatArea_;
	private JTextArea typingArea_;
	
	protected Thread() {
		// TODO get the title from some better place
		this.title_ = "Thread " + (count_++);
		
		
		// TODO text area with convo; typing area for typing
		// TODO advanced: user list; clone button
		
		this.chatArea_ = new JTextArea(5, 10);
		this.chatArea_.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));// TODO tweak color and thickness?
		this.chatArea_.setEditable(false);
		this.add(this.chatArea_);
		
		this.typingArea_ = new JTextArea(5, 10); 
		this.typingArea_.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));// TODO tweak color and thickness?
		this.typingArea_.setEditable(true);
		this.typingArea_.addKeyListener(this);
		this.add(this.typingArea_);
		
		
		// TODO probably need to set a layout as well
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.typingArea_.setText("");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.chatArea_.append("username: " + this.typingArea_.getText() + "\n");
		}
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	protected String getTitle() {
		return this.title_;
	}
	
	protected int timeSinceLastActivity() {
		return 0; // TODO
	}

}
