package client;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import server.centerServerInterfaceIDL.CenterServerInterface;
import server.centerServerInterfaceIDL.CenterServerInterfaceHelper;
import server.centerServerInterfaceIDL.ProjectInfo;

/**
 * Manager client class. It will run the multiple manager (threads) requests and is responsible for the flow of the
 * program
 *
 * @author Hagop Awakian
 * Assignment 2
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class ManagerClient implements Runnable
{
    private Logger logger;
    private FileHandler fh;
    String managerId;

    /**
     * Checks if the passed string, specifically an HR manager ID (ex: CA1234), is an integer. It is used to pass the
     * 4 last characters of the HR manager ID string
     * @param s An HR manager ID string
     * @return Returns true if s is an integer, otherwise returns false
     */
    public boolean isInteger(String s)
    {
        try
        {
            Integer.parseInt(s);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    /**
     * Checks if it's a valid manager given the string as a manager ID
     * @param s Manager ID
     * @return Returns true if HR manager is from CA, US, or UK. Otherwise returns false
     */
    public boolean isValidManager(String s)
    {
        switch (s.substring(0,2))
        {
            case "CA":
            case "US":
            case "UK":
                return true;
        }
        return false;
    }

    /**
     * Method to log information after update of a record
     * @param message Message to log.
     */
    public void log(String message)
    {
        if(this.logger == null)
        {
            this.logger = Logger.getLogger(Thread.currentThread().getId() + "Client");
            try
            {
                this.fh = new FileHandler("Client Logs\\" + this.managerId + "_client_log.txt", true);
                this.fh.setFormatter((new SimpleFormatter()));
                this.logger.addHandler(fh);
                this.logger.setUseParentHandlers(false);
                this.logger.info(message);
        }
            catch (IOException e)
            {
                System.out.println("IO Error: " + e.getMessage() + ". The file could not been created");
            }
            catch (SecurityException e)
            {
                System.out.println("Security Error: " + e.getMessage() + ". The file could not been created");
            }
        }
        else
        {
            this.logger.info(message);
        }
    }

    /**
     * Run method for multithreaded execution
     */
    public void run()
    {
        /**
         * Data members
         */
        Scanner keyboard = new Scanner(System.in);
        boolean exitSystem = false;
        boolean logout;
        int chosenOption;
        String associatedServer;
        String response;

        System.out.println("---------------------DEMS---------------------\n");
        System.out.println("Welcome to the DEMS\n");

        /**
         * Main program loop, will exit if the use enters "e"
         */
        while(!exitSystem)
        {

            System.out.print("Please enter your manager ID to start using the system, or enter \"e\" to exit the system: ");
            managerId = keyboard.next().toUpperCase();
            keyboard.nextLine();
            logout = false;

            if(managerId.substring(0,1).equalsIgnoreCase("e"))
            {
                exitSystem = true;
                continue;
            }

            if(managerId.length() != 6 || !isValidManager(managerId) || !isInteger(managerId.substring(2, 6)))
            {
                System.out.println("You have entered an invalid manager ID.");
                continue;
            }

            associatedServer = managerId.substring(0,2);
            this.log(managerId + " has started using the system");

            while(!logout)
            {
                System.out.println("Please choose one of the following options to perform in the DEMS: ");
                System.out.println("\t1. Create a manager record");
                System.out.println("\t2. Create an employee record");
                System.out.println("\t3. Get record counts");
                System.out.println("\t4. Edit a record");
                System.out.println("\t5. Transfer a record");
                System.out.print("Enter the number associated with the action (1-4): ");
                try
                {
                    chosenOption = keyboard.nextInt();
                    keyboard.nextLine();
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Error: you have entered an invalid option, please try again:");
                    keyboard.nextLine();
                    continue;
                }
                switch (chosenOption)
                {
                    case 1:
                        String firstName;
                        String lastName;
                        int empId = 0;
                        String mailId;
                        String projectId;
                        String clientName;
                        String projectName;
                        ProjectInfo aProject;
                        String location;

                        System.out.println("You have chosen to create a manager record.");
                        System.out.println("Please enter the first name: ");
                        firstName = keyboard.nextLine();
                        System.out.println("Please enter the last name: ");
                        lastName = keyboard.nextLine();
                        boolean NaN = true;
                        while(NaN)
                        {
                            try
                            {
                                System.out.println("Please enter the employee ID: ");
                                empId = keyboard.nextInt();
                                keyboard.nextLine();
                                NaN = false;
                            }
                            catch(InputMismatchException e)
                            {
                                System.out.println("Error: You have not entered a number, try again.");
                                keyboard.nextLine();
                            }
                        }
                        System.out.println("Please enter the mailId: ");
                        mailId = keyboard.next();
                        keyboard.nextLine();
                        System.out.println("Please enter the project ID: ");
                        projectId = keyboard.next();
                        keyboard.nextLine();
                        System.out.println("Please enter the client name: ");
                        clientName = keyboard.nextLine();
                        System.out.println("Please enter the project name: ");
                        projectName = keyboard.nextLine();
                        System.out.println("Please enter the location: ");
                        location = keyboard.next();
                        keyboard.nextLine();
                        aProject = new ProjectInfo(projectId, clientName, projectName);
                        if(associatedServer.equals("CA"))
                        {
                            System.out.println(centerServerImplCA.createMRecord(managerId, firstName, lastName, empId, mailId, aProject, location));
                            this.log(managerId + " created a manager record." +
                                    "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                    " Mail ID: " + mailId + " Project ID: " + projectId + " Project client name: " + clientName +
                                    "\nProject name: " + projectName + " Locaton: " + location);
                        }
                        else if(associatedServer.equals("US"))
                        {
                            System.out.println(centerServerImplUS.createMRecord(managerId, firstName, lastName, empId, mailId, aProject, location));
                            this.log(managerId + " created a manager record." +
                                    "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                    " Mail ID: " + mailId + " Project ID: " + projectId + " Project client name: " + clientName +
                                    "\nProject name: " + projectName + " Locaton: " + location);
                        }
                        else if(associatedServer.equals("UK"))
                        {
                            System.out.println(centerServerImplUK.createMRecord(managerId, firstName, lastName, empId, mailId, aProject, location));
                            this.log(managerId + " created a manager record." +
                                    "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                    " Mail ID: " + mailId + " Project ID: " + projectId + " Project client name: " + clientName +
                                    "\nProject name: " + projectName + " Locaton: " + location);
                        }
                        break;
                    case 2:
                        empId = 0;
                        System.out.println("You have chosen to create an employee record.");
                        System.out.println("Please enter the first name: ");
                        firstName = keyboard.nextLine();
                        System.out.println("Please enter the last name: ");
                        lastName = keyboard.nextLine();
                        boolean NaN2 = true;
                        while(NaN2)
                        {
                            try
                            {
                                System.out.println("Please enter the employee ID: ");
                                empId = keyboard.nextInt();
                                keyboard.nextLine();
                                NaN2 = false;
                            }
                            catch(InputMismatchException e)
                            {
                                System.out.println("Error: You have not entered a number, try again.");
                                keyboard.nextLine();
                            }
                        }
                        System.out.println("Please enter the mailId: ");
                        mailId = keyboard.next();
                        System.out.println("Please enter the project ID: ");
                        projectId = keyboard.next();

                        if(associatedServer.equals("CA"))
                        {
                            System.out.println(centerServerImplCA.createERecord(managerId, firstName, lastName, empId, mailId, projectId));
                            this.log(managerId + " created a employee record." +
                                    "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                    " Mail ID: " + mailId + " Project ID: " + projectId);
                        }
                        else if(associatedServer.equals("US"))
                        {
                            System.out.println(centerServerImplUS.createERecord(managerId, firstName, lastName, empId, mailId, projectId));
                            this.log(managerId + " created a employee record." +
                                    "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                    " Mail ID: " + mailId + " Project ID: " + projectId);
                        }
                        else if(associatedServer.equals("UK"))
                        {
                            System.out.println(centerServerImplUK.createERecord(managerId, firstName, lastName, empId, mailId, projectId));
                            this.log(managerId + " created a employee record." +
                                    "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                    " Mail ID: " + mailId + " Project ID: " + projectId);
                        }
                        break;
                    case 3:
                        System.out.println("You have chosen to get the record counts.");

                        if(associatedServer.equals("CA"))
                        {
                            String message = centerServerImplCA.getRecordCounts(managerId);
                            System.out.println(message);
                            this.log(message);
                        }
                        else if(associatedServer.equals("US"))
                        {
                            String message = centerServerImplUS.getRecordCounts(managerId);
                            System.out.println(message);
                            this.log(message);
                        }
                        else if(associatedServer.equals("UK"))
                        {
                            String message = centerServerImplUK.getRecordCounts(managerId);
                            System.out.println(message);
                            this.log(message);
                        }
                        break;
                    case 4:
                        System.out.println("You have chosen to edit a record.");

                        String recordId;
                        String fieldName;
                        String newValue;
                        ProjectInfo newValueProject;
                        System.out.println("Please enter the record ID: ");
                        recordId = keyboard.next().toUpperCase();

                        if(recordId.substring(0,2).toUpperCase().equals("MR"))
                        {
                            System.out.println("Please enter the field name. The options are as follows:\n "
                                    + "\t To modify a manager record field, enter: mailId, projectId, clientName, projectName, location.\n");
                        }
                        else if(recordId.substring(0,2).toUpperCase().equals("ER"))
                        {
                            System.out.println("\t To modify an employee record field, enter: maildId, projectId.");
                        }
                        fieldName = keyboard.next();

                        System.out.println("Please enter the new value of the field: ");
                        newValue = keyboard.next();

                        if(associatedServer.equals("CA"))
                        {
                            String message = centerServerImplCA.editRecord(managerId, recordId, fieldName, newValue);
                            System.out.println(message);
                            this.log(message);
                        }
                        else if(associatedServer.equals("US"))
                        {
                            String message = centerServerImplUS.editRecord(managerId, recordId, fieldName, newValue);
                            System.out.println(message);
                            this.log(message);
                        }
                        else if(associatedServer.equals("UK"))
                        {
                            String message = centerServerImplUK.editRecord(managerId, recordId, fieldName, newValue);
                            System.out.println(message);
                            this.log(message);
                        }
                        break;
                    case 5:
                        System.out.println("You have chosen to transfer a record");
                        String recordID;
                        String remoteCenterServerName;
                        System.out.println("Please enter the record ID: ");
                        recordID = keyboard.next().toUpperCase();
                        System.out.println("Please enter the remote center server name: ");
                        remoteCenterServerName = keyboard.next().toUpperCase();

                        if(associatedServer.equals("CA"))
                        {
                            String message = centerServerImplCA.transferRecords(managerId, recordID, remoteCenterServerName);
                            System.out.println(message);
                            this.log(message);
                        }
                        else if(associatedServer.equals("US"))
                        {
                            String message = centerServerImplUS.transferRecords(managerId, recordID, remoteCenterServerName);
                            System.out.println(message);
                            this.log(message);
                        }
                        else if(associatedServer.equals("UK"))
                        {
                            String message = centerServerImplUK.transferRecords(managerId, recordID, remoteCenterServerName);
                            System.out.println(message);
                            this.log(message);
                        }
                        break;
                    default:
                        System.out.println("Please select a number within the range specified (1-5)");
                        continue;
                }
                boolean toContinue = true;
                while(toContinue)
                {
                    System.out.println("Would you like to continue with another operation? [y,n]");
                    response = keyboard.next();
                    keyboard.nextLine();

                    if(response.substring(0,1).equalsIgnoreCase("n"))
                    {
                        logout = true;
                        toContinue = false;
                    }
                    else if(response.substring(0,1).equalsIgnoreCase("y"))
                    {
                        toContinue = false;
                    }
                }
            }
        }
        keyboard.close();
    }

    static CenterServerInterface centerServerImplCA;
    static CenterServerInterface centerServerImplUS;
    static CenterServerInterface centerServerImplUK;

    /**
     * Entry point of the program to begin interacting with the DEMS
     */
    public static void main(String[] args)
    {
        ORB orb;
        org.omg.CORBA.Object objRef;
        NamingContextExt ncRef;
        final String nameCA = "CA";
        final String nameUS = "US";
        final String nameUK = "UK";

        try
        {
            orb = ORB.init(args, null);
            objRef = orb.resolve_initial_references("NameService");
            ncRef = NamingContextExtHelper.narrow(objRef);
            centerServerImplCA = CenterServerInterfaceHelper.narrow(ncRef.resolve_str(nameCA));
            centerServerImplUS = CenterServerInterfaceHelper.narrow(ncRef.resolve_str(nameUS));
            centerServerImplUK = CenterServerInterfaceHelper.narrow(ncRef.resolve_str(nameUK));
        }
        catch (InvalidName invalidName)
        {
            invalidName.printStackTrace();
        }
        catch (CannotProceed cannotProceed)
        {
            cannotProceed.printStackTrace();
        }
        catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName)
        {
            invalidName.printStackTrace();
        }
        catch (NotFound notFound)
        {
            notFound.printStackTrace();
        }

        ManagerClient mc1 = new ManagerClient();
        Thread t1 = new Thread(mc1);
        t1.start();

//        ManagerClient mc2 = new ManagerClient();
//        Thread t2 = new Thread(mc2);
//        t2.start();
//
//        ManagerClient mc3 = new ManagerClient();
//        Thread t3 = new Thread(mc3);
//        t3.start();
//
//        ManagerClient mc4 = new ManagerClient();
//        Thread t4 = new Thread(mc4);
//        t4.start();
//
//        ManagerClient mc5 = new ManagerClient();
//        Thread t5 = new Thread(mc5);
//        t5.start();
//
//        ManagerClient mc6 = new ManagerClient();
//        Thread t6 = new Thread(mc6);
//        t6.start();
//
//        ManagerClient mc7 = new ManagerClient();
//        Thread t7 = new Thread(mc7);
//        t7.start();
//
//        ManagerClient mc8 = new ManagerClient();
//        Thread t8 = new Thread(mc8);
//        t8.start();
//
//        ManagerClient mc9 = new ManagerClient();
//        Thread t9 = new Thread(mc9);
//        t9.start();
//
//        ManagerClient mc10 = new ManagerClient();
//        Thread t10 = new Thread(mc10);
//        t10.start();
    }
}
