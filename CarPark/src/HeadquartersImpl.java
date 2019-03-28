import CarPark.*;

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

    @Override
    public void turn_machine_on(String type) {
        EntryGateImpl entryImpl = new EntryGateImpl();
        ExitGateImpl exitImpl = new ExitGateImpl();
        PayStationImpl payImpl = new PayStationImpl();

        if (type.contains("Entry")) {
            entryImpl.turn_on();
        } else if (type.contains("Exit")) {
            exitImpl.turn_on();
        } else if (type.contains("Pay")) {
            payImpl.turn_on();
        }
    }

    @Override
    public void turn_machine_off(String type) {
        EntryGateImpl entryImpl = new EntryGateImpl();
        ExitGateImpl exitImpl = new ExitGateImpl();
        PayStationImpl payImpl = new PayStationImpl();

        if (type.contains("Entry")) {
            entryImpl.turn_off();
        } else if (type.contains("Exit")) {
            exitImpl.turn_off();
        } else if (type.contains("Pay")) {
            payImpl.turn_off();
        }
    }

    @Override
    public void reset_machine(String type) {
        EntryGateImpl entryImpl = new EntryGateImpl();
        ExitGateImpl exitImpl = new ExitGateImpl();
        PayStationImpl payImpl = new PayStationImpl();
        if (type.contains("Entry")) {
            entryImpl.reset();
        } else if (type.contains("Exit")) {
            exitImpl.reset();
        } else if (type.contains("Pay")) {
            payImpl.reset();
        }
    }
}
