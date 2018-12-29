import java.rmi.Naming;

public class StartFileServer {
	public static void main(String[] args) {

		try{

			java.rmi.registry.LocateRegistry.createRegistry(1099);

			FileServer fs = new FileServer();
			Naming.rebind("rmi://localhost/abc", fs);
			System.out.println("File Server is Up!");
			fs.createFile();

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
