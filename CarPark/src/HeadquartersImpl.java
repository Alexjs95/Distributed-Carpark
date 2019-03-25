import CarPark.CompanyHQPOA;
import CarPark.LocalServers;
import CarPark.Machines;
import CarPark.VehicleEvent;

import java.util.ArrayList;

public class HeadquartersImpl extends CompanyHQPOA {

    public static ArrayList<LocalServers> servers;

    public HeadquartersImpl() {
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
    public void register_local_server(LocalServers server) {
        servers.add(server);
        System.out.println("local server registered: " + server.location);
    }

    @Override
    public Machines[] return_machines() {
        Machines[] stations = new Machines[LocalServerImpl.machines.size()];
        LocalServerImpl.machines.toArray(stations);
        return stations;
    }
}
