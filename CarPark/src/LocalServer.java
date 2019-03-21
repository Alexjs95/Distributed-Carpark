import CarPark.EntryGate;
import CarPark.EntryGateHelper;
import CarPark.PayStation;
import CarPark.PayStationHelper;
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
            org.omg.CORBA.Object nameServiceObj =
                    orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                System.out.println("nameServiceObj = null");
                return;
            }

            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                System.out.println("nameService = null");
                return;
            }

            // bind the Count object in the Naming service
            String entryName = "EntryGate";
            NameComponent[] entrygateName = nameService.to_name(entryName);
            nameService.rebind(entrygateName, crefEntry);

            // bind the Count object in the Naming service
            String payName = "PayStation";
            NameComponent[] paystationName = nameService.to_name(payName);
            nameService.rebind(paystationName, crefPay);

            //  wait for invocations from clients
            orb.run();

            LocalServerImpl server = new LocalServerImpl();

            server.register_server(serverName);

        } catch(Exception e) {
            System.err.println(e);
        }
    }

}
