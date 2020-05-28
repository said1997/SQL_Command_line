import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.hsqldb.TextTable;

import CommandInterface.InterfaceCommand;
import CommandInterface.SelectCommand;
import CommandInterface.TableGenerator;
import QueryTraitement.querySelect;


public enum AppRun{
	AppRun;

	public void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir votre requete : ");
		String query = sc.nextLine();
		while (query.isEmpty() && !query.equals("exit")) {
			query = sc.nextLine();
		}
		if(!query.equals("exit")) {
		InterfaceCommand comand = new SelectCommand(query);
		comand.execute();
		}
	}

	public static void main(String[] args) throws SqlParseException {

		AppRun.run();
		
	}

}


