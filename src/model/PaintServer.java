package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class PaintServer {
	
	public static final int SERVER_PORT = 9001;
	private static ServerSocket server;
	private static List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList<>());

	public static void main(String[] args) throws IOException {
		server = new ServerSocket(SERVER_PORT);
		Socket client = null;
		System.out.println("Server started on port " + SERVER_PORT);
		
		while (true) {
			client = server.accept();
			
			ObjectInputStream fromClient = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream toClient = new ObjectOutputStream(client.getOutputStream());
			
			clients.add(toClient);
			
			PaintObjectList pics = new PaintObjectList();

			
			ClientHandler handler = new ClientHandler(fromClient, clients, pics);
			handler.start();
		}
	}
}
	
class ClientHandler extends Thread {

	private ObjectInputStream input;
	private List<ObjectOutputStream> clients;
	private PaintObjectList paintObjects;

	public ClientHandler(ObjectInputStream fromClient, List<ObjectOutputStream> clients, PaintObjectList pics) { 
		this.input = fromClient;
		this.clients = clients;
		this.paintObjects = pics;
		//writePaintListToClients(pics);
	}
	@Override
	public void run(){
		while (true){
			try {
				PaintObject currentObject = (PaintObject) input.readObject();
				paintObjects.add(currentObject);
				//writePaintObjectToClients(currentObject);
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
	
	private void writePaintListToClients(PaintObjectList pics) {
		for (ObjectOutputStream client : clients) {
			try {
				((ObjectOutputStream)client).writeObject(pics);
				//((ObjectOutputStream)client).reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// when a new client logs on, send it all of the previously drawn pictures
//	private void updateNewClient() {
//		if(clients.size()!=1){
//		for (PaintObject pic : paintObjects) {
//			try {
//				clients.get(clients.size()-1).writeObject(pic);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		}
//
//	}
	
}
