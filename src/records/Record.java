package records;

import java.io.Serializable;

/**
 * Abstract Record class that stores the basic building blocks of a record
 *
 * @author Hagop Awakian
 * Assignment 3
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public abstract class Record implements Serializable
{
    /**
     * Data members
     */
    private String firstName;
    private String lastName;
    private int empId;
    private String mailId;

    /**
     * Constructor
     * @param firstName First name of the person
     * @param lastName Last name of the person
     * @param empId The employee ID of the person
     * @param mailId The mail ID of the person
     */
    public Record(String firstName, String lastName, int empId, String mailId)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.empId = empId;
        this.mailId = mailId;
    }

    /**
     * Abstract methods that will be used in inherited classes
     */
    abstract void createRecordId();
    public abstract String getRecordId();
    public abstract boolean checkIfFieldExists(String aField);

    /**
     * @return Returns the string representation of the object
     */
    public String toString()
    {
        return "First Name: " + this.getFirstName() + " " +
                "Last Name: " + this.getLastName() + " " +
                "Employee ID: " + this.getEmpId() + " " +
                "Mail ID: " + this.getMailId() + " ";
    }

    /**
     * Getter for firstName
     * @return Returns firstName
     */
    public String getFirstName()
    {
        return this.firstName;
    }

    /**
     * Setter for firstName
     * @param firstName First name of the person
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Getter for lastName
     * @return Returns lastName
     */
    public String getLastName()
    {
        return this.lastName;
    }

    /**
     * Setter for lastName
     * @param lastName Last name of the person
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Getter for empId
     * @return Returns the employee ID of the person
     */
    public int getEmpId()
    {
        return this.empId;
    }

    /**
     * Setter for empId
     * @param empId Employee ID of the person
     */
    public void setEmpId(int empId)
    {
        this.empId = empId;
    }

    /**
     * Getter for mailId
     * @return Returns the mail ID of the person
     */
    public String getMailId()
    {
        return this.mailId;
    }

    /**
     * Setter for mailId
     * @param mailId Mail ID of the person
     */
    public void setMailId(String mailId)
    {
        this.mailId = mailId;
    }
}
