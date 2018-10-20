package client;

import records.ProjectInfo;
import server.CenterServerInterface;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Manager client class. It will run the multiple manager (threads) requests and is responsible for the flow of the
 * program
 *
 * @author Hagop Awakian
 * Assignment 1
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
     * Check if the passed string, specifically an HR manager ID (ex: CA1234), is an integer. It is used to pass the
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
        Registry CAregistry;
        Registry USregistry;
        Registry UKregistry;
        CenterServerInterface CA = null;
        CenterServerInterface US = null;
        CenterServerInterface UK = null;
        Scanner keyboard = new Scanner(System.in);
        boolean exitSystem = false;
        boolean logout;
        int chosenOption;
        String associatedServer;
        String response;
        /**
         * Initialize registries
         */
        try
        {
            CAregistry = LocateRegistry.getRegistry(2930);
            CA = (CenterServerInterface) CAregistry.lookup("CA");

            USregistry = LocateRegistry.getRegistry(2931);
            US = (CenterServerInterface) USregistry.lookup("US");

            UKregistry = LocateRegistry.getRegistry(2932);
            UK = (CenterServerInterface) UKregistry.lookup("UK");
        }
        catch (AccessException e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        catch (RemoteException e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        catch (NotBoundException e)
        {
            System.out.println("Error: Invalid server - " + e.getMessage());
            System.exit(0);
        }

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
                            try
                            {
                                System.out.println(CA.createMRecord(firstName, lastName, empId, mailId, aProject, location));
                                this.log(managerId + " created a manager record." +
                                        "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                        " Mail ID: " + mailId + " Project ID: " + projectId + " Project client name: " + clientName +
                                        "\nProject name: " + projectName + " Locaton: " + location);
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
                        }
                        else if(associatedServer.equals("US"))
                        {
                            try
                            {
                                System.out.println(US.createMRecord(firstName, lastName, empId, mailId, aProject, location));
                                this.log(managerId + " created a manager record." +
                                        "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                        " Mail ID: " + mailId + " Project ID: " + projectId + " Project client name: " + clientName +
                                        "\nProject name: " + projectName + " Locaton: " + location);
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
                        }
                        else if(associatedServer.equals("UK"))
                        {
                            try
                            {
                                System.out.println(UK.createMRecord(firstName, lastName, empId, mailId, aProject, location));
                                this.log(managerId + " created a manager record." +
                                        "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                        " Mail ID: " + mailId + " Project ID: " + projectId + " Project client name: " + clientName +
                                        "\nProject name: " + projectName + " Locaton: " + location);
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
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
                            }
                        }
                        System.out.println("Please enter the mailId: ");
                        mailId = keyboard.next();
                        System.out.println("Please enter the project ID: ");
                        projectId = keyboard.next();

                        if(associatedServer.equals("CA"))
                        {
                            try
                            {
                                System.out.println(CA.createERecord(firstName, lastName, empId, mailId, projectId));
                                this.log(managerId + " created a employee record." +
                                        "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                        " Mail ID: " + mailId + " Project ID: " + projectId);
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
                        }
                        else if(associatedServer.equals("US"))
                        {
                            try
                            {
                                System.out.println(US.createERecord(firstName, lastName, empId, mailId, projectId));
                                this.log(managerId + " created a employee record." +
                                        "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                        " Mail ID: " + mailId + " Project ID: " + projectId);
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
                        }
                        else if(associatedServer.equals("UK"))
                        {
                            try
                            {
                                System.out.println(UK.createERecord(firstName, lastName, empId, mailId, projectId));
                                this.log(managerId + " created a employee record." +
                                        "First name: " + firstName + " Last name: " + lastName + " Employee ID: " + empId +
                                        " Mail ID: " + mailId + " Project ID: " + projectId);
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
                        }
                        break;
                    case 3:
                        System.out.println("You have chosen to get the record counts.");

                        if(associatedServer.equals("CA"))
                        {
                            try
                            {
                                System.out.println(CA.getRecordCounts());
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
                        }
                        else if(associatedServer.equals("US"))
                        {
                            try
                            {
                                System.out.println(US.getRecordCounts());
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
                        }
                        else if(associatedServer.equals("UK"))
                        {
                            try
                            {
                                System.out.println(UK.getRecordCounts());
                            }
                            catch(RemoteException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                                System.exit(0);
                            }
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
                        System.out.println("Please enter the field name. The options are as follows:\n "
                                            + "\t To modify a manager record field, enter: mailId, project, location.\n"
                                            + "\t To modify an employee record field, enter: maildId, projectId.");
                        fieldName = keyboard.next();
                        if(fieldName.equals("project"))
                        {
                            System.out.println("Please enter the project ID: ");
                            projectId = keyboard.next();
                            keyboard.nextLine();
                            System.out.println("Please enter the client name: ");
                            clientName = keyboard.nextLine();
                            System.out.println("Please enter the project name: ");
                            projectName = keyboard.nextLine();
                            newValueProject = new ProjectInfo(projectId, clientName, projectName);

                            if(associatedServer.equals("CA"))
                            {
                                try
                                {
                                    this.log(managerId + CA.editRecord(recordId, fieldName, newValueProject));
                                }
                                catch(RemoteException e)
                                {
                                    System.out.println("Error: " + e.getMessage());
                                    System.exit(0);
                                }
                            }
                            else if(associatedServer.equals("US"))
                            {
                                try
                                {
                                    this.log(managerId + US.editRecord(recordId, fieldName, newValueProject));
                                }
                                catch(RemoteException e)
                                {
                                    System.out.println("Error: " + e.getMessage());
                                    System.exit(0);
                                }
                            }
                            else if(associatedServer.equals("UK"))
                            {
                                try
                                {
                                    this.log(managerId + UK.editRecord(recordId, fieldName, newValueProject));
                                }
                                catch(RemoteException e)
                                {
                                    System.out.println("Error: " + e.getMessage());
                                    System.exit(0);
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Please enter the new value of the field: ");
                            newValue = keyboard.next();
                            if(associatedServer.equals("CA"))
                            {
                                try
                                {
                                    this.log(CA.editRecord(recordId, fieldName, newValue));
                                }
                                catch(RemoteException e)
                                {
                                    System.out.println("Error: " + e.getMessage());
                                    System.exit(0);
                                }
                            }
                            else if(associatedServer.equals("US"))
                            {
                                try
                                {
                                    this.log(US.editRecord(recordId, fieldName, newValue));
                                }
                                catch(RemoteException e)
                                {
                                    System.out.println("Error: " + e.getMessage());
                                    System.exit(0);
                                }
                            }
                            else if(associatedServer.equals("UK"))
                            {
                                try
                                {
                                    UK.editRecord(recordId, fieldName, newValue);
                                }
                                catch(RemoteException e)
                                {
                                    System.out.println("Error: " + e.getMessage());
                                    System.exit(0);
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("Please select a number within the range specified (1-4)");
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

    /**
     * Entry point of the program to begin interacting with the DEMS
     */
    public static void main(String[] args)
    {
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
