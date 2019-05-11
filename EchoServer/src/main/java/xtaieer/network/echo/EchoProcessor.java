package xtaieer.network.echo;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EchoProcessor implements Runnable {

    private Socket socket;

    public EchoProcessor(Socket socket) {
        this.socket = Objects.requireNonNull(socket);
    }

    @Override
    public void run() {
        if(socket.isClosed()) {
            throw new RuntimeException("socket is closed");
        }
        try {
            InputStream input = new BufferedInputStream(socket.getInputStream());
            OutputStream output = new BufferedOutputStream(socket.getOutputStream());
            int c;
            List<Byte> bytes = new ArrayList<>();
            while((c = input.read()) != -1) {
                bytes.add((byte) c);
                if(c == '\n') {
                    output.write(("Echo Server: " + getClientAddressString() + " ").getBytes());
                    byte[] bs = new byte[bytes.size()];
                    for(int i = 0; i < bytes.size(); i ++) {
                        output.write(bytes.get(i));
                        bs[i] = bytes.get(i);
                    }
                    output.flush();
                    bytes.clear();
                }
            }
            /*
            if(c == -1 && bytes.size() > 0) {

            }
            */
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                System.out.println(getClientAddressString() + " connect closed");
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getClientAddressString() {
        final String addressFormat = "%s:%s";
        return String.format(addressFormat, socket.getInetAddress(), socket.getPort());
    }
}
