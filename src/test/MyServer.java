package test;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MyServer
{
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;

    public MyServer(int port, ClientHandler ch)
    {
        this.ch=ch;
        this.port=port;
        stop= false;
    }
    public void start()
    {
        new Thread(() -> {
            try {
                runServer();
            } catch (Exception e) {
                // Handle the exception
                e.printStackTrace();
            }
        }).start();
    }
    private void runServer() throws Exception
    {
        ServerSocket server= new ServerSocket(port);
        server.setSoTimeout(1000);
        while (!stop) {
            try {
                Socket aClient = server.accept(); // blocking call
                try {
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                } catch (IOException e) {
                    // handle any exceptions related to handling the client
                    e.printStackTrace();
                }
                finally {
                    // Close the streams and the client socket
                    try {
                        aClient.getInputStream().close();
                    } catch (IOException e) {
                        // handle any exceptions related to closing the input stream
                        e.printStackTrace();
                    }
                    try {
                        aClient.getOutputStream().close();
                    } catch (IOException e) {
                        // handle any exceptions related to closing the output stream
                        e.printStackTrace();
                    }
                    try {
                        aClient.close();
                    } catch (IOException e) {
                        // handle any exceptions related to closing the client socket
                        e.printStackTrace();
                    }
                }
            } catch (SocketTimeoutException e) {
                // handle the case when no client connects within the timeout period
                e.printStackTrace();
            }
        }
        server.close();
    }
    public void close()
    {
        stop=true; // stop the server
    }
	
}
