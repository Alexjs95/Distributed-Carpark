javac *.java

java Headquarters -ORBInitialPort 1075 &
sleep .5 &
java LocalServer lServer -ORBInitialPort 1075 &
sleep .5  &
java EntryGateClient eGate -ORBInitialPort 1075 &
sleep .5  &
java PayStationClient pStation -ORBInitialPort 1075