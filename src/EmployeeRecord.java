public class EmployeeRecord extends Record
{

    private String projectId;
    private static int recordIdNumber = 10000;
    private final String ER = "ER";
    private String recordId;
    private final String[] ERfields = {"firstName", "lastName", "empId", "maildId", "projectId"};

    public EmployeeRecord(String firstName, String lastName, int empId, String mailId, String projectId)
    {
        super(firstName, lastName, empId, mailId);
        this.projectId = projectId;
        this.createRecordId();
    }

    void createRecordId()
    {
        this.recordId = ER + Integer.toString(recordIdNumber++);
    }

    public boolean checkIfFieldExists(String aField)
    {
        for(int i = 0; i < ERfields.length; i++)
        {
            if(ERfields[i].equals(aField))
            {
                return true;
            }
        }
        return false;
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

    public String getRecordId()
    {
        return this.recordId;
    }
}
