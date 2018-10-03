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

public class CenterServer<T> extends UnicastRemoteObject implements CenterServerInterface<T>
{

    private HashMap<String, ArrayList<Record>> records = new HashMap<>();
    private Logger logger = Logger.getLogger("Server");
    private FileHandler fh;

    public CenterServer() throws RemoteException
    {
        super();
    }

    public String createMRecord(String firstName, String lastName, int empId, String mailId, ProjectInfo project, String location)
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
        return "Successfully created the manager.";
    }

    public String createERecord(String firstName, String lastName, int empId, String mailId, String projectId)
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
        return "Successfully created the employee.";
    }

    public String getRecordCounts()
    {
        return null;
    }

    public String editRecord(String recordId, String fieldName, T newValue)
    {
        if(recordId == null || fieldName == null || newValue == null)
        {
            return "Error: recordId, fieldName, or newValue cannot be null.";
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
                        if(entry.getClass().getName().equals("ManagerRecord"))
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
                                    if(!newValue.toString().equals("CA") || !newValue.toString().equals("US")
                                    || !newValue.toString().equals("UK"))
                                    {
                                        return "Error: " + newValue.toString() + " is not a valid location.";
                                    }
                                    ((ManagerRecord) entry).setLocation(newValue.toString());
                                    break;
                                default:
                                    return "Error: it is not possible to modify the value of " + fieldName;
                            }
                        }
                        else if(entry.getClass().getName().equals("EmployeeRecord"))
                        {
                            switch (fieldName)
                            {
                                case "mailId":
                                    entry.setMailId(newValue.toString());
                                    break;
                                case "projectId":
                                    ((EmployeeRecord) entry).setProjectId(newValue.toString());
                                default:
                                    return "Error: it is not possible to modify the value of " + fieldName;
                            }
                        }
                        return "Successfully modified record id: " + recordId + ". " + fieldName +
                                " has been modified to " + newValue.toString();
                    }
                    else
                    {
                        return "Error: Cannot modify a field that does not exist.";
                    }
                }
            }
        }
        return "No records have been found with record ID: " + recordId;
    }

    public void log(String server)
    {
        try
        {
            fh = new FileHandler(server + "_server_log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }
}
