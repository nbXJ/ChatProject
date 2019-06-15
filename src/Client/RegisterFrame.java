package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.*;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private Client client;


	public void createRegistration(Client c) {
		client = c;
		this.setTitle("Chat room");
		this.setSize(400, 400);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				client.closeSocket();
				System.exit(0);
			}
		});
		components(new JPanel());
		this.setVisible(true);
	}

	private void components(JPanel panel) {
		panel.setLayout(null);
		JLabel title = new JLabel("Fill your information!");
		title.setBounds(130, 20, 160, 100);
		panel.add(title);

		JLabel user = new JLabel("Username:");
		user.setBounds(60, 90, 90, 30);
		panel.add(user);

		JTextField text = new JTextField(15);
		text.setBounds(100, 90, 200, 30);
		text.setBackground(Color.LIGHT_GRAY);
		panel.add(text);

		JLabel password1 = new JLabel("Password:");
		password1.setBounds(30, 130, 90, 30);
		panel.add(password1);

		JPasswordField password2 = new JPasswordField(15);
		password2.setBounds(100, 130, 200, 30);
		password2.setBackground(Color.LIGHT_GRAY);
		panel.add(password2);

		JLabel nickName = new JLabel("Nickname:");
		nickName.setBounds(30, 170, 90, 30);
		panel.add(nickName);

		JTextField name = new JTextField(15);
		name.setBounds(100, 170, 200, 30);
		name.setBackground(Color.lightGray);
		panel.add(name);


		JButton confirmButton = new JButton("confirm");
		confirmButton.setBounds(100, 210, 80, 30);
		panel.add(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String u = text.getText();
				String p = new String(password2.getPassword());
				String n = name.getText();
				client.sendCmd("RegistrationRequest!" + u + "!" + p + "!" + n);
				RegisterFrame.this.setVisible(false);
				new ChatFrame().createChatFrame(client);
			}
		});

		this.add(panel);
	}


}
