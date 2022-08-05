package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        int port = 8888;
        String hostName = "127.0.0.1";
        Scanner scanner = new Scanner(System.in);

        try {
            Socket socket = new Socket(hostName, port);  // 绑定客户端ip和端口号
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    while (true) {
                        System.out.println(in.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String userInput;
            System.out.println("请输入信息");
            while (!(userInput = scanner.nextLine()).equals("exit")) {
                out.println(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
