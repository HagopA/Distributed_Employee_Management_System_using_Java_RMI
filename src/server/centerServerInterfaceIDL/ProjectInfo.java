package server.centerServerInterfaceIDL;


/**
* server/centerServerInterfaceIDL/ProjectInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CenterServerInterfaceIDL.idl
* Friday, October 26, 2018 5:01:33 PM EDT
*/

public final class ProjectInfo implements org.omg.CORBA.portable.IDLEntity
{
  public String projectId = null;
  public String clientName = null;
  public String projectName = null;

  public ProjectInfo ()
  {
  } // ctor

  public ProjectInfo (String _projectId, String _clientName, String _projectName)
  {
    projectId = _projectId;
    clientName = _clientName;
    projectName = _projectName;
  } // ctor

} // class ProjectInfo
