javac *.java
sleep 1.0
java Headquarters -name HQ -localservers 1 server001 -ORBInitialPort 1075 &
sleep .5
java LocalServer -name server001 -entrygate 2 Entry1 Entry2 -exitgate 2 Exit1 Exit2 -paystation 2 Pay1 Pay2 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry1 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry2 -ORBInitialPort 1075 &
sleep .5
java ExitGateClient -name Exit1 -ORBInitialPort 1075 &
sleep .5
java ExitGateClient -name Exit2 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name Pay1 -ORBInitialPort 1075 &
sleep .5
java PayStationClient -name Pay2 -ORBInitialPort 1075
