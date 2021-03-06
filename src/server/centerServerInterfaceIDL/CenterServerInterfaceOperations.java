package server.centerServerInterfaceIDL;


/**
* server/centerServerInterfaceIDL/CenterServerInterfaceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CenterServerInterfaceIDL.idl
* Friday, October 26, 2018 5:01:33 PM EDT
*/

public interface CenterServerInterfaceOperations 
{
  String createMRecord (String managerId, String firstName, String lastName, int empId, String mailId, server.centerServerInterfaceIDL.ProjectInfo project, String location);
  String createERecord (String managerId, String firstName, String lastName, int empId, String mailId, String projectId);
  String getRecordCounts (String managerId);
  String editRecord (String managerId, String recordId, String fieldName, String newValue);
  String transferRecords (String managerId, String recordId, String remoteCenterServerName);
  String getLocalCounts ();
  boolean searchIfRecordExists (String recordId);
} // interface CenterServerInterfaceOperations
