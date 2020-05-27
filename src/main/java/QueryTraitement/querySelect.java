package QueryTraitement;


import java.util.List;
import java.util.ArrayList;

import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;


public class querySelect extends query {

	private SqlSelect SelectNode;

	/**
	 * Exctraire le attributs de la clause Select et les mettre dans queryResult
	 */

	public querySelect(final String query) {
		super(query);
		setSelectNode(this.parseQuery());

	}
	/**
	 * extraire les attributs de la clause Select et les mettre dans queryResult
	 */
	public void ExtractClausesSelect() {
		ArrayList<String> SelectTable=new ArrayList<String>();
		SqlNodeList ListNode = (getSelectNode()).getSelectList();
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

		SqlSelect selectNode =(SqlSelect) getSelectNode();
		List<String> FromTables=new ArrayList<String>();

		if (selectNode.getFrom().getKind().equals(SqlKind.IDENTIFIER))
		{	
			if(checkLowerOrOppCaseString(selectNode.getFrom().toString()))
			  FromTables.add(selectNode.getFrom().toString());
			else
				FromTables.add(selectNode.getFrom().toString().toLowerCase());
		}
		else {
			SqlJoin join = (SqlJoin) selectNode.getFrom();
			this.RecurisiveClausesFrom(join, selectNode, FromTables);
			}
		queryResult.put("FROM", FromTables);
		
	}

	/**
	 * Exctraire le attributs de la clause Where et les mettre dans queryResult
	 * Types d'attributs de la clause where (type,name,size,contenent,extention, )
	 */
	private void ExtractClausesWhere() {

		 SqlSelect sel = getSelectNode();
		SqlNode sqlnode = sel.getWhere();
		this.node.put("WHERE", sqlnode);
	}

	/**
	 *  Exctraire les sattributs de and de la clause Where et les mettre dans queryResult
	 */
	private void ExtractAndFromWhere() {
		
		String FindAnd = null;
		 SqlSelect sel = getSelectNode();
		FindAnd = sel.getWhere().toString().toUpperCase();
		if(FindAnd.contains("AND")) {
			System.out.println("contains AND ");
		}

	}

	/**
	 *  Exctraire les sattributs de OR de la clause Where et les mettre dans queryResult
	 */
	private void ExtractOrFromWhere() {
		
		String FindOr = null;
		 SqlSelect sel = getSelectNode();
		FindOr = sel.getWhere().toString().toUpperCase();
		if(FindOr.contains("Or")) {
			System.out.println("contains Or ");
		}
	}


	/**
	 * Metttre a jour le Noeud Select
	 * @param newNode le nouveau noeud select
	 */
	public void setSelectNode (SqlNode newNode) {
		this.SelectNode = (SqlSelect)newNode;
	}

	/**
	 * retourner le Noeud Select
	 * @return Le noeud Select de cette classe
	 */
	public SqlSelect getSelectNode () {
		return this.SelectNode;
	}

	public List<String> RecurisiveClausesFrom (SqlJoin join,SqlNode node,List<String> tables)
	{
		if (join.getLeft().getKind().equals(SqlKind.IDENTIFIER))
		{
			if(checkLowerOrOppCaseString(join.getRight().toString()))
				tables.add(join.getRight().toString());
			else
				tables.add(join.getRight().toString().toLowerCase());
			if(checkLowerOrOppCaseString(join.getLeft().toString()))
				tables.add(join.getLeft().toString());
			else
				tables.add(join.getLeft().toString().toLowerCase());
			
			return tables;
		}
		else {
			if(checkLowerOrOppCaseString(join.getRight().toString()))
				tables.add(join.getRight().toString());
			else
				tables.add(join.getRight().toString().toLowerCase());
			
		}
		return RecurisiveClausesFrom((SqlJoin) join.getLeft(),node,tables);

	}

}