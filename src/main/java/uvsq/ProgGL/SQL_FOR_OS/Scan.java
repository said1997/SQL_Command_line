package uvsq.ProgGL.SQL_FOR_OS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParser;

public class Scan {

//private String Query;	
public List <String> FromTable;
public List <String> SelectTable;


public Scan ()
{

	this.FromTable=new ArrayList<String>();
	this.SelectTable=new ArrayList<String>();
}


private List<String> amine (SqlJoin join,SqlNode node,List<String> tables)
{
	System.out.println("DANS LA FONCTION");
	System.out.println("LE KIND DU NOEUD EST"+join.getLeft().getKind().toString());
	if (join.getLeft().getKind().equals(SqlKind.IDENTIFIER))
	{
		System.out.println("DANS LE IF DE LA FONCTION");
		tables.add(join.getRight().toString());
		tables.add(join.getLeft().toString());
		this.FromTable=tables;
		//return tables;
		return this.FromTable;
	}
	else 
		System.out.println("LES ELEM DE LA LIST"+join.getRight().toString());
		tables.add(join.getRight().toString());
	return amine((SqlJoin) join.getLeft(),node,tables);

}


public void Syntaxe (Scanner sc)
{
	//je parse la requete
			System.out.println("Veuillez saisir votre requete : ");
			String query=sc.nextLine();
			boolean a=false;
			SqlParser parser = SqlParser.create(query);
			System.out.println("XXXXXXXXXXXXXXXXX");
		try	{
	    		SqlSelect selectNode = (SqlSelect) parser.parseQuery();
		    	SqlNodeList ListNode = selectNode.getSelectList();
		    	System.out.println("LA LISTE SELECT EST"+ ListNode);
		    	for (int i=0;i<ListNode.size();i++)
		    	{ 
		    		
		    		this.SelectTable.add(ListNode.get(i).toString());
		    		System.out.println("JE SUIS LA 2");
		    		//System.out.println("lelement "+ i +"du SELECT est "+ ListNode.get(i));}
		    	}
		    if (selectNode.getFrom().getKind().equals(SqlKind.IDENTIFIER))
		    	{
		    		this.FromTable.add(0, selectNode.getFrom().toString());
		    	
		    	//System.out.println("LE SEUL ELEMENT DU FROM EST"+ selectNode.getFrom().toString());
		    	
		    }
		    	
		    else {
		    		SqlJoin join = (SqlJoin) selectNode.getFrom();
			    	List <String> FromTable=new ArrayList<String>();
			    	System.out.println("SUIS JE ARRIVE ICI");
			    	amine(join,selectNode,FromTable);
			    	System.out.println(" fonction extract executer");
			    // récupération des elements du SELECT
			    	
					 
					System.out.println("la taille de la table from est"+FromTable.size());
				/*	for (int i=0;i<FromTable.size();i++)
					{
						System.out.println("lelement du from est"+i+ FromTable.get(i)); }
			    		} */
		    }
				}
		catch (Exception e)
			{	
			a=true;
			System.out.println("Incorrect requete veuillez vérifier votre syntaxe et retaper");
			//throw new IncorrectQueryException("Incorrect requete veuillez vérifier votre syntaxe et retaper");
			Syntaxe(sc);
			
			}	
	    	
			
}

public List<String> getFromTable()
{
return this.FromTable;	
}




public String adapter()
{
	String ret="";
	String replace="/";
	if (SelectTable.size()!=0 )
	{
		//System.out.println("LA CASE DU SELECT EST EGAL A"+ SelectTable.get(0)+"----"+SelectTable.get(1));
		
		ret="ls"+ " ";
	}
	if (FromTable.size()!=0)
	{	
		String replace1=FromTable.get(0).toString().replace(".", "/");
		replace+=replace1;
		System.out.println("LA CHAINE REPLACE EST EGAL A "+ replace);
		ret+=replace;
		//ret=ret.toLowerCase();
	}
	return ret;
}


public List<String> getSelectTable()
{
return this.SelectTable;	
}

	
}
