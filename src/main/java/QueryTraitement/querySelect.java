package QueryTraitement;


import java.awt.List;
import java.util.ArrayList;

import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParser;

public class querySelect extends query {
	
	private SqlSelect SelectNode;
	
	/**
	 * Exctraire le attributs de la clause Select et les mettre dans queryResult
	 */
	
	public querySelect(final String query) {
		super(query);
		setSelectNode(getSelectNode());
		
	}
	/**
	 * extraire les attributs de la clause Select et les mettre dans queryResult
	 */
	public void ExtractClausesSelect() {
		ArrayList<String> SelectTable=new ArrayList<String>();
		SqlNodeList ListNode = ((SqlSelect) this.parseQuery()).getSelectList();
	    	for (int i=0;i<ListNode.size();i++)
	    	{ 
	    		SelectTable.add(ListNode.get(i).toString());
	    	}
	    queryResult.put("SELECT", SelectTable);

	}

	/**
	 * Exctraire le attributs de la clause From et les mettre dans queryResult
	 */
	public void ExtractClausesFrom() {
		
		SqlSelect selectNode =(SqlSelect) this.parseQuery();
		java.util.List<String> tables=new ArrayList<String>();
		
		  if (selectNode.getFrom().getKind().equals(SqlKind.IDENTIFIER))
	    	{
	    		tables.add(0, selectNode.getFrom().toString());
	    	}
		  else {
			  	SqlJoin join = (SqlJoin) selectNode.getFrom();
			  	this.RecurisiveClausesFrom(join, selectNode, tables);}
		queryResult.put("FROM", tables);
		}

	/**
	 * Exctraire le attributs de la clause Where et les mettre dans queryResult
	 * Types d'attributs de la clause where (type,name,size,contenent,extention, )
	 */
	private void ExtractClausesWhere() {

	}

	/**
	 *  Exctraire les sattributs de and de la clause Where et les mettre dans queryResult
	 */
	private void ExtractAndFromWhere() {

	}

	/**
	 *  Exctraire les sattributs de OR de la clause Where et les mettre dans queryResult
	 */
	private void ExtractOrFromWhere() {

	}

	/**
	 * Retourner le premieu noeud de la l'arbre de la requette à parser.
	 * @return SqlSelect Le premier noeud de la requette de type Select node
	 */
	protected SqlSelect getFirstNode() {
		try {
			
			return (SqlSelect)parseQuery();
		}
		catch(Exception e) {
			System.err.println("Impossible de retourner le premier Noeud de la requette type Select");
			return null;
		}
	}
	
	/**
	 * Metttre a jour le Noeud Select
	 * @param newNode le nouveau noeud select
	 */
	public void setSelectNode (SqlSelect newNode) {
		this.SelectNode = newNode;
	}
	
	/**
	 * retourner le Noeud Select
	 * @return Le noeud Select de cette classe
	 */
	public SqlSelect getSelectNode () {
		return this.SelectNode;
	}
	
	private java.util.List<String> RecurisiveClausesFrom (SqlJoin join,SqlNode node,java.util.List<String> tables)
	{
		if (join.getLeft().getKind().equals(SqlKind.IDENTIFIER))
		{
			tables.add(join.getRight().toString());
			tables.add(join.getLeft().toString());
			return tables;
		}
		else 
		tables.add(join.getRight().toString());
		return RecurisiveClausesFrom((SqlJoin) join.getLeft(),node,tables);

	}

}
