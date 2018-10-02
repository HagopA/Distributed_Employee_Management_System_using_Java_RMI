public class ManagerRecord extends Record
{

    private ProjectInfo project;
    private String location;
    private static int recordIdNumber = 10000;
    private String recordId = "MR";

    public ManagerRecord(String firstName, String lastName, int empId, String mailId, ProjectInfo project, String location)
    {
        super(firstName, lastName, empId, mailId);
        this.project = new ProjectInfo(project.getProjectId(), project.getClientName(), project.getProjectName());
        this.location = location;
        this.createRecordId();
    }

    public void createRecordId()
    {
        this.recordId += Integer.toString(recordIdNumber++);
    }

    public String toString()
    {
        return super.toString() + project.toString() + "\n" + "Location: " + this.getLocation() + "\n";
    }

    public ProjectInfo getProject()
    {
        return project;
    }

    public void setProject(ProjectInfo project)
    {
        this.project.setProjectId(project.getProjectId());
        this.project.setClientName(project.getClientName());
        this.project.setProjectName(project.getProjectName());
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
}