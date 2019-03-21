import CarPark.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class LocalServer {
    public static String serverName;

    static public void main(String[] args) {
        for (int i = 0; i < args.length; i ++) {
            if ((!args[i].equals("-ORBInitialPort")) && (!args[i].matches("^[0-9]*$"))) {
                serverName = args[i];
            }
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


            // Create the Pay Station servant object
            PayStationImpl pay = new PayStationImpl();

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(pay);
            PayStation crefPay = PayStationHelper.narrow(ref);


            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObjMachines =
                    orb.resolve_initial_references ("NameService");
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

            // bind the Count object in the Naming service
            String entryName = "EntryGate";
            NameComponent[] entrygateName = nameServiceMachines.to_name(entryName);
            nameServiceMachines.rebind(entrygateName, crefEntry);

            // bind the Count object in the Naming service
            String payName = "PayStation";
            NameComponent[] paystationName = nameServiceMachines.to_name(payName);
            nameServiceMachines.rebind(paystationName, crefPay);



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

            String name = "Local Server";
            CarPark.LocalServer localServer = LocalServerHelper.narrow(nameServiceServers.resolve_str(name));

            localServer.register_server(serverName);
            System.out.println(serverName);


            //  wait for invocations from clients
            orb.run();

        } catch(Exception e) {
            System.err.println(e);
        }
    }

}
