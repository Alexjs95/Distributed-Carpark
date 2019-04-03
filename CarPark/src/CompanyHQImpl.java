import CarPark.*;
import CarPark.LocalServer;

import java.util.ArrayList;

public class CompanyHQImpl extends CompanyHQPOA {

    public static ArrayList<LocalServers> servers;
    public static ArrayList<Alerts> alerts;

    public CompanyHQImpl() {
        servers = new ArrayList<LocalServers>();
        alerts = new ArrayList<Alerts>();
    }

    @Override
    public LocalServers[] server_log() {
        return (LocalServers[])servers.toArray();
    }

    @Override
    public Alerts[] return_alerts() {
        return (Alerts[])alerts.toArray();
    }

    @Override
    public void raise_alarm(Alerts alert) {
        alerts.add(alert);
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

        Machines[] machines = localServer.machine_log();

        for (int j =0; j < machines.length; j++){
            stations.add(machines[j]);
        }

        Machines[] mach = new Machines[stations.size()];
        stations.toArray(mach);
        return mach;
    }
}
