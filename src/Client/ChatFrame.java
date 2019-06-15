package Client;

import javax.swing.*;
import java.awt.event.*;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	private Client client;
	private JPanel mainPanel;
	private JTextArea textLog;
	private JTextField textEntry;
	private JButton send;

	public void createChatFrame(Client c) {
		client = c;
		client.receiverActivate(textLog);
		this.setSize(400, 600);
		this.setResizable(false);
		this.add(mainPanel);
		send.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				client.closeSocket();
				System.exit(0);
			}
		});
		this.setVisible(true);
	}

	//Button
	@Override
	public void actionPerformed(ActionEvent e) {
		send();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			send();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	private void send() {
		if (!textEntry.getText().isEmpty()) {
			client.sendMsg(textEntry.getText());
			textEntry.setText("");
		}
	}
}
