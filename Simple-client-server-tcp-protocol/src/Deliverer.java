import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Deliverer implements Runnable{

	private Socket socket;
	private AirportData data;

	public Deliverer(Socket aSocket, AirportData ad){
		this.socket = aSocket;
		this.data = ad;
	}
	@Override
	public void run() {
		System.out.println("Connected: " + socket);
		String clientCommand = "";
		try {
			Scanner in = new Scanner(socket.getInputStream());
			
			//while (in.hasNextLine()) {
				clientCommand = in.nextLine();
			//}
			System.out.println("clientCommand="+clientCommand);
			String l[];
			if(clientCommand.startsWith("READ"))
			{System.out.println("Client command starts with READ");
				System.out.println("clientCommand.substring(6) = "+clientCommand.substring(6,clientCommand.length()-1));
				l = data.getLine(clientCommand.substring(6,clientCommand.length()-1));
				//l now looks like: l["XY4352","Arrival","12:40"]
				System.out.println("The line found was: l[0] = "+l[0]+", l[1] = "+l[1]+", l[2] = "+l[2]);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				if(l.equals(null))
					out.println("RERR");
				else
					out.println("ROK <"+l[0]+"> <"+l[1]+"> <"+l[2]+">");
			}
			else
			{
				
			}
				


		} catch (Exception e) {
			System.out.println("Error:" + socket);
		} finally {
			try { socket.close(); } catch (IOException e) {}
			System.out.println("Closed: " + socket);
		}
	}



}
