thrift --gen java FileSystem.thrift
mv ./gen-java/* ./
javac -cp ".:/usr/local/Thrift/*" Server.java -d .
javac -cp ".:/usr/local/Thrift/*" Client.java -d .
