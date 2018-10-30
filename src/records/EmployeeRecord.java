package records;

/**
 * Employee record class
 *
 * @author Hagop Awakian
 * Assignment 2
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class EmployeeRecord extends Record
{
    /**
     * Data members
     */
    private String projectId;
    private static int recordIdNumber = 10000;
    private final String ER = "ER";
    private String recordId;
    private final String[] ERfields = {"firstName", "lastName", "empId", "maildId", "projectId"};

    /**
     * Constructor
     * @param firstName First name of the employee
     * @param lastName Last name of the employee
     * @param empId Employee ID of the employee
     * @param mailId Mail ID of the employee
     * @param projectId Project ID of the employee
     */
    public EmployeeRecord(String firstName, String lastName, int empId, String mailId, String projectId)
    {
        super(firstName, lastName, empId, mailId);
        this.projectId = projectId;
        this.createRecordId();
    }

    /**
     * Creates a record ID each time the constructor is called (when a new employee object is created)
     */
    void createRecordId()
    {
        this.recordId = ER + Integer.toString(recordIdNumber++);
    }

    /**
     * Check if a field exists in this class.
     * @param aField The name of the field that is being changed by an HR manager
     * @return Returns true if the field exists, otherwise returns false
     */
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

    /**
     * @return Returns the string representation of the object
     */
    public String toString()
    {
        return super.toString() + "Project ID: " + this.getProjectId() + " records.Record ID: " + this.getRecordId();
    }

    /**
     * Getter for projectID
     * @return Returns the project ID of the employee
     */
    public String getProjectId()
    {
        return this.projectId;
    }

    /**
     * Setter for projectId
     * @param projectId Project ID of the employee
     */
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    /**
     * Getter for recordId
     * @return Returns the record ID of the employee
     */
    public String getRecordId()
    {
        return this.recordId;
    }
}
