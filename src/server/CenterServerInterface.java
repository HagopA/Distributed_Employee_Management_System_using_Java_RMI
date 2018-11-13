package server;

import records.ProjectInfo;

import javax.jws.WebService;

/**
 * The Interface for the center server
 *
 * @author Hagop Awakian
 * Assignment 3
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
@WebService
public interface CenterServerInterface
{
    String createMRecord(String managerId, String firstName, String lastName, int empId, String mailId, ProjectInfo aProject, String location);
    String createERecord(String managerId, String firstName, String lastName, int empId, String mailId, String projectId);
    String getRecordCounts(String managerId);
    String editRecord(String managerId, String recordId, String fieldName, String newValue);
    String transferRecords(String managerId, String recordId, String remoteCenterServerName);
    String getLocalCounts();
    boolean searchIfRecordExists(String recordId);
}
