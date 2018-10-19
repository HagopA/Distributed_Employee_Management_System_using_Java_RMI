package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server class
 *
 * @author Hagop Awakian
 * Assignment 1
 * Course: SOEN 423
 * Section: H
 * Instructor: Dr. R. Jayakumar
 * Fall 2018
 */
public class Server
{
    public static void main(String[] args) throws Exception
    {
        /**
         * Canada server
         */
        CenterServer CA = new CenterServer();
        Registry CAregistry = LocateRegistry.createRegistry(2930);
        CAregistry.bind("CA",CA);
        CA.log("CA server has started", "CA");
        System.out.println("CA server has started");

        /**
         * US server
         */
        CenterServer US = new CenterServer();
        Registry USregistry = LocateRegistry.createRegistry(2931);
        USregistry.bind("US", US);
        US.log("US server has started", "US");
        System.out.println("US server has started");

        /**
         * UK server
         */
        CenterServer UK = new CenterServer();
        Registry UKresgistry = LocateRegistry.createRegistry(2932);
        UKresgistry.bind("UK", UK);
        UK.log("UK server has started", "UK");
        System.out.println("UK server has started");
    }
}
