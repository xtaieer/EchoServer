package xtaieer.network.echo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {

    private static final int PORT = 11880;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            for (;;) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = new BufferedInputStream(socket.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
                try {
                    int b;
                    List<Byte> readBytes = new ArrayList<>();
                    while ((b = inputStream.read()) != -1) {
                        readBytes.add((byte) b);
                        if ((char) b == '\n') {
                            for (int i = 0; i < readBytes.size(); i++) {
                                outputStream.write(readBytes.get(i));
                            }
                            outputStream.flush();
                            readBytes.clear();
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
