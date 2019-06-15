package Server;

import Server.Server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

//manage User info
class User{
	private PrintWriter w;
	private Handler h;
	private Server server;
	private Socket client;

	public boolean isLogin;
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

	public void login(Socket client, PrintWriter w, BufferedReader r, Server server) {
		isLogin = true;
		this.client = client;
		this.w = w;
		this.server = server;
		h = new Handler(r, this, this.server);
		h.start();
		server.sendAll("User Login: " + nickname);
		System.out.println("User Login: " + nickname);
	}


	public void logOut() throws Exception{
		isLogin = false;
		h.close();
		w.close();
		client.close();
		server.sendAll("User Logout: " + nickname);
		System.out.println("User Logout: " + nickname);
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
