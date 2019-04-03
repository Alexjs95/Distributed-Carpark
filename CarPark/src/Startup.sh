#!/usr/bin/env bash
javac *.java
sleep .5
java CompanyHQ -ORBInitialPort 1075 &
sleep .5
java LocalServer -name server001 -spaces 100 -ORBInitialPort 1075 &
sleep .5
java LocalServer -name server002 -spaces 200 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry001 -server server001 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry002 -server server001 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name Pay001 -server server001 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name Pay002 -server server001 -ORBInitialPort 1075 &
sleep .5
java ExitGateClient -name Exit001 -server server001 -ORBInitialPort 1075 &
sleep .5
java ExitGateClient -name Exit002 -server server001 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry003 -server server002 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry004 -server server002 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name Pay003 -server server002 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name Pay004 -server server002 -ORBInitialPort 1075 &
sleep .5
java ExitGateClient -name Exit003 -server server002 -ORBInitialPort 1075 &
sleep .5
java ExitGateClient -name Exit004 -server server002 -ORBInitialPort 1075


