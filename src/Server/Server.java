package Server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
public class Server {
    public static void main(String[] args) {
        new Server().serverSocket();
    }
    ArrayList<User> users;
    ServerSocket ss;
    FileHandler userFile;

    public void serverSocket(){
        System.out.println(System.getProperty("user.dir"));
        try {
            userFile = FileHandler.createOrRead(System.getProperty("user.dir") + "/UsersInformation.txt");
            users = userFile.extractUsers();
            ss = new ServerSocket(12000);
            System.out.println("Run server....");
            while(true) {
                Socket s = ss.accept();
                PrintWriter w = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                //loginprocess thread to login
                LoginProcess process = new LoginProcess(s, this, users, br, w, userFile);
                process.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAll(String msg){
        for(User u : users){
            if (u.isLogin) {
                u.sendToUser(msg);
            }
        }
    }
}

class LoginProcess extends Thread{
    Server server;
    Socket client;
    ArrayList<User> users;
    BufferedReader br;
    PrintWriter w;
    FileHandler f;

    LoginProcess(Socket client, Server server, ArrayList<User> users, BufferedReader br, PrintWriter w, FileHandler f) {
        this.server = server;
        this.br = br;
        this.w = w;
        this.users = users;
        this.f = f;
        this.client = client;
    }

    @Override
    public void run(){
        try{
            String line;
            while (true){
                line = br.readLine().substring(2);
                if (line.equals("CloseSocket")) {
                    w.close();
                    br.close();
                    client.close();
                }
                String[] entry = line.split("!");
                if(entry.length < 3){
                    w.println("*$GUN");
                    w.flush();
                    continue;
                }
                if(entry[0].equals("LoginRequest")) {
                    String user = entry[1];
                    String pass = entry[2];
                    for (User u : users) {
                        if (u.getUsername().equals(user) &&
                                u.getPassword().equals(pass)) {
                            u.login(client, w, br, server);
                            w.println("*$LoginGranted");
                            w.flush();
                            this.stop();
                        }
                    }
                }
                if(entry[0].equals("RegistrationRequest")){
                    String u = entry[1];
                    String p = entry[2];
                    String n = entry[3];
                    System.out.println("User Registry: " + u + " " + p + " " + n);
                    f.addRegistry(u,p,n);
                    User user = new User(u, p, n);
                    user.login(client, w, br, server);
                    this.stop();
                }
            }
        }catch (Exception e){
            //fill
            e.printStackTrace();
        }
    }

}

class Handler extends Thread{
    Server server;
    BufferedReader br;
    User user;
    boolean running;
    Handler(BufferedReader reader, User u, Server server){
        br = reader;
        user = u;
        this.server = server;
        running = true;
    }
    @Override
    public void run(){
        try{
            while(running){
                String msg = br.readLine();
                if(msg.substring(0, 2).equals("*$")){
                    String cmd = msg.substring(2);
                    if (cmd.equals("CloseSocket")) {
                        user.logOut();
                    }
                    continue;
                }
                server.sendAll(user.transformMsg(msg));
            }
        }catch (Exception e){
            //fill
            e.printStackTrace();
        }
    }

    public void close() throws IOException{
        br.close();
        running = false;
    }
}