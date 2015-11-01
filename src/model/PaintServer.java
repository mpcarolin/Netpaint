package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.security.sasl.SaslServer;

public class PaintServer {
	
	public static final int SERVER_PORT = 9001;
	private static ServerSocket server;
	private static List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList<>());
	private static Vector<PaintObject> pics = new Vector<PaintObject>();
	

	public static void main(String[] args) throws IOException {
		server = new ServerSocket(SERVER_PORT);
		Socket client = null;
		System.out.println("Server started on port " + SERVER_PORT);
		
		while (true) {
			client = server.accept();
			
			ObjectInputStream fromClient = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream toClient = new ObjectOutputStream(client.getOutputStream());
			
			clients.add(toClient);
			
			ClientHandler handler = new ClientHandler(fromClient, clients, pics);
			handler.start();

		}
	}
}
	
 class ClientHandler extends Thread {


	private ObjectInputStream input;
	private List<ObjectOutputStream> clients;
	private Vector<PaintObject> paintObjects;

	public ClientHandler(ObjectInputStream fromClient, List<ObjectOutputStream> clients, Vector<PaintObject> pics) { 
		this.input = fromClient;
		this.clients = clients;
		this.paintObjects = pics;
		writePaintListToClients(pics);	// update new client with prior pictures
	}
	
	
	@Override
	public void run(){
		System.out.println("Another client changes");
		while (true){
			try {
				PaintObject currentObject = (PaintObject) input.readObject();
				paintObjects.add(currentObject);
				System.out.println("Thread pic: " + currentObject);
				writePaintListToClients(paintObjects);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// writes all of the paintObjects currently stored by the server to all clients
	private void writePaintObjectToClients(PaintObject paintObject) {
		for (ObjectOutputStream client : clients) {
			try {
				((ObjectOutputStream)client).writeObject(paintObject);
				((ObjectOutputStream)client).reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void writePaintListToClients(Vector<PaintObject> pics) {
		for (ObjectOutputStream client : clients) {
			try {
				((ObjectOutputStream)client).writeObject(pics);
				((ObjectOutputStream)client).reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

