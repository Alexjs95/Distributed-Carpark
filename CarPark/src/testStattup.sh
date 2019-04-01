#!/usr/bin/env bash
javac *.java
sleep .5
java CompanyHQ -ORBInitialPort 1075 &
sleep .5
java LocalServer -name server001 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry001 -server server001 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry002 -server server001 -ORBInitialPort 1075 &
sleep .5