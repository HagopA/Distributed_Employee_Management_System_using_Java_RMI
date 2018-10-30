package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.omg.CORBA.ORB;
import records.*;
import server.centerServerInterfaceIDL.*;

/**
 * Center server class implementation, implements the CenterServerInterface
 *
 * @author Hagop Awakian
 * Assignment 2
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class CenterServer extends CenterServerInterfacePOA implements Serializable
{
    /**
     * Data members
     */
    private HashMap<String, ArrayList<Record>> records = new HashMap<>();
    private Logger logger;
    private FileHandler fh;
    private String serverName;
    private int recordCounts;
    private ORB orb;

    /**
     * Constructor
     */
    public CenterServer()
    {
        super();
        this.recordCounts = 0;
    }

    /**
     * Creates a manager record
     * @param managerId Manager ID of the HR manager
     * @param firstName First name of the manager
     * @param lastName Last name of the manager
     * @param empId Employee ID of the manager
     * @param mailId Mail ID of the manager
     * @param project Project information of the manager
     * @param location Location of the manager
     * @return Returns the message to indicate that a manager record has successfully been created
     */
    public synchronized String createMRecord(String managerId, String firstName, String lastName, int empId,
                                             String mailId, server.centerServerInterfaceIDL.ProjectInfo project,
                                             String location)
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
        String message = "Successfully created the manager: \n" + aMRecord.toString() + "\n By manager ID: " + managerId;
        this.log(message, null);
        this.recordCounts++;
        return message;
    }

    /**
     * Creates an employee record
     * @param managerId Manager ID of the HR manager
     * @param firstName First name of the employee
     * @param lastName Last name of the employee
     * @param empId Employee ID of the employee
     * @param mailId Mail ID of the employee
     * @param projectId Project ID of the employee
     * @return Returns the message to indicate that an employee record has successfully been created
     */
    public synchronized String createERecord(String managerId, String firstName, String lastName, int empId, String mailId, String projectId)
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
        String message = "Successfully created the employee: " + aERecord.toString() + "\n By manager ID: " + managerId;
        this.log(message, null);
        this.recordCounts++;
        return message;
    }

    /**
     * Retrieves the number of records in each server using UDP/IP protocol
     * @param managerId Manager ID of the HR manager
     * @return Returns a String indicating the number of records in each server
     */
    public synchronized String getRecordCounts(String managerId)
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
                send = "getRecordCounts".getBytes();

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
                send = "getRecordCounts".getBytes();

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
                send = "getRecordCounts".getBytes();

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
        this.log("Manager ID: " + managerId + " requested to get the record counts." +
                CARecords + " " + USRecords + " " + UKRecords, null);
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
     * @param managerId Manager ID of the HR manager
     * @param recordId Record ID that an HR manager wishes to modify
     * @param fieldName Field name that an HR manager wishes to modify
     * @param newValue New value of the field
     * @return Returns a message, whether the modification was successful or not
     */
    public synchronized String editRecord(String managerId, String recordId, String fieldName, String newValue)
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
                                    entry.setMailId(newValue);
                                    break;
                                case "projectId":
                                    ((ManagerRecord) entry).setProjectId(newValue);
                                    break;
                                case "clientName":
                                    ((ManagerRecord) entry).setProjectClientName(newValue);
                                    break;
                                case "projectName":
                                    ((ManagerRecord) entry).setProjectName(newValue);
                                case "location":
                                    if(!isValidLocation(newValue.toUpperCase()))
                                    {
                                        String message = "Error: " + newValue.toUpperCase() + " is not a valid location.";
                                        this.log(message, null);
                                        return message;
                                    }
                                    ((ManagerRecord) entry).setLocation(newValue.toUpperCase());
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
                                    entry.setMailId(newValue);
                                    break;
                                case "projectId":
                                    ((EmployeeRecord) entry).setProjectId(newValue);
                                    break;
                                default:
                                    String message = "Error: it is not possible to modify the value of " + fieldName;
                                    this.log(message, null);
                                    return message;
                            }
                        }
                        String message = "Successfully modified record id: " + recordId + ". " + fieldName +
                                        " has been modified to " + newValue + " by manager ID: " + managerId;
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
     * Transfers one record from a server to the other using UDP
     * @param managerId Manager ID of the HR manager
     * @param recordId Record ID of the record that the HR manager wishes to transfer
     * @param remoteCenterServerName Server name where manager will transfer the record
     * @return Returns a message to say whether the operation was successful or not
     */
    public synchronized String transferRecords(String managerId, String recordId, String remoteCenterServerName)
    {
        int portNum = 0;

        if(!searchIfRecordExists(recordId))
        {
            String message = "Error: record ID " + recordId + " does not exist in your server.";
            this.log(message, null);
            return message;
        }

        try
        {
            if(remoteCenterServerName.equalsIgnoreCase("CA"))
            {
                portNum = 2930;
            }
            else if(remoteCenterServerName.equalsIgnoreCase("US"))
            {
                portNum = 2931;
            }
            else if(remoteCenterServerName.equalsIgnoreCase("UK"))
            {
                portNum = 2932;
            }
            else
            {
                String message = "Error: Server name " + remoteCenterServerName + " is invalid.";
                this.log(message, null);
                return message;
            }

            DatagramSocket socket = new DatagramSocket();
            byte[] send = new byte[1024];
            byte[] receive = new byte[1024];
            InetAddress ipAddress = InetAddress.getByName("localhost");
            send = ("searchIfRecordExists:" + recordId).getBytes();

            DatagramPacket sendPacket = new DatagramPacket(send, send.length, ipAddress, portNum);
            socket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());

            if(response.equalsIgnoreCase("true"))
            {
                String message = "Error: record ID " + recordId + " already exists in server " + remoteCenterServerName;
                this.log(message, null);
                return message;
            }

            else
            {
                Set<Map.Entry<String, ArrayList<Record>>> recordsEntrySet = records.entrySet();
                boolean deleteRecord = false;

                for(Map.Entry<String, ArrayList<Record>> data : recordsEntrySet)
                {
                    int counter = 0;
                    for (Record entry : data.getValue())
                    {
                        counter++;
                        if(entry.getRecordId().equalsIgnoreCase(recordId))
                        {
                            if(recordId.substring(0,2).equalsIgnoreCase("MR"))
                            {
                                ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
                                ObjectOutputStream oOutputStream = new ObjectOutputStream(bOutputStream);

                                String firstName = entry.getFirstName();
                                String lastName = entry.getLastName();
                                int empId = entry.getEmpId();
                                String mailId = entry.getMailId();
                                ProjectInfo project = ((ManagerRecord) entry).getProject();
                                String location = ((ManagerRecord) entry).getLocation();

                                ManagerRecord sendRecord = new ManagerRecord(firstName, lastName, empId, mailId, project, location);
                                oOutputStream.writeObject(sendRecord);
                                byte[] outputData = bOutputStream.toByteArray();

                                sendPacket = new DatagramPacket(outputData, outputData.length, ipAddress, portNum);
                                socket.send(sendPacket);
                                receivePacket = new DatagramPacket(receive, receive.length);
                                socket.receive(receivePacket);

                            }
                            else if(recordId.substring(0,2).equalsIgnoreCase("ER"))
                            {
                                ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
                                ObjectOutputStream oOutputStream = new ObjectOutputStream(bOutputStream);

                                String firstName = entry.getFirstName();
                                String lastName = entry.getLastName();
                                int empId = entry.getEmpId();
                                String mailId = entry.getMailId();
                                String projectId = ((EmployeeRecord) entry).getProjectId();

                                EmployeeRecord sendRecord = new EmployeeRecord(firstName, lastName, empId, mailId, projectId);
                                oOutputStream.writeObject(sendRecord);
                                byte[] outputData = bOutputStream.toByteArray();

                                sendPacket = new DatagramPacket(outputData, outputData.length, ipAddress, portNum);
                                socket.send(sendPacket);
                                receivePacket = new DatagramPacket(receive, receive.length);
                                socket.receive(receivePacket);
                                oOutputStream.close();
                            }
                            deleteRecord = true;
                            break;
                        }
                    }
                    if(deleteRecord)
                    {
                        data.getValue().remove(counter);
                        String message = "Successfully transferred record ID " + recordId + " from " + serverName +
                                            " to " + remoteCenterServerName + " by manager ID " + managerId;
                        this.log(message, null);
                        return message;
                    }
                }
            }
        }
        catch (Exception e)
        {

        }
        String message = "Unexpected error while attempting to transfer records. Action has not been completed.";
        this.log(message, null);
        return message;
    }

    /**
     * Searches if a given record exists in the server (the server that is being called from)
     * @param recordId The record ID to search for
     * @return Returns true if the record exists, otherwise returns false
     */
    public boolean searchIfRecordExists(String recordId)
    {
        Set<Map.Entry<String, ArrayList<Record>>> recordsEntrySet = records.entrySet();
        boolean recordExists = false;

        for(Map.Entry<String, ArrayList<Record>> data : recordsEntrySet)
        {
            for (Record entry : data.getValue())
            {
                if(entry.getRecordId().equalsIgnoreCase(recordId))
                {
                    recordExists = true;
                }
            }
            if(recordExists)
            {
                break;
            }
        }
        return recordExists;
    }

    /**
     * Setter for the ORB object
     * @param orb
     */
    public void setORB(ORB orb)
    {
        this.orb = orb;
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
