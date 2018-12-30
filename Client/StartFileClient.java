import java.rmi.Naming;
import java.util.*;


public class StartFileClient {

	public static void main(String[] args) {
		try{

			FileServerInt server = (FileServerInt)Naming.lookup("rmi://server/abc");

			System.out.println("Receiving file.....");
			FileClient fc = new FileClient();
			server.createFile();
			server.checksum();
			server.sendFile(fc);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
