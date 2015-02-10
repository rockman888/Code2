import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer 
{
	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub		
		
		Socket client = null;
		ServerSocket server = null;
		
		try{
			server = new ServerSocket(6666);
			System.out.print("Server Stared ... ");
			client = server.accept();
		}
		catch(Exception ex){}
		
		// read a display the message
		
		if (client == null)
			return;
				
		Scanner scann = new Scanner(client.getInputStream());
		String szMsg;
			
		while (true)
		{
			if (scann.hasNext())
			{
				szMsg = scann.nextLine();
				System.out.println("Client Message: " + szMsg);
			}
		}
	}
}
