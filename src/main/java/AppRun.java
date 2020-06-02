
import java.util.Scanner;
import java.util.Stack;

import org.apache.calcite.sql.parser.SqlParseException;
import CommandInterface.InterfaceCommand;
import CommandInterface.SelectCommand;
import CommandInterface.ShowCommand;
import QueryTraitement.attributsOfFile;



public enum AppRun{
	AppRun;
	private Scanner sc;
	private Stack<String> HistoriqueCommand = new Stack<String>();

	public void run() {
		sc = new Scanner(System.in);
		System.out.println("Veuillez saisir votre requete ");
		System.out.print(">");
		String query = sc.nextLine();
		while (query.isEmpty() && !query.equals("exit")) {
			System.out.print(">");
			query = sc.nextLine();
		}
		if(!query.equals("exit")) {
			if(query.equals("show")) {
				InterfaceCommand show = new ShowCommand(HistoriqueCommand);
				show.execute();
			}
			if(!query.equals("show")) {
				HistoriqueCommand.push(query);
				InterfaceCommand comand = new SelectCommand(query);
				try {
					comand.execute();
				} catch (Exception e) {
					run();
					} 
			}
			run();
		}

	}

	public static void main(String[] args) throws SqlParseException {
		System.out.println(attributsOfFile.GUIDEUTILISATION.get());
		AppRun.run();
	}

}


