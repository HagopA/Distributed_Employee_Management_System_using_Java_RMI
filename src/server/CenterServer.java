package server;

import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import records.EmployeeRecord;
import records.ManagerRecord;
import records.ProjectInfo;
import records.Record;

/**
 * Center server class implementation, implements the CenterServerInterface
 *
 * @author Hagop Awakian
 * Assignment 1
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class CenterServer<T> extends UnicastRemoteObject implements CenterServerInterface<T>
{
    /**
     * Data members
     */
    private HashMap<String, ArrayList<Record>> records = new HashMap<>();
    private Logger logger;
    private FileHandler fh;
    private String serverName;
    private int recordCounts;

    /**
     * Constructor
     * @throws RemoteException
     */
    public CenterServer() throws RemoteException
    {
        super();
        this.recordCounts = 0;
    }

    /**
     * Creates a manager record
     * @param firstName First name of the manager
     * @param lastName Last name of the manager
     * @param empId Employee ID of the manager
     * @param mailId Mail ID of the manager
     * @param project Project information of the manager
     * @param location Location of the manager
     * @return Returns the message to indicate that a manager record has successfully been created
     */
    public synchronized String createMRecord(String firstName, String lastName, int empId, String mailId, ProjectInfo project, String location)
    {
        Record aMRecord = new ManagerRecord(firstName, lastName, empId, mailId, project, location);
        String key = lastName.substring(0,1).toUpperCase();

        if(records.containsKey(key))
        {
            records.get(key).add(aMRecord);
        }
        else
        {
            ArrayList<Record> aList = new ArrayList<>();
            records.put(key, aList);
            records.get(key).add(aMRecord);
        }
        String message = "Successfully created the manager: \n" + aMRecord.toString();
        this.log(message, null);
        this.recordCounts++;
        return message;
    }

    /**
     * Creates an employee record
     * @param firstName First name of the employee
     * @param lastName Last name of the employee
     * @param empId Employee ID of the employee
     * @param mailId Mail ID of the employee
     * @param projectId Project ID of the employee
     * @return Returns the message to indicate that an employee record has successfully been created
     */
    public synchronized String createERecord(String firstName, String lastName, int empId, String mailId, String projectId)
    {
        Record aERecord = new EmployeeRecord(firstName, lastName, empId, mailId, projectId);
        String key = lastName.substring(0,1).toUpperCase();

        if(records.containsKey(key))
        {
            records.get(key).add(aERecord);
        }
        else
        {
            ArrayList<Record> aList = new ArrayList<>();
            records.put(key, aList);
            records.get(key).add(aERecord);
        }
        String message = "Successfully created the employee: " + aERecord.toString();
        this.log(message, null);
        this.recordCounts++;
        return message;
    }

    /**
     * Retrieves the number of records in each server using UDP/IP protocol
     * @return Returns a String indicating the number of records in each server
     */
    public synchronized String getRecordCounts()
    {
        String CARecords = "";
        String USRecords = "";
        String UKRecords = "";

        if(this.serverName.equalsIgnoreCase("CA"))
        {
            CARecords = this.getLocalCounts();

            try
            {
                DatagramSocket socket = new DatagramSocket();
                byte[] send = new byte[1024];
                byte[] receive = new byte[1024];
                InetAddress ipAddress = InetAddress.getByName("localhost");
                send = "".getBytes();

                /**
                 * Requesting US number of records
                 */
                DatagramPacket USsendPacket = new DatagramPacket(send, send.length, ipAddress, 2931);
                socket.send(USsendPacket);
                DatagramPacket USreceivePacket = new DatagramPacket(receive, receive.length);
                socket.receive(USreceivePacket);
                String USRecordCounts = new String(USreceivePacket.getData(), USreceivePacket.getOffset(), USreceivePacket.getLength());
                USRecords = USRecordCounts;

                /**
                 * Requesting UK number of records
                 */
                DatagramPacket UKsendPacket = new DatagramPacket(send, send.length, ipAddress, 2932);
                socket.send(UKsendPacket);
                DatagramPacket UKreceivePacket = new DatagramPacket(receive, receive.length);
                socket.receive(UKreceivePacket);
                String UKRecordCounts = new String(UKreceivePacket.getData(), UKreceivePacket.getOffset(), UKreceivePacket.getLength());
                UKRecords = UKRecordCounts;
            }
            catch (SocketException e)
            {
                e.printStackTrace();
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(this.serverName.equalsIgnoreCase("US"))
        {
            USRecords = this.getLocalCounts();

            try
            {
                DatagramSocket socket = new DatagramSocket();
                byte[] send = new byte[1024];
                byte[] receive = new byte[1024];
                InetAddress ipAddress = InetAddress.getByName("localhost");
                send = "".getBytes();

                /**
                 * Requesting CA number of records
                 */
                DatagramPacket CAsendPacket = new DatagramPacket(send, send.length, ipAddress, 2930);
                socket.send(CAsendPacket);
                DatagramPacket CAreceivePacket = new DatagramPacket(receive, receive.length);
                socket.receive(CAreceivePacket);
                String CARecordCounts = new String(CAreceivePacket.getData(), CAreceivePacket.getOffset(), CAreceivePacket.getLength());
                CARecords = CARecordCounts;

                /**
                 * Requesting UK number of records
                 */
                DatagramPacket UKsendPacket = new DatagramPacket(send, send.length, ipAddress, 2932);
                socket.send(UKsendPacket);
                DatagramPacket UKreceivePacket = new DatagramPacket(receive, receive.length);
                socket.receive(UKreceivePacket);
                String UKRecordCounts = new String(UKreceivePacket.getData(), UKreceivePacket.getOffset(), UKreceivePacket.getLength());
                UKRecords = UKRecordCounts;
            }
            catch (SocketException e)
            {
                e.printStackTrace();
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(this.serverName.equalsIgnoreCase("UK"))
        {
            UKRecords = this.getLocalCounts();

            try
            {
                DatagramSocket socket = new DatagramSocket();
                byte[] send = new byte[1024];
                byte[] receive = new byte[1024];
                InetAddress ipAddress = InetAddress.getByName("localhost");
                send = "".getBytes();

                /**
                 * Requesting CA number of records
                 */
                DatagramPacket CAsendPacket = new DatagramPacket(send, send.length, ipAddress, 2930);
                socket.send(CAsendPacket);
                DatagramPacket CAreceivePacket = new DatagramPacket(receive, receive.length);
                socket.receive(CAreceivePacket);
                String CARecordCounts = new String(CAreceivePacket.getData(), CAreceivePacket.getOffset(), CAreceivePacket.getLength());
                CARecords = CARecordCounts;

                /**
                 * Requesting US number of records
                 */
                DatagramPacket USsendPacket = new DatagramPacket(send, send.length, ipAddress, 2931);
                socket.send(USsendPacket);
                DatagramPacket USreceivePacket = new DatagramPacket(receive, receive.length);
                socket.receive(USreceivePacket);
                String USRecordCounts = new String(USreceivePacket.getData(), USreceivePacket.getOffset(), USreceivePacket.getLength());
                USRecords = USRecordCounts;
            }
            catch (SocketException e)
            {
                e.printStackTrace();
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return CARecords + " " + USRecords + " " + UKRecords;
    }

    /**
     * Used to get the number of records in the calling server
     * @return Returns the number of records in the server (object) that is calling it
     */
    public synchronized String getLocalCounts()
    {
        return this.serverName + " " + this.recordCounts;
    }

    /**
     * Will edit a field (variable) in a record given a record ID
     * @param recordId Record ID that an HR manager wishes to modify
     * @param fieldName Field name that an HR manager wishes to modify
     * @param newValue New value of the field
     * @return Returns a message, whether the modification was successful or not
     */
    public synchronized String editRecord(String recordId, String fieldName, T newValue)
    {
        if(recordId == null || fieldName == null || newValue == null)
        {
            String message = "Error: recordId, fieldName, or newValue cannot be null.";
            this.log(message, null);
            return message;
        }

        Set<Map.Entry<String, ArrayList<Record>>> recordsEntrySet = records.entrySet();

        for(Map.Entry<String, ArrayList<Record>> data : recordsEntrySet)
        {
            for(Record entry : data.getValue())
            {
                if(recordId.equals(entry.getRecordId()))
                {
                    if(entry.checkIfFieldExists(fieldName))
                    {
                        if(entry.getClass().getName().equals("records.ManagerRecord"))
                        {
                            switch (fieldName)
                            {
                                case "mailId":
                                    entry.setMailId(newValue.toString());
                                    break;
                                case "project":
                                    ((ManagerRecord) entry).setProject((ProjectInfo) newValue);
                                    break;
                                case "location":
                                    if(!isValidLocation(newValue.toString().toUpperCase()))
                                    {
                                        String message = "Error: " + newValue.toString().toUpperCase() + " is not a valid location.";
                                        this.log(message, null);
                                        return message;
                                    }
                                    ((ManagerRecord) entry).setLocation(newValue.toString().toUpperCase());
                                    break;
                                default:
                                    String message = "Error: the field name specified (" + fieldName + ") is invalid. No changes have been made";
                                    this.log(message, null);
                                    return message;
                            }
                        }
                        else if(entry.getClass().getName().equals("records.EmployeeRecord"))
                        {
                            switch (fieldName)
                            {
                                case "mailId":
                                    entry.setMailId(newValue.toString());
                                    break;
                                case "projectId":
                                    ((EmployeeRecord) entry).setProjectId(newValue.toString());
                                    break;
                                default:
                                    String message = "Error: it is not possible to modify the value of " + fieldName;
                                    this.log(message, null);
                                    return message;
                            }
                        }
                        String message = "Successfully modified record id: " + recordId + ". " + fieldName +
                                        " has been modified to " + newValue.toString();
                        this.log(message, null);
                        return message;
                    }
                    else
                    {
                        String message = "Error: Cannot modify a field that does not exist.";
                        this.log(message, null);
                        return message;
                    }
                }
            }
        }
        String message = "No records have been found with record ID: " + recordId + ". No changes have been made";
        this.log(message,null);
        return message;
    }

    /**
     * Validates the location when trying to modify to new value
     * @param s New location
     * @return Returns true if the new location is valid, false otherwise
     */
    private boolean isValidLocation(String s)
    {
        switch (s)
        {
            case "CA":
            case "US":
            case "UK":
                return true;
        }
        return false;
    }

    /**
     * Method to log information after update of a record
     * @param message The message to log
     * @param server Server that the logger is being called from. If the server is null, then simply log the message
     *               as the log file with the server name has already been created previously. If it is not null, then
     *               create the log file, name it after the server that is calling the method, and log the message.
     */
    void log(String message, String server)
    {
        /**
         * If the server is null, then it means that the file has already been created and therefore simply log the message
         */
        if(server == null)
        {
            this.logger.info(message);
        }
        /**
         * Otherwise, create the file and log the message
         */
        else
        {
            this.serverName = server;
            this.logger = Logger.getLogger(serverName + "server.Server");
            try
            {
                this.fh = new FileHandler( "Server Logs\\" + server + "_server_log.txt", true);
                this.fh.setFormatter((new SimpleFormatter()));
                this.logger.addHandler(fh);
                this.logger.setUseParentHandlers(false);
                this.logger.info(message);
            }
            catch (IOException e)
            {
                System.out.println("IO Error: " + e.getMessage() + ". The file could not been created");
            }
            catch (SecurityException e)
            {
                System.out.println("Security Error: " + e.getMessage() + ". The file could not been created");
            }
        }
    }
}
