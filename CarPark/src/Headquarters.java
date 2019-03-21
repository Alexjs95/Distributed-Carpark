import CarPark.CompanyHQ;
import CarPark.CompanyHQHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.*;

public class Headquarters extends JFrame {



    public Headquarters() {


    }


    public static void main(String[] args) {
        Headquarters headquarters = new Headquarters();

        try {
            // Initialize the ORB
            System.out.println("Initializing the ORB");
            ORB orb = ORB.init(args, null);

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj =
                    orb.resolve_initial_references("NameService");
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

            String name = "Headquarters";
            CompanyHQ hq = CompanyHQHelper.narrow(nameService.resolve_str(name));




        } catch (Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }
}
