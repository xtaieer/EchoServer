package xtaieer.network.echo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class EchoClient {
    private static final int PORT = 11880;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket((String) null, PORT);
            InputStream inputStream = new BufferedInputStream(socket.getInputStream());
            OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
            List<Byte> bytes = new ArrayList<Byte>();
            int r;
            for (;;) {
                bytes.clear();
                while ((r = System.in.read()) != '\n') {
                    bytes.add((byte) r);
                    outputStream.write(r);
                }
                bytes.add((byte) r);
                outputStream.write(r);
                outputStream.flush();

                byte[] readBytes = new byte[bytes.size()];
                for(int i = 0; i < bytes.size(); i ++) {
                    readBytes[i] = bytes.get(i);
                }

                bytes.clear();
                while ((r = inputStream.read()) != '\n') {
                    bytes.add((byte) r);
                }
                bytes.add((byte) r);
                byte[] bs = new byte[bytes.size()];
                for (int i = 0; i < bytes.size(); i++) {
                    bs[i] = bytes.get(i);
                }
                System.out.print(new String(bs));

                if("ShutDown\n".equals(new String(readBytes))) {
                    break;
                }
            }
        } catch(ConnectException e) {
            System.out.println("连接出错");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
}
