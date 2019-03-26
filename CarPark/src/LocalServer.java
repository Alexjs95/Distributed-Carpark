import CarPark.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.ArrayList;

public class LocalServer {
    public static String serverName;
    public static int numberOfEntry;
    public static int numberOfExit;
    public static int numberOfPay;
    static ArrayList<String> entryGates = new ArrayList<String>();
    static ArrayList<String> exitGates = new ArrayList<String>();
    static ArrayList<String> payStations = new ArrayList<String>();


    static public void main(String[] args) {
        for (int i = 0; i < args.length; i ++) {
            if (args[i].equals("-name")) {
                serverName = args[i + 1];
            } else if (args[i].equals("-entrygate")) {
                numberOfEntry = Integer.parseInt((args[i + 1]));
                for (int j = i + 2; j <= i + 1+ numberOfEntry; j++) {
                    entryGates.add(args[j]);
                }
            } else if (args[i].equals("-paystation")) {
                numberOfPay = Integer.parseInt((args[i + 1]));
                for (int j = i + 2; j <= i + 1 + numberOfPay; j++) {
                    payStations.add(args[j]);
                }
            } else if (args[i].equals("-exitgate")) {
                numberOfExit = Integer.parseInt((args[i + 1]));
                for (int j = i + 2; j <= i + 1 + numberOfExit; j++) {
                    exitGates.add(args[j]);
                }
            }
        }
        //java LocalServer -name server001 -entrygate 3 001 002 003 -exitgate 2 001 002 -paystation 4 002 004 005 005 -ORBInitialPort 1075

        System.out.println("Server name " + serverName);

        System.out.println("Number of entry gates" + numberOfEntry);
        for (int i = 0; i <entryGates.size(); i++) {
            System.out.println("Entry Gate " + i + " is named " + entryGates.get(i));
        }


        System.out.println("Number of exit gates" + numberOfExit);
        for (int i = 0; i <exitGates.size(); i++) {
            System.out.println("Exit Gate " + i + " is named " + exitGates.get(i));
        }



        System.out.println("Number of pay stations" + payStations);
        for (int i = 0; i <payStations.size(); i++) {
            System.out.println("paystation " + i + " is named " + payStations.get(i));
        }

        System.out.println(serverName);

        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();



            // Create the Entry servant object
            EntryGateImpl entry = new EntryGateImpl();

            // get object reference from the servant
            org.omg.CORBA.Object entryRef = rootpoa.servant_to_reference(entry);
            EntryGate crefEntry = EntryGateHelper.narrow(entryRef);



            // Create the Exit gate servant object
            ExitGateImpl exit = new ExitGateImpl();

            // get object reference from the servant
            org.omg.CORBA.Object exitRef = rootpoa.servant_to_reference(exit);
            ExitGate crefExit = ExitGateHelper.narrow(exitRef);




            // Create the Pay Station servant object
            PayStationImpl pay = new PayStationImpl();

            // get object reference from the servant
            org.omg.CORBA.Object payRef = rootpoa.servant_to_reference(pay);
            PayStation crefPay = PayStationHelper.narrow(payRef);




            // Create the HQ servant object
            HeadquartersImpl hq = new HeadquartersImpl();

            // get object reference from the servant
            org.omg.CORBA.Object hqRef = rootpoa.servant_to_reference(hq);
            CompanyHQ crefHq = CompanyHQHelper.narrow(hqRef);




            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObjMachines = orb.resolve_initial_references ("NameService");
            if (nameServiceObjMachines == null) {
                System.out.println("nameServiceObj = null");
                return;
            }

            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt nameServiceMachines = NamingContextExtHelper.narrow(nameServiceObjMachines);
            if (nameServiceMachines == null) {
                System.out.println("nameService = null");
                return;
            }

            for (int i = 0; i < numberOfEntry; i++) {
                // bind the entry gate objects in the Naming service
                String name = entryGates.get(i);
                NameComponent[] entrygateName = nameServiceMachines.to_name(name);
                nameServiceMachines.rebind(entrygateName, crefEntry);
            }

            for (int i = 0; i < numberOfExit; i++) {
                // bind the exit gate objects in the Naming service
                String name = exitGates.get(i);
                NameComponent[] exitgateName = nameServiceMachines.to_name(name);
                nameServiceMachines.rebind(exitgateName, crefExit);
            }

            for (int i = 0; i < numberOfPay; i++) {
                // bind the pay station objects in the Naming service
                String name = payStations.get(i);
                NameComponent[] paystationName = nameServiceMachines.to_name(name);
                nameServiceMachines.rebind(paystationName, crefPay);
            }

            NameComponent[] hqName = nameServiceMachines.to_name("HQ");
            nameServiceMachines.rebind(hqName, crefHq);


            // Register local server as a client

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObjServers = orb.resolve_initial_references("NameService");
            if (nameServiceObjServers == null) {
                System.out.println("nameServiceObjServers = null");
                return;
            }

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt nameServiceServers = NamingContextExtHelper.narrow(nameServiceObjServers);
            if (nameServiceServers == null) {
                System.out.println("nameServiceServers = null");
                return;
            }

            String name = serverName;
            CarPark.LocalServer localServer = LocalServerHelper.narrow(nameServiceServers.resolve_str(name));


            // create servant and register it with the ORB
            LocalServerImpl serverImpl = new LocalServerImpl();

            // Get the 'stringified IOR'
            org.omg.CORBA.Object serverRef = rootpoa.servant_to_reference(serverImpl);
            String stringified_ior = orb.object_to_string(serverRef);

            localServer.register_server(serverName, stringified_ior);


            //  wait for invocations from clients
            orb.run();

        } catch(Exception e) {
            System.err.println("LocalServer Exception");
            System.err.println(e);
        }
    }

}
