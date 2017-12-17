package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import resources.Strings;

public class ThreadList extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8408968042626016488L;
	
	private Conversation parent_;
	private PriorityBlockingQueue<Thread> threads_; // TODO replace with priority queue
	
	protected ThreadList(Conversation conversation) {
		this.parent_ = conversation;
		this.threads_ = new PriorityBlockingQueue<Thread>();
		
		// Set up New Thread button
		JButton newThreadButton = new JButton(Strings.getNewThreadButtonText());
		newThreadButton.addActionListener(this);
		newThreadButton.setActionCommand("new thread");
		this.add(newThreadButton);
		
		
		// List runs from top to bottom
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("new thread")) {
			this.threads_.add(new Thread());
			this.parent_.redisplay();
		}
	}
	
	protected void addThread(Thread thread) {
		this.threads_.add(thread);
		this.redisplay();
	}
	
	protected PriorityBlockingQueue<Thread> getThreads() {
		return this.threads_;
	}
	
	protected void redisplay() {
		for (Thread thread : this.threads_) {
			// TODO maybe a JScrollPane? 
		}
	}

}
