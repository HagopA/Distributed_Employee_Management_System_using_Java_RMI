public class EmployeeRecord extends Record
{

    private String projectId;
    private static int recordIdNumber = 10000;
    private String recordId = "ER";

    public EmployeeRecord(String firstName, String lastName, int empId, String mailId, String projectId)
    {
        super(firstName, lastName, empId, mailId);
        this.projectId = projectId;
        this.createRecordId();
    }

    public void createRecordId()
    {
        this.recordId += Integer.toString(recordIdNumber++);
    }

    public String toString()
    {
        return super.toString() + "Project ID: " + this.getProjectId() + "\n";
    }

    public String getProjectId()
    {
        return this.projectId;
    }

    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }
}