package UDP;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import records.EmployeeRecord;
import records.ManagerRecord;
import server.centerServerInterfaceIDL.CenterServerInterfaceHelper;
import server.centerServerInterfaceIDL.CenterServerInterfaceOperations;

/**
 * US thread that will handle UDP requests
 *
 * @author Hagop Awakian
 * Assignment 2
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class USServerThread implements Runnable, Serializable
{
    /**
     * Data members
     */
    private DatagramSocket socket;
    CenterServerInterfaceOperations server;

    /**
     * Constructor
     * @param port Port number to locate the registry
     * @throws SocketException
     */
    public USServerThread(int port, ORB orb) throws Exception
    {
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        String name = "US";
        server = CenterServerInterfaceHelper.narrow(ncRef.resolve_str(name));
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
            byte[] send = null;
            String methodToInvoke = "";

            while (true)
            {
                DatagramPacket request = new DatagramPacket(receive, receive.length);
                socket.receive(request);
                methodToInvoke = "";

                for (int i = 0; i < request.getLength(); i++)
                {
                    methodToInvoke += Character.toString((char) request.getData()[i]);
                }

                if (methodToInvoke.equalsIgnoreCase("getRecordCounts"))
                {
                    send = (server.getLocalCounts()).getBytes();
                } else if (methodToInvoke.equalsIgnoreCase("searchIfRecordExists:"))
                {
                    send = Boolean.toString(server.searchIfRecordExists(methodToInvoke.substring(22))).getBytes();
                } else
                {
                    ByteArrayInputStream in = new ByteArrayInputStream(receive);
                    ObjectInputStream iStream = new ObjectInputStream(in);
                    Object o = iStream.readObject();

                    if (o instanceof ManagerRecord)
                    {
                        ManagerRecord newRecord = (ManagerRecord) o;
                        send = (server.createMRecord("", newRecord.getFirstName(), newRecord.getLastName(),
                                newRecord.getEmpId(), newRecord.getMailId(), newRecord.getProject(),
                                newRecord.getLocation()).getBytes());
                    } else if (o instanceof EmployeeRecord)
                    {
                        EmployeeRecord newRecord = (EmployeeRecord) o;
                        send = (server.createERecord("", newRecord.getFirstName(), newRecord.getLastName(),
                                newRecord.getEmpId(), newRecord.getMailId(), newRecord.getProjectId())).getBytes();
                    }

                    iStream.close();
                }
                DatagramPacket reply = new DatagramPacket(send, send.length, request.getAddress(), request.getPort());
                socket.send(reply);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
