package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        List<Socket> userSocketArray = new ArrayList<>();
        int port = 8888; // 设置服务器端口
        try {
            ServerSocket serverSocket = new ServerSocket(port); // 创建服务器套接字并绑定端口
            while (true) {
                Socket client = serverSocket.accept();
                userSocketArray.add(client);
                new Thread(() -> {
                    System.out.println(client.getInetAddress().getHostName() + ":" + client.getPort() + "已加入聊天室");
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                        String userIn;
                        while (!(userIn = in.readLine()).equals("quit!!")) {
                            for (Socket socket : userSocketArray) {
                                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                                printWriter.println(socket.getInetAddress().getHostName() + ":" + socket.getPort() + " " + userIn);
                            }

                        }
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
