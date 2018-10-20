package server;

import java.rmi.*;
import records.ProjectInfo;

/**
 * The Interface for the center server
 *
 * @author Hagop Awakian
 * Assignment 1
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public interface CenterServerInterface<T> extends Remote
{
    String createMRecord(String firstName, String lastName, int empId, String mailId, ProjectInfo project, String location) throws RemoteException;

    String createERecord(String firstName, String lastName, int empId, String mailId, String projectId) throws RemoteException;

    String getRecordCounts() throws RemoteException;

    String editRecord(String recordId, String fieldName, T newValue) throws RemoteException;

    String getLocalCounts() throws RemoteException;

//    String transferRecord(String managerID, String recordID, String remoteCenterServerName);
}
