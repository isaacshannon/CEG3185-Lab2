import java.awt.*;
import java.applet.*;
import java.net.*;
import javax.swing.*;
import java.io.*;

public class MyServer {

	public static void main(String[] args) throws IOException {

		//
		// sockets and other variables declaration
		//
		// maximum number of clients connected: 10
		//

		ServerSocket serverSocket = null;
		Socket[] client_sockets;
		client_sockets = new Socket[10];
		String inputLine = null;
		String outputLine = null;
		PrintWriter[] s_out;
		s_out = new PrintWriter[10];
		BufferedReader[] s_in;
		s_in = new BufferedReader[10];



		//
		//get port number from the command line
		//
		int nPort = 4444; // default 
		//nPort = Integer.parseInt(args[0]);

		boolean bListening = true;

		String[] sMessages;
		sMessages = new String[10];
		int nMsg = 0;
		boolean bAnyMsg = false;
		boolean bAlive = false;

		//
		// initialize some var's for array handling
		//
		int s_count = 0;
		int i = 0;
		int k = 0;
		int j = 0;

		//
		// create server socket
		//
		try {
			serverSocket = new ServerSocket(nPort);

		} catch (IOException e) {
			System.err.println("Could not listen on port: " + args[0]);
			System.exit(-1);
		}

		//
		// this variable defines how many clients are connected
		//
		int nClient = 0;

		//
		// set timeout on the socket so the program does not
		// hang up
		//
		serverSocket.setSoTimeout(1000);

		//
		// main server loop
		//
		System.out.println("Server starting up...");
		System.out.println("Looking for new clients");
		while (bListening){

			bAlive = false;

			try {
				//System.out.println("looking for new clients");
				//
				// trying to listen to the socket to accept
				// clients
				// if there is nobody to connect, exception will be
				// thrown - set by setSoTimeout()
				//
				client_sockets[s_count]=serverSocket.accept();

				//
				// connection got accepted
				// 
				if (client_sockets[s_count]!=null){
					System.out.println("Connection from " +
							client_sockets[s_count].getInetAddress() + " accepted.");
					client_sockets[s_count].setSoTimeout(1000);
					System.out.println("accepted client");
					s_out[s_count] = new PrintWriter(client_sockets[s_count].getOutputStream(),true);
					s_in[s_count] = new BufferedReader(new InputStreamReader(client_sockets[s_count].getInputStream()));

					//
					// set server message about new client connection
					//

					bAnyMsg = true;
					sMessages[nMsg]=" " + s_in[s_count].readLine() + " joined";
					nMsg ++;

					// 
					// increment count of clients
					//
					s_count++;
				}
			}
			catch (InterruptedIOException e) {}

			//System.out.println(" ");

			//
			// is there anything to send
			//
			if (bAnyMsg)
			{
				System.out.println("Sending messages - select stations");

				//
				// select stations
				//
				for (i=0;i<s_count;i++)
					for (j=0;j<nMsg;j++)
					{

						s_out[i].println("SEL" + sMessages[j]);

						System.out.println("writing to station!");
					}
				//
				// all messages sent - clear messages array
				//
				for (j=0;j<nMsg;j++)
					sMessages[j] = null;
				bAnyMsg = false;
				nMsg = 0;
			}

			if (s_count >0){
				//System.out.println("Polling stations!");

				// poll stations
				for (i=0;i<s_count;i++){
					//
					// send POLL type of request
					//

					//s_out[i].println("POL");


					inputLine = null;

					try {
						//
						// read respose from the client
						//
						inputLine = s_in[i].readLine();

					} catch (InterruptedIOException e) {
						inputLine = null;}
					catch (java.net.SocketException e) {}

					if(inputLine != null){
						System.out.println("Got message: " + inputLine);
					}


				}

			} 

			//
			// stop server automatically when
			// all clients disconnect
			//
			// no active clients
			//
			if (s_count < 1){
				System.out.println("All clients are disconnected");
				//bListening = false;
			}

		}// end of while loop

		//
		// close all sockets
		//

		for (i=0;i<s_count;i++){
			client_sockets[i].close();
		}

		serverSocket.close();

	}// end main 
}// end of class MyServer
