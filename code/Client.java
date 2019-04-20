import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import java.io.*;
import java.util.*;
public class Client{
	public static void main(String[] args){
		try{
			Scanner sc = new Scanner(System.in);
			String serverIp, operationMode;
			int serverPort, nw, nr;
			BufferedReader reader = null;
			boolean setFlag = false, inputFlag = false, exitFlag = false;
			Date operStart, operEnd;
			System.out.println("Please enter the server's ip: ");
			serverIp = sc.nextLine();
			System.out.println("Please enter the server's port number: ");
			serverPort = Integer.parseInt(sc.nextLine());
			TTransport transport = new TSocket(serverIp, serverPort);
			TProtocol protocol = new TBinaryProtocol(transport);
			ServerService.Client client = new ServerService.Client(protocol);
			transport.open();
			System.out.println("Do you want to set the nw and nr?(y/n)");
			setFlag = "n".equalsIgnoreCase(sc.nextLine());
			while(!setFlag){
				System.out.println("Please enter nw: ");
				nw = Integer.parseInt(sc.nextLine());
				System.out.println("Please enter nr: ");
				nr = Integer.parseInt(sc.nextLine());
				if(setFlag = client.setNwNr(nw, nr, "user")){
					System.out.println("Successfully set nw and nr to the file system.");		
				}
				else{
					System.out.println("Something wrong with the nw and nr you input, please enter again.");
				}				
			}
			System.out.println("Finished entering nw and nr.");
			System.out.println("------------------------------------");
			System.out.println("Start uploading the files.");
			System.out.println("Do you want to upload more files to the files system?(y/n)");
			inputFlag = "n".equalsIgnoreCase(sc.nextLine());
			String curFileName;
			String curFileContent;
			String inputDir;
			String curFile;
			String[] curFileParsed;
			if(!inputFlag){
				System.out.println("Please enter the path of the files data: ");
				inputDir = sc.nextLine();
				File f = new File(inputDir);
				while(!f.isFile()){
					System.out.println("What you have just entered is not a file, please enter again: ");
					inputDir = sc.nextLine();
					f = new File(inputDir);
				}
				try{
					reader = new BufferedReader(new FileReader(f));
					while((curFile = reader.readLine()) != null ){
						curFileParsed = curFile.split(", ");
						if(curFileParsed.length < 2) {
							System.out.println("The content of file " + curFileParsed[0] + " is missing.");
							System.out.println("Set this file's content to empty.");
							curFileName = curFileParsed[0];
							curFileContent = "";
						}
						else {
							curFileName = curFileParsed[0];
							curFileContent = curFileParsed[1];
						}
						System.out.println("Setting the file with name: " + curFileName + "and content: " + curFileContent + ".");
						// call set function of node here:
						client.upload(curFileName, curFileContent, "user");						
					}
					System.out.println("Setting finished");
				}
				catch (Exception e){
					System.err.println("Something wrong with the input file, end setting.");
				}
			}
			System.out.println("Finished uploading the files.");
			System.out.println("--------------------------------------");
			while(!exitFlag){
				System.out.println("Please enter the path of the operation data: ");
				inputDir = sc.nextLine();
				File f = new File(inputDir);
				while(!f.isFile()){
					System.out.println("What you have just entered is not a file, please enter again: ");
					inputDir = sc.nextLine();
					f = new File(inputDir);
				}
				try{
					reader = new BufferedReader(new FileReader(f));
					Date startTime = new Date();
					while((curFile = reader.readLine()) != null ){
						curFileParsed = curFile.split(", ");
						if(curFileParsed[0].equalsIgnoreCase("read")){
							FileInfo ret;
							if(curFileParsed.length < 2){
								continue;
							}
							else{
								curFileName = curFileParsed[1];
							}
							System.out.println("Reading file: " + curFileName + " from the system.");
							ret = client.read(curFileName, "user");
							if(ret.fileContent.equalsIgnoreCase("not found")){
								System.out.println("No files with name: " + curFileName + " in the file system currently.");
							}
							else {
								System.out.println("File name: " + ret.fileName + " file content: \n" + ret.fileContent);
							}
						}
						if(curFileParsed[0].equalsIgnoreCase("write")){
							if(curFileParsed.length < 3){
								continue;
							}
							else{
								curFileName = curFileParsed[1];
								curFileContent = curFileParsed[2];
							}
							System.out.println("Writing to file: " + curFileName + " with content: " + curFileContent);
							boolean succFlag = client.write(curFileName, curFileContent, "user");
							if(!succFlag){
								System.out.println("No files with name: " + curFileName + " in the file system currently.");
							}
							else {
								System.out.println("File name: " + curFileName + " has been updated.");
							}
						}						
					}
					Date endTime = new Date();
					System.out.println("After " + (endTime.getTime() - startTime.getTime()) + " milliseconds, all operations are done.");
					System.out.println("Do you want to do another operations?(y/n)");
					exitFlag = "n".equals(sc.nextLine());
				}
				catch (Exception e){
					e.printStackTrace();
				}
				System.out.println("Finished last operation.");
			}

		} catch (Exception e){
			e.printStackTrace();
		} 
	}
}