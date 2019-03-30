import CarPark.CompanyHQPOA;
import CarPark.LocalServer;
import CarPark.LocalServers;
import CarPark.Machines;
import CarPark.VehicleEvent;

import java.util.ArrayList;

public class CompanyHQImpl extends CompanyHQPOA {

    public static ArrayList<LocalServers> servers;

    public CompanyHQImpl() {
        servers = new ArrayList<LocalServers>();
    }

    @Override
    public LocalServers[] server_log() {
        return new LocalServers[0];
    }

    @Override
    public void raise_alarm(VehicleEvent event) {

    }

    @Override
    public void register_local_server(String name, String ior, LocalServer obj) {
        LocalServers localServer = new LocalServers();
        localServer.name = name;
        localServer.ior = ior;
        localServer.obj = obj;

        servers.add(localServer);
    }

    @Override
    public Machines[] return_machines(short server) {
        ArrayList<Machines> stations = new ArrayList<Machines>();
        LocalServer localServer = servers.get(server).obj;


        Machines[] machines = new Machines[localServer.machine_log().length];
        machines = ( Machines[]) localServer.machine_log();

        for (int j =0; j < machines.length; j++){
            stations.add(machines[j]);
        }

        Machines[] mach = new Machines[stations.size()];
        stations.toArray(mach);
        return mach;
    }
}
