import java.sql.SQLException;
import java.util.Scanner;

import org.apache.calcite.sql.parser.SqlParseException;
import CommandInterface.InterfaceCommand;
import CommandInterface.SelectCommand;


public enum AppRun{
	AppRun;

	private Scanner sc;

	public void run() {
		sc = new Scanner(System.in);
		System.out.println("Veuillez saisir votre requete : ");
		String query = sc.nextLine();
		while (query.isEmpty() && !query.equals("exit")) {
			query = sc.nextLine();
		}
		if(!query.equals("exit")) {
			InterfaceCommand comand = new SelectCommand(query);
			try {
				comand.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws SqlParseException {

		AppRun.run();

	}

}


