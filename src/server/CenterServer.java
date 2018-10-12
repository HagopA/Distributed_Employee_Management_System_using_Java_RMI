package server;

import java.io.IOException;
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

public class CenterServer<T> extends UnicastRemoteObject implements CenterServerInterface<T>
{

    private HashMap<String, ArrayList<Record>> records = new HashMap<>();
    private Logger logger;
    private FileHandler fh;
    private String serverName;
    private int recordCounts;

    public CenterServer() throws RemoteException
    {
        super();
        this.recordCounts = 0;
    }

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

    public synchronized String getRecordCounts()
    {
        return this.serverName + " " + this.recordCounts;
    }

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

    void log(String message, String server)
    {
        if(server == null)
        {
            this.logger.info(message);
        }
        else
        {
            this.serverName = server;
            this.logger = Logger.getLogger(serverName + "server.Server");
            try
            {
                this.fh = new FileHandler( "Server Logs\\" + server + "_server_log.txt", true);
                this.fh.setFormatter((new SimpleFormatter()));
                this.logger.addHandler(fh);
                this.logger.info(message);
            }
            catch (IOException e)
            {
                System.out.println("Error: " + e.getMessage() + ". The file could not been created");
            }
            catch (SecurityException e)
            {
                System.out.println("Error: " + e.getMessage() + ". The file could not been created");
            }
        }
    }
}
