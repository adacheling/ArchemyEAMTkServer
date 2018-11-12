import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class ArchemyEAMTkServer {
	public static void main(String[] args) throws Exception {
		System.out.println("The Archemy EAMTk Server is running.");
		int clientNumber = 0;
		int PORT = 9898;
		ServerSocket listener = new ServerSocket(PORT);
		try {
			while (true) {
				new ArchemyEAMTkClientHandler(listener.accept(), clientNumber++).start();
			}
		} finally {
			listener.close();
		}
	}
	
	private static class ArchemyEAMTkClientHandler extends Thread {
		private Socket socket;
		private int clientNumber;
		
		public ArchemyEAMTkClientHandler(Socket socket, int clientNumber) {
			this.socket = socket;
			this.clientNumber = clientNumber;
			log("New connection with client #: " + clientNumber + " at " + socket);
		}
		
		public void run() {
			try {
				BufferedReader in = new  BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				
				out.println("Hello, you are client #: " + clientNumber + ".");
				out.println("Enter a line with only a period to quit\n");
				
				while(true) {
					String input = in.readLine();
					if(input == null || input.equals(".")) {
						break;
					}
					handleRequest(input);
					//out.println(input.toUpperCase());
				}
			} catch (IOException e) {
				log("Error Hhandling client #: " + clientNumber + ": " + e);
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					log("Couldn't close a socket!");
				}
				log("Connection with client #: " + clientNumber + " closed.");
			}
		}
		private void log(String message) {
			System.out.println(message);
		}
	}
	
	private static void handleRequest(String input) throws MalformedURLException {
		int process_id = 0;
		int task_id = 0;
		URL processURL = new URL(input);
		 
		// parse URL to extract the process ID, and task ID (e.g., process_id=1?task_id=0)		 
		String[] tokens = processURL.getQuery().split("\\?");
		
		if(tokens[0].contains("process_id")) {
			String[] processIDs = tokens[0].split("=");
			process_id = new Integer(processIDs[1]).intValue();
		}
		if(tokens[1].contains("task_id")) {
			String[] taskIDs = tokens[1].split("=");
			task_id = new Integer(taskIDs[1]).intValue();
		}
		performTask(process_id, task_id);
	}
	
	private static void performTask(int processID, int taskID) {
		
		// DEBUG: for now, assuming that only dealing with one process and so process_id is not used at this point
		// use process ID and task ID to invoke the correct 
		switch (taskID) {
		//case 0: 
			// startup process with initiator and reason for running process
		//	System.out.println("Starting process with Process ID: " + processID);
		case 1:
			// invoke Login task
			System.out.println("Invoking 'Login' task with Task ID: " + taskID);
			break;
		case 2:
			// invoke Solution task
			System.out.println("Invoking 'Solution' task with Task ID: " + taskID);
			break;
		case 3:
			// invoke Build task
			System.out.println("Invoking 'Build' task with Task ID: " + taskID);
			break;
		case 4:
			// invoke Elicit task
			System.out.println("Invoking 'Elicit' task with Task ID: " + taskID);
			break;
		case 5: 
			// invoke Search task
			System.out.println("Invoking 'Search' task with Task ID: " + taskID);
			break;
		case 6: 
			// invoke Discover task
			System.out.println("Invoking 'Discover' task with Task ID: " + taskID);
			break;
		case 7:
			// invoke Discover (Review) task
			System.out.println("Invoking 'Discover (Review)' task with Task ID: " + taskID);
			break;
		case 8:
			// invoke Reuse task
			System.out.println("Invoking 'Reuse' task with Task ID: " + taskID);
			break;
		case 9:
			// invoke Adapt task
			System.out.println("Invoking 'Adapt' task with Task ID: " + taskID);
			break;
		case 10:
			// invoke Deploy task
			System.out.println("Invoking 'Deploy' task with Task ID: " + taskID);
			break;
		case 11:
			// invoke Operate task
			System.out.println("Invoking 'Operate' task with Task ID: " + taskID);
			break;
		case 12:
			// invoke Visualize task
			System.out.println("Invoking 'Visualize' task with Task ID: " + taskID);
			break;
			default:
				System.out.println("No tasks to invoke!");
		}
	}
}
