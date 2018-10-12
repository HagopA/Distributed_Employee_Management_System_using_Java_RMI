package server;

import java.rmi.*;
import records.ProjectInfo;

public interface CenterServerInterface<T> extends Remote
{
    String createMRecord(String firstName, String lastName, int empId, String mailId, ProjectInfo project, String location) throws RemoteException;

    String createERecord(String firstName, String lastName, int empId, String mailId, String projectId) throws RemoteException;

    String getRecordCounts() throws RemoteException;

    String editRecord(String recordId, String fieldName, T newValue) throws RemoteException;
}
