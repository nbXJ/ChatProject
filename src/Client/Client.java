package Client;

import javax.swing.*;
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
			receiver = new Receiver(reader);
        } catch (IOException e) {
			System.exit(-1);
        }
        return this;
    }

	public void closeSocket() {
		try {
			sendCmd("CloseSocket");
			if (receiver.running == true) {
				receiver.closeReceiver();
			}
			w.close();
			s.close();
		} catch (Exception e) {
			System.exit(-1);
		}
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

	public void receiverActivate(JTextArea log) {
		receiver.setTextArea(log);
        receiver.start();
    }

    class Receiver extends Thread{
        boolean running;
        BufferedReader reader;
		JTextArea log;
        Receiver(BufferedReader reader){
            this.reader = reader;
			running = false;
		}

		public void setTextArea(JTextArea log) {
			this.log = log;
        }
        @Override
        public void run(){
			running = true;
            try {
                while (running) {
                    String msg = reader.readLine();
                    if(msg.substring(0,2).equals("*$")){
                        continue;
                    }
					log.append(msg + "\n");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

		public void closeReceiver() throws IOException {
			reader.close();
			running = false;
			this.stop();
		}
    }
}
