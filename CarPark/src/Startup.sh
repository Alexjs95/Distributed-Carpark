javac *.java

java Headquarters -name HQ -localservers 1 server001 -ORBInitialPort 1075 &
sleep .9
java LocalServer -name server001 -entrygate 3 001 002 003 -exitgate 2 001 002 -paystation 4 010 011 012 013 -ORBInitialPort 1075 &
sleep .9
java EntryGateClient -name 001 -ORBInitialPort 1075 &
sleep .9
java EntryGateClient -name 002 -ORBInitialPort 1075 &
sleep .9
java EntryGateClient -name 003 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name 010 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name 011 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name 012 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name 013 -ORBInitialPort 1075 &