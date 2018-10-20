package records;

import java.io.Serializable;

/**
 * Project class that will store the project information for managers
 *
 * @author Hagop Awakian
 * Assignment 1
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class ProjectInfo implements Serializable
{
    /**
     * Data members
     */
    private String projectId;
    private String clientName;
    private String projectName;

    /**
     * Constructor
     * @param projectId ID of the project
     * @param clientName Name of the client
     * @param projectName Name of the project
     */
    public ProjectInfo(String projectId, String clientName, String projectName)
    {
        this.projectId = projectId;
        this.clientName = clientName;
        this.projectName = projectName;
    }

    /**
     * @return Returns the string representation of the object
     */
    public String toString()
    {
        return "Project Information: \n" +
                "\tProject ID: " + this.getProjectId() + "\n" +
                "\tClient Name: " + this.getClientName() + "\n" +
                "\tProject Name: " + this.getProjectName() + "\n";
    }

    /**
     * Getter for ProjectId
     * @return Returns projectId
     */
    public String getProjectId()
    {
        return this.projectId;
    }

    /**
     * Setter for projectId
     * @param projectId ID of the project
     */
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    /**
     * Getter for clientName
     * @return Returns clientName
     */
    public String getClientName()
    {
        return clientName;
    }

    /**
     * Setter for clientName
     * @param clientName Name of the client
     */
    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    /**
     * Getter for the projectName
     * @return Returns projectName
     */
    public String getProjectName()
    {
        return projectName;
    }

    /**
     * Setter for projectName
     * @param projectName Name of the project
     */
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
}