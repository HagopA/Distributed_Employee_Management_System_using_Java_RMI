package UDP;

import server.CenterServerInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class USServerThread implements Runnable
{
    /**
     * Data members
     */
    private DatagramSocket socket;
    Registry registry;
    CenterServerInterface server;

    /**
     * Constructor
     * @param port Port number to locate the registry
     * @throws RemoteException
     * @throws NotBoundException
     * @throws SocketException
     */
    public USServerThread(int port) throws RemoteException, NotBoundException, SocketException
    {
        registry = LocateRegistry.getRegistry(port);
        server = (CenterServerInterface) registry.lookup("US");
        socket = new DatagramSocket(port);
    }

    /**
     * Run method for UDP packet transfers
     */
    public void run()
    {
        try
        {
            byte[] receive = new byte[1024];
            byte[] send;

            while(true)
            {
                DatagramPacket request = new DatagramPacket(receive, receive.length);
                socket.receive(request);
                send = (server.getLocalCounts()).getBytes();
                DatagramPacket reply = new DatagramPacket(send, send.length, request.getAddress(), request.getPort());
                socket.send(reply);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
