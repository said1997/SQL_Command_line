import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

import QueryTraitement.querySelect;


public class AppRun extends querySelect {
	List<String> SelectTable=new ArrayList<String>();
	List<String> FromTable=new ArrayList<String>();
	public AppRun(String query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public void Syntaxe(Scanner sc) {
		// je parse la requete

		System.out.println("Veuillez saisir votre requete : ");
		String query = sc.nextLine();
		boolean a = false;
		SqlParser parser = SqlParser.create(query);
		try {
			SqlSelect selectNode = (SqlSelect) parser.parseQuery();
			SqlNodeList ListNode = selectNode.getSelectList();
			System.out.println("JE SUIS LA 1");
			System.out.println("LA TAILLE DE LA LISTE EST" + ListNode.size());
			for (int i = 0; i < ListNode.size(); i++) {

				SelectTable.add(ListNode.get(i).toString());
				System.out.println("JE SUIS LA 2");
				// System.out.println("lelement "+ i +"du SELECT est "+ ListNode.get(i));}
			}
			if (selectNode.getFrom().getKind().equals(SqlKind.IDENTIFIER)) {
				this.FromTable.add(0, selectNode.getFrom().toString());

				// System.out.println("LE SEUL ELEMENT DU FROM EST"+
				// selectNode.getFrom().toString());

			}

			else {
				SqlJoin join = (SqlJoin) selectNode.getFrom();
				List<String> FromTable = new ArrayList<String>();
				System.out.println("SUIS JE ARRIVE ICI");
				this.RecurisiveClausesFrom(join, selectNode, FromTable);
				System.out.println(" fonction extract executer");
				// récupération des elements du SELECT

				System.out.println("la taille de la table from est" + FromTable.size());

				for (int i=0;i<FromTable.size();i++) {
					System.out.println("lelement du from est"+i+ FromTable.get(i));
					} 
				

		}
	} catch (Exception e) {
		System.out.println("Incorrect requete veuillez vérifier votre syntaxe et retaper");
		// throw new IncorrectQueryException("Incorrect requete veuillez vérifier votre
		// syntaxe et retaper");
		Syntaxe(sc);

	}
	

}
	
	public List<String> getFromTable() {
		return this.FromTable;
	}

	public List<String> getSelectTable() {
		return this.SelectTable;
	}




public static void main(String[] args) throws SqlParseException {
	
	Scanner sc = new Scanner(System.in);
   
    AppRun a=new AppRun(sc.nextLine());
    a.parseQuery();
    a.Syntaxe(sc);
    List <String> FromTable = a.getFromTable();
    List <String> SelectTable=a.getSelectTable();
    
    for (int i=0;i<SelectTable.size();i++)
    {
    	System.out.println("LELEMENT NUMERO"+i+"DU SELECT EST"+SelectTable.get(i));
    	
    }
    for (int i=0;i<FromTable.size();i++)
    {
    	System.out.println("LELEMENT NUMERO"+i+"DU FROM EST"+FromTable.get(i));
    	
    }

}

}
