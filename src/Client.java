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
			boolean setFlag = false, inputFlag = false, readFlag = false, writeFlag = false, exitFlag = false;
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
			String inpuDir;
			String curFile;
			String[] curFileParsed
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
						if(bookSplited.length < 2) {
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
						res = client.upload(curFileName, curFileContent, "user");						
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
				System.out.println("Please enter next operation: (read/write/exit)");
				operationMode = sc.nextLine();
				// check the input to decide which opeartion to take next
				// if the user's input is none of the following three instruction
				// it will simply ask for input again
				exitFlag = operationMode.equalsIgnoreCase("exit");
				readFlag = operationMode.equalsIgnoreCase("read");
				writeFlag = operationMode.equalsIgnoreCase("write");
				if(readFlag){					
					System.out.println("In read mode, please enter the file name that you want to read.");
					curFileName = sc.nextLine();
					// record the start time of the operation
					operStart = new Date();
					FileInfo ret = client.read(curFileName, "user");
					// record the end time of the operation
					operEnd = new Date();
					System.out.println("The time taken by this read operation is: "
					 + (operEnd.getTime() - operStart.getTime()) + " milliseconds.");
					if(ret.fileContent.equalsIgnoreCase("not found")){
						System.out.println("No files with name: " + curFileName + " in the file system currently.");
					}
					else {
						System.out.println("File name: " + ret.fileName + " file content: \n" + ret.fileContent);
					}
				}
				if(writeFlag){
					System.out.println("In write mode, please enter the file name that you want to write.");
					curFileName = sc.nextLine();
					System.out.println("Please enter the content that you want to write to the file.");
					curFileContent = sc.nextLine();
					// record the start time of the operation
					operStart = new Date();
					boolean succFlag = client.write(curFileName, curFileContent, "user");
					// record the end time of the operation
					operEnd = new Date();
					System.out.println("The time taken by this write operation is: "
					 + (operEnd.getTime() - operStart.getTime()) + " milliseconds.");
					if(succFlag) System.out.println("Successfuly write content: " + curFileContent + " into file: " + curFileName);
					else System.out.println("Failed to write file: " + curFileName + ", this file may not exist.");
				}
				System.out.println("Finished last operation.");
				transport.close();
			}

		} catch (Exception e){
			e.printStackTrace();
		} 
	}
}