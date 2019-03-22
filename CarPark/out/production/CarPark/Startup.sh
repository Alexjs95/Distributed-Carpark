javac *.java

java Headquarters -ORBInitialPort 1075 &
java LocalServer lServer -ORBInitialPort 1075 &
java EntryGateClient eGate -ORBInitialPort 1075 &
java PayStationClient pStation -ORBInitialPort 1075