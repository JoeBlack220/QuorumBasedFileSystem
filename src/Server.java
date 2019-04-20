import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TTransportFactory;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import java.io.*;
import java.util.*;
import java.net.InetAddress;
import java.lang.Thread;
public class Server{
	public static ServerServiceHandler handler;
	public static ServerService.Processor processor;
	public static void main(String[] args){
		try{
			InetAddress localhost;
			String curIp;
			int curPort, numberRead, numberWrite;
			localhost = InetAddress.getLocalHost();
			curIp = (localhost.getHostAddress()).trim();
			System.out.println("The current IP address of this node is: " + curIp + ".");
			boolean isCoor;
			Scanner sc = new Scanner(System.in);
			System.out.println("Are you the coordinator? (y/n)");
			isCoor =  ("y".equalsIgnoreCase(sc.nextLine()));
			if(isCoor){
				System.out.println("This node now act as the coordinator.");
				System.out.println("Please enter the port number of the node: ");
				curPort = Integer.parseInt(sc.nextLine());
				handler = new ServerServiceHandler(curIp, curPort, curIp, curPort);
			}
			else{
				String coorIp;
				int coorPort;
				System.out.println("Please enter the port number of the node: ");
				curPort = Integer.parseInt(sc.nextLine());
				System.out.println("Please enter the ip address of the coordinator: ");
				coorIp = sc.nextLine();
				System.out.println("Please enter the port number of the coordinator: ");
				coorPort = Integer.parseInt(sc.nextLine());
				handler = new ServerServiceHandler(curIp, curPort, coorIp, coorPort);
			}
			processor = new ServerService.Processor(handler);
			Runnable simple = new Runnable(){
				public void run(){
					simple(processor);
				}
			};
			new Thread(simple).start();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void simple(ServerService.Processor processor){
		try{
			System.out.println("Finished setting up the server.");
			// Create Thrift server socket
			TServerTransport serverTransport = new TServerSocket(handler.getCurPort());
			TTransportFactory factory = new TFramedTransport.Factory();
			// Set server arguments
			TServer.Args args = new TServer.Args(serverTransport);
			args.processor(processor);  //Set handler
			args.transportFactory(factory);  //Set FramedTransport (for performance)
			//Run server as a multithread server
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
			System.out.println("Start to serve as a file server node.");
			server.serve();			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}