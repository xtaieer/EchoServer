package xtaieer.network.echo;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class EchoClient {
    private static final int PORT = 11880;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket((String) null, PORT);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
            List<Byte> bytes = new ArrayList<Byte>();
            int r;
            for (;;) {
                bytes.clear();
                while ((r = System.in.read()) != '\n') {
                    outputStream.write(r);
                }
                outputStream.write(r);
                outputStream.flush();
                bytes.clear();
                while ((r = inputStream.read()) != '\n') {
                    bytes.add((byte) r);
                }
                bytes.add((byte) r);
                byte[] bs = new byte[bytes.size()];
                for (int i = 0; i < bytes.size(); i++) {
                    bs[i] = bytes.get(i);
                }
                System.out.println("echo clint " + new String(bs));
            }
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
