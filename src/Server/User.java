package Server;

import Server.Server;

import java.io.BufferedReader;
import java.io.PrintWriter;

class User{
    private PrintWriter w;
    private Handler h;
    private Server server;

    private boolean isLogin;
    private final String username;
    private final String password;
    private final String nickname;

    User(String u, String p, String n){
        username = u;
        password = p;
        nickname = n;
        isLogin = false;
    }

    public String transformMsg(String msg){
        return nickname + ": " + msg;
    }

    public void sendToUser(String msg){
        w.println(msg);
        w.flush();
    }

    public void login(PrintWriter w, BufferedReader r, Server server){
        isLogin = true;
        this.w = w;
        this.server = server;
        h = new Handler(r, this, this.server);
        h.start();
        System.out.println("Server.User Login: " + nickname);
    }

    public void logOut() throws Exception{
        h.close();
        w.close();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
