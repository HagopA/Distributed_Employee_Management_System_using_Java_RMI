package server;

import UDP.*;

import javax.xml.ws.Endpoint;


/**
 * Main Server class
 *
 * @author Hagop Awakian
 * Assignment 3
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class Server
{
    public static void main(String[] args) throws Exception
    {
        CenterServer caServerImpl = new CenterServer();
        CenterServer usServerImpl = new CenterServer();
        CenterServer ukServerImpl = new CenterServer();

        Endpoint endpointCA = Endpoint.publish("http://localhost:7777/CA", caServerImpl);
        Endpoint endpointUS = Endpoint.publish("http://localhost:7777/US", usServerImpl);
        Endpoint endpointUK = Endpoint.publish("http://localhost:7777/UK", ukServerImpl);
        new Thread(new CAServerThread(2930)).start();
        new Thread(new USServerThread(2931)).start();
        new Thread(new UKServerThread(2932)).start();

        caServerImpl.log("CA server started running", "CA");
        usServerImpl.log("US server started running", "US");
        ukServerImpl.log("UK server started running", "UK");
        System.out.println("CA server started running");
        System.out.println("US server started running");
        System.out.println("UK server started running");
    }
}
