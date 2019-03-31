import CarPark.*;
import CarPark.CompanyHQ;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class LocalServer {
    public static String serverName;


    public static void main(String args[]) {
        try {
            for (int i = 0; i < args.length; i ++) {
                if (args[i].equals("-name")) {
                    serverName = args[i + 1];
                }
            }

            ORB orb = ORB.init(args, null);

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                System.out.println("nameServiceObj = null");
                return;
            }

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                System.out.println("nameService = null");
                return;
            }

            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            LocalServerImpl serverImpl = new LocalServerImpl();

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(serverImpl);

            String stringified_ior = orb.object_to_string(ref);

            CarPark.LocalServer cref = LocalServerHelper.narrow(ref);

            // bind the server name object in the Naming service
            NameComponent[] sName = nameService.to_name(serverName);
            nameService.rebind(sName, cref);

            CarPark.LocalServer localServer = LocalServerHelper.narrow(nameService.resolve_str(serverName));

            CompanyHQ hq = CompanyHQHelper.narrow(nameService.resolve_str("HQ"));

            localServer.register_server(serverName,200, hq);
            hq.register_local_server(serverName, stringified_ior, localServer);


            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
