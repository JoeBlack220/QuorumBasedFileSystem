import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import java.io.*;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerServiceHandler implements ServerService.Iface{
	private ConnectionInfo cur;
	private ConnectionInfo coor;
	private ArrayList<ConnectionInfo> allServers = new ArrayList();
	private HashMap<String, FileInfo> allFiles = new HashMap<>();
	private HashMap<String, Vector<Date>> taskQueue = new HashMap<>();
	private int numberWrite;
	private int numberRead;
	private boolean isCoor = false;
	public ServerServiceHandler(String curIp, int curPort, String coorIp, int coorPort){
		cur = new ConnectionInfo();
		cur.nodeIp = curIp;
		cur.nodePort = curPort;
		coor = new ConnectionInfo();
		coor.nodeIp = coorIp;
		coor.nodePort = coorPort;
		if(curPort == coorPort && curIp.equals(coorIp)){
			// this node is coordinator
			allServers.add(cur);
			isCoor = true;
			numberRead = allServers.size();
			numberWrite = allServers.size();
			System.out.println("This node is coordinator.");
			System.out.println("Now the file system has following servers");
			printAllServers(allServers);
		}
		else{
			// this node is not the coordinator
			// connect the coordinator to join the file system
			try{
					TTransport  transport = new TSocket(coor.nodeIp, coor.nodePort);
					TBinaryProtocol protocol = new TBinaryProtocol(transport);
					ServerService.Client client = new ServerService.Client(protocol);
					//Try to connect
					transport.open();
					System.out.println("Trying to contact the coordinator to join the file system.");
					if(client.join(cur)) System.out.println("Successfully joined the file system.");
					else System.out.println("Didn't join the file system.");
					System.out.println("Finished joining the file system.");
					transport.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	// used for non-coordinator node to join the file system
	@Override
	public boolean join(ConnectionInfo node) throws TException{
		allServers.add(node);
		System.out.println("Now the system has following servers: ");
		printAllServers(allServers);
		numberRead = allServers.size();
		numberWrite = allServers.size();
		return true;
	}
	// set the read number and the write number when initialize the coordinator
	@Override
	public boolean setNwNr(int nw, int nr, String mode){
		boolean ret = true;
		if(mode.equals("node")){
			System.out.println("Trying to set the nw to " + nw + " and set nr to " + nr + ".");
			numberWrite = nw;
			numberRead = nr;
			System.out.println("Successfully set the nw to " + nw + " and set nr to " + nr + ".");			
		}
		else if (mode.equals("coor")){
			if((nr + nw) < allServers.size() || nw <= (allServers.size()/2) || nw > allServers.size() || nr > allServers.size())
				ret = false;
			else{
				numberWrite = nw;
				numberRead = nr;
				ret = true;
				// nw and nr requires the conditions
				// set all the servers in the files system
				for(ConnectionInfo node : allServers){
					try{
						TTransport  transport = new TSocket(node.nodeIp, node.nodePort);
						TBinaryProtocol protocol = new TBinaryProtocol(transport);
						ServerService.Client client = new ServerService.Client(protocol);
						//Try to connect
						transport.open();
						System.out.println("Setting nw and nr to server with ip: " + node.nodeIp + " with port: " + node.nodePort );
						client.setNwNr(nw, nr, "node");
						transport.close();
					} catch(Exception e){
						e.printStackTrace();
					}
				}
			}	
			System.out.println("Finished setting.");
		}
		else{
			// user mode, try to contact the coordinator to set nw and nr
			try{
				TTransport transport = new TSocket(coor.nodeIp, coor.nodePort);
				TBinaryProtocol protocol = new TBinaryProtocol(transport);
				ServerService.Client client = new ServerService.Client(protocol);
				transport.open();
				if(client.setNwNr(nw, nr, "coor")) {
					System.out.println("Successfully set the nw and nr.");
					transport.close();
					ret = true;
				}
				else{
					System.out.println("Some thing wrong with the nw and nr you give.");
					transport.close();
					ret = false;
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return ret;
	}
	@Override
	public boolean write(String fileName, String fileContent, String mode){
		boolean ret = false;
		if(mode.equals("coor")){
			// if the server doesn't have the file, other file servers also don't have it
			// straightly return false
			if(!allFiles.containsKey(fileName)) return ret;
			// used for examine whether we can proceed this task now
			Date signature =  new Date();
			taskQueue.get(fileName).add(signature);
			while(!taskQueue.get(fileName).firstElement().equals(signature)){
				System.out.println("The system now has other operation on file: " + fileName);
				System.out.println("Wait for 0.1 second.");
				try{
					Thread.currentThread().sleep(100);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			int curVer = -1;
			FileInfo curFile = null;
			List<ConnectionInfo> curSet = selectSub(numberWrite);
			System.out.println("Now randomly select " + numberWrite + " file servers for write operation.");
			System.out.println("The following servers are selected.");
			printAllServers(curSet);
			for(ConnectionInfo node: curSet){
				try{
					TTransport  transport = new TSocket(node.nodeIp, node.nodePort);
					TBinaryProtocol protocol = new TBinaryProtocol(transport);
					ServerService.Client client = new ServerService.Client(protocol);
					//Try to connect
					transport.open();
					curFile = client.read(fileName, "node");
					// finding the newest copies of the file among the chosen set
					if (curFile.fileVersion > curVer ){
						curVer = curFile.fileVersion;
					}
					transport.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			System.out.println("The newest copy of those servers has content: \n" + curFile.fileContent);
			System.out.println("Now writing new content to the files.");
			curFile.fileVersion ++;
			curFile.fileContent = curFile.fileContent + "\n" + fileContent + " version: " + curFile.fileVersion;
			System.out.println("Now updating them to the previously selected servers.");
			// update the newest copy to the prevously selected sub set of servers
			for(ConnectionInfo node: curSet){
				try{
					TTransport  transport = new TSocket(node.nodeIp, node.nodePort);
					TBinaryProtocol protocol = new TBinaryProtocol(transport);
					ServerService.Client client = new ServerService.Client(protocol);
					transport.open();
					client.update(curFile);
					transport.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			taskQueue.get(fileName).remove(0);
			ret = true;
			System.out.println("Finished writing the file: " + curFile.fileName + ".");
			System.out.println("Now the system can proceed other operations.");
		}
		// if read was called by user
		else{
			try{
				TTransport transport = new TSocket(coor.nodeIp, coor.nodePort);
				TBinaryProtocol protocol = new TBinaryProtocol(transport);
				ServerService.Client client = new ServerService.Client(protocol);
				transport.open();
				ret = client.write(fileName, fileContent, "coor");
				transport.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return ret;
	}
	@Override
	public void update(FileInfo curFile){
		System.out.println("Start update the content of file: " + curFile.fileName);
		allFiles.put(curFile.fileName, curFile);
	}
	@Override
	public void upload(String fileName, String fileContent, String mode){
		if(mode.equals("node")){
			FileInfo curFile = new FileInfo();
			curFile.fileName = fileName;
			curFile.fileContent = fileContent + " version: " + "0";
			curFile.fileVersion = 0;
			allFiles.put(fileName, curFile);
			if(isCoor){
				taskQueue.put(fileName, new Vector<>());
			}
			List<FileInfo> files = new ArrayList<>(allFiles.values());
			printAllFiles(files);
		}
		else if (mode.equals("coor")){
			for(ConnectionInfo node : allServers){
				try{
					TTransport  transport = new TSocket(node.nodeIp, node.nodePort);
					TBinaryProtocol protocol = new TBinaryProtocol(transport);
					ServerService.Client client = new ServerService.Client(protocol);
					//Try to connect
					transport.open();
					System.out.println("Upload files to node with ip: " + node.nodeIp + " with port: " + node.nodePort );
					client.upload(fileName, fileContent, "node");
					transport.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}	
			System.out.println("Finished setting.");
		}
		else{
			// user mode, try to contact the coordinator to set nw and nr
			try{
				TTransport transport = new TSocket(coor.nodeIp, coor.nodePort);
				TBinaryProtocol protocol = new TBinaryProtocol(transport);
				ServerService.Client client = new ServerService.Client(protocol);
				transport.open();
				client.upload(fileName, fileContent, "coor");
				transport.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	@Override
	public FileInfo read(String fileName, String mode){
		FileInfo ret = new FileInfo();
		ret.fileName = fileName;
		ret.fileContent = "not found";
		if(mode.equals("node")){
			if(!allFiles.containsKey(fileName)) return ret;
			else ret = allFiles.get(fileName);
		}
		else if(mode.equals("coor")){
			if(!allFiles.containsKey(fileName)) return ret;
			Date signature =  new Date();
			taskQueue.get(fileName).add(signature);
			while(!taskQueue.get(fileName).firstElement().equals(signature)){
				System.out.println("The system now has other operation on file: " + fileName);
				System.out.println("Wait for 0.1 second.");
				try{
					Thread.currentThread().sleep(1000);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			int curVer = -1;
			FileInfo curFile = null;
			List<ConnectionInfo> curSet = selectSub(numberRead);
			System.out.println("We randomly choose the following servers for this read operation.");
			printAllServers(curSet);
			for(ConnectionInfo node: curSet){
				try{
					TTransport  transport = new TSocket(node.nodeIp, node.nodePort);
					TBinaryProtocol protocol = new TBinaryProtocol(transport);
					ServerService.Client client = new ServerService.Client(protocol);
					//Try to connect
					transport.open();
					System.out.println("Read file in node with ip: " + node.nodeIp + " with port: " + node.nodePort );
					curFile = client.read(fileName, "node");
					if (curFile.fileVersion > curVer ){
						curVer = curFile.fileVersion;
						ret = curFile;
					}
					transport.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			taskQueue.get(fileName).remove(0);
			System.out.println("Finished reading the file: " + curFile.fileName + ".");
			System.out.println("Now the system can proceed other operations.");
		}
		// if read was called by user
		else{
			try{
				TTransport transport = new TSocket(coor.nodeIp, coor.nodePort);
				TBinaryProtocol protocol = new TBinaryProtocol(transport);
				ServerService.Client client = new ServerService.Client(protocol);
				transport.open();
				ret = client.read(fileName, "coor");
				transport.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return ret;
	}

	public Map<String, FileInfo> getFiles(){
		return allFiles;
	}
	public List<ConnectionInfo> selectSub(int num){
		HashSet<Integer> exist = new HashSet();
		ArrayList<ConnectionInfo> sub = new ArrayList();
		Random rand = new Random();
		int i = 0;
		while(i < num){
			int cur = rand.nextInt(allServers.size());
			if (!exist.contains(cur)){
				exist.add(cur);
				sub.add(allServers.get(i));
				++ i;
			}
		}
		return sub;
	}
	public FileInfo readContent(String name){
		if (allFiles.containsKey(name)) return allFiles.get(name);
		else return null;
	}
	public void updateFile(FileInfo curFile){
		allFiles.put(curFile.fileName, curFile);
	}
	public boolean appendFile(String name, String content){
		if(allFiles.containsKey(name)) {
			allFiles.get(name).fileContent += content;
			return true;
		}
		else return false;
	}
	public int getCurPort(){
		return cur.nodePort;
	}
	public void printAllServers(List<ConnectionInfo> toPrint){
		int i = 1;
		for(ConnectionInfo node: toPrint){
			System.out.println("Servers " + i + " with IP address: " + node.nodeIp + " with port: " + node.nodePort);
			++ i;
		}
	}
	public void printAllFiles(List<FileInfo> toPrint){
		System.out.println("Now the file system has " + toPrint.size() + " files.");
		for(FileInfo file: toPrint){
			System.out.println("File name : " + file.fileName + " content: " + file.fileContent );
		}
	}
}