package Client;

import Client.Client;

import javax.swing.*;

public class ChatFrame extends JFrame {
    private Client client;
    public void createChatroom(){
        this.setTitle("Chat room");
        this.setSize(400,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        components(new JPanel());
        client = new Client().createSocket("127.0.0.1");
        this.setVisible(true);
    }
    public void components(JPanel panel){

    }

}
