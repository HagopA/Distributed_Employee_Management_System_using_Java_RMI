package UDP;

import client.UKimport.CenterServerInterface;
import client.UKimport.CenterServerService;
import client.UKimport.ProjectInfo;
import records.EmployeeRecord;
import records.ManagerRecord;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UK thread that will handle UDP requests
 *
 * @author Hagop Awakian
 * Assignment 3
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class UKServerThread implements Runnable
{
    /**
     * Data members
     */
    private DatagramSocket socket;
    CenterServerService service;
    CenterServerInterface server;

    /**
     * Constructor
     * @param port Port number to locate the registry
     * @throws SocketException
     */
    public UKServerThread(int port) throws Exception
    {
        this.service = new client.UKimport.CenterServerService();
        this.server = service.getCenterServerPort();
        this.socket = new DatagramSocket(port);
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
                }
                else if (methodToInvoke.equalsIgnoreCase("searchIfRecordExists:"))
                {
                    send = Boolean.toString(server.searchIfRecordExists(methodToInvoke.substring(22))).getBytes();
                }
                else
                {
                    byte [] data = request.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(data);
                    ObjectInputStream iStream = new ObjectInputStream(in);
                    Object o = iStream.readObject();

                    if (o instanceof ManagerRecord)
                    {
                        ManagerRecord newRecord = (ManagerRecord) o;
                        ProjectInfo project = new ProjectInfo();
                        project.setClientName(newRecord.getClientName());
                        project.setProjectId(newRecord.getProjectId());
                        project.setProjectName(newRecord.getProjectName());

                        send = (server.createMRecord("", newRecord.getFirstName(), newRecord.getLastName(),
                                newRecord.getEmpId(), newRecord.getMailId(), project,
                                newRecord.getLocation()).getBytes());
                    }
                    else if (o instanceof EmployeeRecord)
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
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
