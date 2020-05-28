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
		
		TableGenerator tableGenerator = new TableGenerator();

        List<String> headersList = new ArrayList<>(); 
        headersList.add("F-Name");
        headersList.add("Type");
        headersList.add("Size");
        headersList.add("ACCESS");
        headersList.add("elwali");
        
        List<List<String>> rowsList = new ArrayList<>();

       for (int i = 0; i < 6; i++) {
            List<String> row = new ArrayList<>(); 
            row.add("col1");
            row.add("col2");
            row.add("col3");
            row.add("col4");
            row.add("col5");
            rowsList.add(row);
        }

        System.out.println(tableGenerator.generateTable(headersList, rowsList));  
		
	}


}


