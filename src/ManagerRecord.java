public class ManagerRecord extends Record
{

    private ProjectInfo project;
    private String location;
    private static int recordIdNumber = 10000;
    private final String MR = "MR";
    private String recordId;
    private final String[] MRfields = {"firstName", "lastName", "empId", "mailId", "project", "location"};

    public ManagerRecord(String firstName, String lastName, int empId, String mailId, ProjectInfo project, String location)
    {
        super(firstName, lastName, empId, mailId);
        this.project = new ProjectInfo(project.getProjectId(), project.getClientName(), project.getProjectName());
        this.location = location;
        this.createRecordId();
    }

    void createRecordId()
    {
        this.recordId = MR + Integer.toString(recordIdNumber++);
    }

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

    public String toString()
    {
        return super.toString() + project.toString() + "\n" + "Location: " + this.getLocation() + "\n";
    }

    public ProjectInfo getProject()
    {
        return this.project;
    }

    public void setProject(ProjectInfo project)
    {
        this.project.setProjectId(project.getProjectId());
        this.project.setClientName(project.getClientName());
        this.project.setProjectName(project.getProjectName());
    }

    public String getLocation()
    {
        return this.location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getRecordId()
    {
        return this.recordId;
    }
}
