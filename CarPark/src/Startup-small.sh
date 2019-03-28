javac *.java
sleep 1.0
java Headquarters -name HQ -localservers 2 server001 server002 -ORBInitialPort 1075 &
sleep .5
java LocalServer -name server001 -entrygate 2 Entry1 Entry2  -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry1 -ORBInitialPort 1075 &
sleep .5
java EntryGateClient -name Entry2 -ORBInitialPort 1075
