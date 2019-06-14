package Client;

import java.io.*;
import java.net.*;

public class Client {
    Socket s;
    Receiver receiver;
    PrintWriter w;
    BufferedReader reader;

    public Client createSocket(String ip){
        try {
            s = new Socket(ip,12000);
            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            w = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void sendMsg(String msg){
        w.println(msg);
        w.flush();
    }

    public void sendCmd(String cmd){
        w.println("*$" + cmd);
        w.flush();
    }

    public String readCmd(){
        try {
            String s = reader.readLine();
            return (s.substring(0,2).equals("*$"))? s.substring(2) : null;
        }catch (IOException e){
            return null;
        }
    }

    public void receiverActivate(){
        receiver = new Receiver(reader);
        receiver.start();
    }

    class Receiver extends Thread{
        boolean running;
        BufferedReader reader;
        Receiver(BufferedReader reader){
            this.reader = reader;
            running = true;
        }
        @Override
        public void run(){
            try {
                while (running) {
                    String msg = reader.readLine();
                    if(msg.substring(0,2).equals("*$")){
                        continue;
                    }
                    System.out.println(msg);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
