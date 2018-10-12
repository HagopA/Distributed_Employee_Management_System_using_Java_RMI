package records;

import java.io.Serializable;

public class ProjectInfo implements Serializable
{
    private String projectId;
    private String clientName;
    private String projectName;

    public ProjectInfo(String projectId, String clientName, String projectName)
    {
        this.projectId = projectId;
        this.clientName = clientName;
        this.projectName = projectName;
    }

    public String toString()
    {
        return "Project Information: \n" +
                "\tProject ID: " + this.getProjectId() + "\n" +
                "\tClient Name: " + this.getClientName() + "\n" +
                "\tProject Name: " + this.getProjectName() + "\n";
    }

    public String getProjectId()
    {
        return this.projectId;
    }

    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
}