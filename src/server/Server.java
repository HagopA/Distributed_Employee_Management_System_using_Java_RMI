package server;

import UDP.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAHelper;
import server.centerServerInterfaceIDL.CenterServerInterface;
import server.centerServerInterfaceIDL.CenterServerInterfaceHelper;


/**
 * Main Server class
 *
 * @author Hagop Awakian
 * Assignment 2
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class Server
{
    public static void main(String[] args)
    {
        try
        {
            ORB orb = ORB.init(args,null);
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            CenterServer CAServer = new CenterServer();
            CAServer.setORB(orb);

            CenterServer USServer = new CenterServer();
            USServer.setORB(orb);

            CenterServer UKServer = new CenterServer();
            UKServer.setORB(orb);

            org.omg.CORBA.Object CAref = rootPOA.servant_to_reference(CAServer);
            org.omg.CORBA.Object USref = rootPOA.servant_to_reference(USServer);
            org.omg.CORBA.Object UKref = rootPOA.servant_to_reference(UKServer);

            CenterServerInterface CAhref = CenterServerInterfaceHelper.narrow(CAref);
            CenterServerInterface UShref = CenterServerInterfaceHelper.narrow(USref);
            CenterServerInterface UKhref = CenterServerInterfaceHelper.narrow(UKref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String CAname = "CA";
            String USname = "US";
            String UKname = "UK";

            NameComponent CApath[] = ncRef.to_name(CAname);
            NameComponent USpath[] = ncRef.to_name(USname);
            NameComponent UKpath[] = ncRef.to_name(UKname);

            ncRef.rebind(CApath, CAhref);
            ncRef.rebind(USpath, UShref);
            ncRef.rebind(UKpath, UKhref);

            /**
             * Start UDP for all 3 servers
             */
            new Thread(new CAServerThread(2930, orb)).start();
            new Thread(new USServerThread(2931, orb)).start();
            new Thread(new UKServerThread(2932, orb)).start();

            CAServer.log("CA server started running", "CA");
            USServer.log("US server started running", "US");
            UKServer.log("UK server started running", "UK");
            System.out.println("CA server started running");
            System.out.println("US server started running");
            System.out.println("UK server started running");

            /**
             * Start ORB
             */
            orb.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Exiting all servers");
    }
}
