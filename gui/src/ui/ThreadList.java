package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import resources.Strings;
import utils.InsertionSortList;

public class ThreadList extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8408968042626016488L;
	
	private Conversation parent_;
	private InsertionSortList<Thread> threads_;
	private JPanel threadListPanel_;
	
	protected ThreadList(Conversation conversation) {
		this.parent_ = conversation;
		this.threads_ = new InsertionSortList<Thread>();
		
		// Set up New Thread button
		JButton newThreadButton = new JButton(Strings.getNewThreadButtonText());
		newThreadButton.addActionListener(this);
		newThreadButton.setActionCommand("new thread");
		this.add(newThreadButton);
		
		this.threadListPanel_ = new JPanel();
		this.threadListPanel_.setLayout(new BoxLayout(this.threadListPanel_, BoxLayout.Y_AXIS));
		this.add(new JScrollPane(this.threadListPanel_));
		
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
	
	protected InsertionSortList<Thread> getThreads() {
		return this.threads_;
	}
	
	protected void redisplay() {
		this.threadListPanel_.removeAll();
		
		for (Thread thread : this.threads_) {
			this.threadListPanel_.add(thread.getListEntry());
		}
		
		this.validate();
	}

}
