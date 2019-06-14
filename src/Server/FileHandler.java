package Server;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    BufferedReader br;
    PrintWriter w;
    Scanner scan = new Scanner(System.in);

    public static FileHandler createOrRead(String path) throws IOException {
        File f = new File(path);
        f.createNewFile();
        return new FileHandler(new BufferedReader(new FileReader(f)),
                                new PrintWriter(new FileWriter(f, true)));
    }

    FileHandler(BufferedReader br, PrintWriter w) {
        this.br = br;
        this.w = w;
    }

    public ArrayList<String> extractRawUsers() throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    public ArrayList<User> extractUsers() throws Exception{
        System.out.println("Users: ");
        ArrayList<String> lines = extractRawUsers();
        ArrayList<User> users = new ArrayList<>();
        for(String l : lines){
            String[] entry = l.split("!");
            users.add(new User(entry[0], entry[1], entry[2]));
            System.out.println(entry[2]);
        }

        return users;
    }

    public void addRegistry(String user, String pass, String nick){
        w.println(user+"!"+pass+"!"+nick);
        w.flush();
    }

}
