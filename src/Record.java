public abstract class Record
{
    private String firstName;
    private String lastName;
    private int empId;
    private String mailId;

    public Record(String firstName, String lastName, int empId, String mailId)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.empId = empId;
        this.mailId = mailId;
    }

    abstract void createRecordId();
    abstract String getRecordId();
    abstract boolean checkIfFieldExists(String aField);

    public String toString()
    {
        return "First Name: " + this.getFirstName() + "\n" +
                "Last Name: " + this.getLastName() + "\n" +
                "Employee ID: " + this.getEmpId() + "\n" +
                "Mail ID: " + this.getMailId() + "\n";
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public int getEmpId()
    {
        return this.empId;
    }

    public void setEmpId(int empId)
    {
        this.empId = empId;
    }

    public String getMailId()
    {
        return this.mailId;
    }

    public void setMailId(String mailId)
    {
        this.mailId = mailId;
    }
}
