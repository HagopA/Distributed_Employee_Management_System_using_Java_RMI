package records;

import java.io.Serializable;
import server.centerServerInterfaceIDL.ProjectInfo;

/**
 * Manager record class
 *
 * @author Hagop Awakian
 * Assignment 2
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class ManagerRecord extends Record implements Serializable
{
    /**
     * Data members
     */
    private ProjectInfo project;
    private String location;
    private static int recordIdNumber = 10000;
    private final String MR = "MR";
    private String recordId;
    private final String[] MRfields = {"firstName", "lastName", "empId", "mailId", "projectId", "clientName", "projectName", "location"};

    /**
     * Constructor
     * @param firstName First name of a manager
     * @param lastName Last name of a manager
     * @param empId Employee ID of a manager
     * @param mailId Mail ID of a manager
     * @param project Project information of a manager as an object
     * @param location Location of a manager
     */
    public ManagerRecord(String firstName, String lastName, int empId, String mailId, ProjectInfo project, String location)
    {
        super(firstName, lastName, empId, mailId);
        this.project = new ProjectInfo(project.projectId, project.clientName, project.projectName);
        this.location = location;
        this.createRecordId();
    }

    /**
     * Creates a record ID each time the constructor is called (when a new manager object is created)
     */
    void createRecordId()
    {
        this.recordId = MR + Integer.toString(recordIdNumber++);
    }

    /**
     * Check if a field exists in this class.
     * @param aField The name of the field that is being changed by an HR manager
     * @return Returns true if the field exists, otherwise returns false
     */
    public boolean checkIfFieldExists(String aField)
    {
        for(int i = 0; i < MRfields.length; i++)
        {
            if(MRfields[i].equals(aField))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Returns the string representation of the object
     */
    public String toString()
    {
        return super.toString() + "\n" + "Project Information: \n" +
                "\tProject ID: " + this.project.projectId + "\n" +
                "\tClient Name: " + this.project.clientName + "\n" +
                "\tProject Name: " + this.project.projectName + "\n" +
                " " + "Location: " + this.getLocation() + " records.Record ID: " + this.getRecordId();
    }

    /**
     * Getter for the project object
     * @return Returns the project information as an object
     */
    public ProjectInfo getProject()
    {
        return this.project;
    }

    /**
     * Setter for project
     * @param project Project object
     */
    public void setProject(ProjectInfo project)
    {
        this.project.projectId = project.projectId;
        this.project.clientName = project.clientName;
        this.project.projectName = project.projectName;
    }

    public String getProjectId()
    {
        return this.project.projectId;
    }

    public String getClientName()
    {
        return this.project.clientName;
    }

    public String getProjectName()
    {
        return this.project.projectName;
    }

    public void setProjectId(String projectId)
    {
        this.project.projectId = projectId;
    }

    public void setProjectClientName(String clientName)
    {
        this.project.clientName = clientName;
    }

    public void setProjectName(String projectName)
    {
        this.project.projectName = projectName;
    }

    /**
     * Getter for location
     * @return Returns the location of the manager
     */
    public String getLocation()
    {
        return this.location;
    }

    /**
     * Setter for location
     * @param location Location of the manager
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * Getter for the record ID of the manager
     * @return Returns the record ID of the manager
     */
    public String getRecordId()
    {
        return this.recordId;
    }
}
