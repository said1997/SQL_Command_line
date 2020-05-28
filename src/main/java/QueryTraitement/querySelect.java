package QueryTraitement;


import java.util.List;
import java.util.ArrayList;

import org.apache.calcite.sql.SqlBasicCall;
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
			if (ListNode.get(i).toString().equals(attributsOfFile.NOM.get()) || 
				ListNode.get(i).toString().equals(attributsOfFile.TYPE.get()) || 
				ListNode.get(i).toString().equals(attributsOfFile.SIZE.get()) ||
				ListNode.get(i).toString().equals(attributsOfFile.DATELACCES.get()) ||
				ListNode.get(i).toString().equals(attributsOfFile.DATELMODIFICATION.get()) ||
				ListNode.get(i).toString().equals(attributsOfFile.ACCESRIGHTS.get()) )
				SelectTable.add(ListNode.get(i).toString());
			
			else if (ListNode.get(i).toString().equals("*"))
			{
				SelectTable.add(attributsOfFile.NOM.get());
				SelectTable.add(attributsOfFile.TYPE.get());
				SelectTable.add(attributsOfFile.SIZE.get());
				SelectTable.add(attributsOfFile.ACCESRIGHTS.get());
				SelectTable.add(attributsOfFile.DATELACCES.get());
				SelectTable.add(attributsOfFile.DATELMODIFICATION.get());
				
			}
					
			
			else { 
				System.err.print("Veuillez rentrer les bons parametres du select");
				System.exit(0);
			}
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
	/**
	 * Exctraire le attributs de la clause Where et les mettre dans queryResult
	 * Types d'attributs de la clause where (type,name,size,contenent,extention, )
	 * @throws SqlParseException 
	 */
	public void ExtractClausesWhere() throws SqlParseException {
		String transition = null;
		String [] str;
		ArrayList<String> tmp = new ArrayList<String>() ;
		 SqlSelect sel = (SqlSelect) getSelectNode();
		SqlNode sqlnode = sel.getWhere();
		String verifORAND = sqlnode.toString();
		if(verifORAND.contains("OR")) {
			 tmp.add("OR");
			this.queryResult.put("WHERE",tmp);
			ExtractOrFromWhere();
		}
		else if(verifORAND.contains("AND")){
			tmp.add("AND");
			this.queryResult.put("WHERE",tmp);
			ExtractAndFromWhere();
		}
		else {
			//Select * FROM Bureau WHERE nom = test.txt 
			transition = sqlnode.toString();
			transition = transition.replace("(", "-");
			transition = transition.replace(")", "-");
			transition = transition.replace(",", "-");
			transition = transition.replace("\n", "-");
			transition = transition.replace("`", "-");
			transition = transition.replace(" ", "-");
			str =transition.split("-");
			for(int i=0; i<str.length; i++) {
				while(str[i].equals("")  && (i<str.length -1)){
					i++;
				}
				tmp.add(str[i]);
			}
			ArrayList<String> PointSupp = new ArrayList<String>();
		    for (int j = 0; j < tmp.size(); j++) {
		        if(tmp.get(j+1).equals(".")){
		        	PointSupp.add(tmp.get(j)+tmp.get(j+1)+tmp.get(j+2));
		        	j+=2;
		        }
		        PointSupp.add(tmp.get(j));
		      }
		    for(int i=0;i<PointSupp.size();i++) {
		    	if(PointSupp.get(i).contains(".")) {
		    		PointSupp.remove(i+1);
		    	}
		    }
		    
			if(PointSupp.get(PointSupp.size()-1).equals("-")) {
				PointSupp.remove(PointSupp.get(PointSupp.size()-1));
			}				
			this.queryResult.put("WHERE", PointSupp);
		}
	}

	/**
	 *  Exctraire les sattributs de and de la clause Where et les mettre dans queryResult
	 * @throws SqlParseException 
	 */
	public void ExtractAndFromWhere() throws SqlParseException {
		operationOR_AND("AND");
	}

	/**
	 *  Exctraire les sattributs de OR de la clause Where et les mettre dans queryResult
	 */
	public void ExtractOrFromWhere() {
		operationOR_AND("OR");
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
	
	/*
	 * Gestion generique des de la persence des AND et OR dans un WHERE
	 */
	public void operationOR_AND(String and_ou_or) {
		ArrayList<String> tmp = new ArrayList<String>() ;
		SqlSelect sel = null;
		sel = (SqlSelect) getSelectNode();
		SqlBasicCall where = (SqlBasicCall)sel.getWhere();
		if (where != null) {
			final SqlBasicCall sqlBasicCallRight = where.operand(1);
			SqlBasicCall sqlBasicCallLeft = where.operand(0);
			int i =0;
			// je prends l'identifiant a gauche iteration à gauche

			tmp.add(sqlBasicCallRight.operand(0).toString());
			tmp.add(sqlBasicCallRight.getOperator().toString());
			tmp.add(sqlBasicCallRight.operand(1).toString());
			this.queryResult.put (and_ou_or+"_right", tmp);
			while (!sqlBasicCallLeft.operand(0).getKind().equals(SqlKind.IDENTIFIER) && !sqlBasicCallLeft.operand(1).getKind().equals(SqlKind.LITERAL)) {

				ArrayList<String> looptmp1 = new ArrayList<String>() ;
				looptmp1.add(((SqlBasicCall) sqlBasicCallLeft.operand(1)).operand(0).toString());
				looptmp1.add(((SqlBasicCall) sqlBasicCallLeft.operand(1)).getOperator().toString());
				looptmp1.add(((SqlBasicCall) sqlBasicCallLeft.operand(1)).operand(1).toString());
				this.queryResult.put(and_ou_or+i,looptmp1);
				i++;
				sqlBasicCallLeft = sqlBasicCallLeft.operand(0); // Move to next where condition.
			}
			ArrayList<String> looptmp = new ArrayList<String>();
			looptmp.add(sqlBasicCallLeft.operand(0).toString());
			looptmp.add(sqlBasicCallLeft.getOperator().toString());
			looptmp.add(sqlBasicCallLeft.operand(1).toString());
			this.queryResult.put (and_ou_or+"_left", looptmp);
		}
		
	}

}