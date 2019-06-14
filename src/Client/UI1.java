package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;

public class UI1 extends JFrame {
    public static void main(String[] args) {
        new UI1().createWindowLogin();

    }

    private Client client;
    private Frame frame;





    public void createWindowLogin(){
        this.setTitle("Chat Login");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        components(new JPanel());
        client = new Client().createSocket("127.0.0.1");
        this.setVisible(true);
    }





    private void components(JPanel panel){
        panel.setLayout(null);

        JLabel title = new JLabel("Welcome to the chat!");
        title.setBounds(130, 20, 160, 100);
        panel.add(title);

        JLabel user = new JLabel("User:");
        user.setBounds(60, 90, 90 ,30);
        panel.add(user);

        JTextField text = new JTextField(15);
        text.setBounds(100, 90, 200, 30);
        text.setBackground(Color.lightGray);
        panel.add(text);

        JLabel password1 = new JLabel("Password:");
        password1.setBounds(30,130, 90, 30);
        panel.add(password1);

        JPasswordField password2 = new JPasswordField(15);
        password2.setBounds(100, 130, 200, 30);
        password2.setBackground(Color.lightGray);
        panel.add(password2);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(100, 170, 80,30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = text.getText();
                String pass = new String(password2.getPassword());
                client.sendCmd("LoginRequest!" + user + "!" + pass + "!");
                String response = client.readCmd();
                if(response != null && response.equals("LoginGranted")){
                    //create new window
                    UI1.this.setVisible(false);
                    System.out.println("Success");

                }else{
                    //open warning and clear the password field
                    JOptionPane.showMessageDialog(frame,
                            "Incorrect username or password.",
                            "Warning!",
                            JOptionPane.WARNING_MESSAGE);
                    password2.setText("");
                }
            }
        });
        panel.add(loginButton);

        JButton register = new JButton("Crate new account");
        register.setBounds(100, 200, 160, 30);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            UI1.this.setVisible(false);
            new UI2().createRegistration();

            }
        });
        panel.add(register);

        this.add(panel);
    }




}
