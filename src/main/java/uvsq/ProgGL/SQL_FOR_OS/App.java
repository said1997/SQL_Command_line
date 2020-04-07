package uvsq.ProgGL.SQL_FOR_OS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.logging.StreamHandler;

import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;


/**
 * Hello world!
 *
 */
public class App 
{
	
	/*public static String execCmd(String cmd) throws java.io.IOException {
		System.out.println("LA COMMANDE EST"+ cmd);
	    java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
	    //System.out.println(s.next());
	return s.hasNext() ? s.next() : "";
	}*/
	private static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                           new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
	
	
private static List<String> amine (SqlJoin join,SqlNode node,List<String> tables)
{
	System.out.println("DANS LA FONCTION");
	System.out.println("LE KIND DU NOEUD EST"+join.getLeft().getKind().toString());
	if (join.getLeft().getKind().equals(SqlKind.IDENTIFIER))
	{
		System.out.println("DANS LE IF DE LA FONCTION");
		tables.add(join.getRight().toString());
		tables.add(join.getLeft().toString());
		return tables;
	}
	else 
		System.out.println("LES ELEM DE LA LIST"+join.getRight().toString());
		tables.add(join.getRight().toString());
	return amine((SqlJoin) join.getLeft(),node,tables);

}
	

	
	
	
	
	
	
	
    public static void main( String[] args ) throws SqlParseException, IncorrectQueryException, IOException
    {	
    	
    Scanner sc = new Scanner(System.in);
    Scan a=new Scan();
    a.Syntaxe(sc);
    List <String> FromTable = a.getFromTable();
    List <String> SelectTable=a.getSelectTable();
   
    /*for (int i=0;i<SelectTable.size();i++)
    {
    	System.out.println("LELEMENT NUMERO"+i+"DU SELECT EST"+SelectTable.get(i));
    	
    }
    for (int i=0;i<FromTable.size();i++)
    {
    	System.out.println("LELEMENT NUMERO"+i+"DU FROM EST"+FromTable.get(i));
    	
    }*/
    System.out.println("LE RESULTAT EST " + a.adapter());
    System.out.println(executeCommand(a.adapter()));
    
   // Syntaxe(sc);
    	/*String s="ls /home/amine/Bureau";
    	String s1= "ls /home/amine/Bureau/calcite";
    	System.out.println();
    	System.out.println(executeCommand(a.adapter()));*/
    	 // process.
   
    	}
}
