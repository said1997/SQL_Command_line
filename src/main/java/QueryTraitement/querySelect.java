package QueryTraitement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
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
	private void ExtractClausesSelect() {

	}

	/**
	 * Exctraire le attributs de la clause From et les mettre dans queryResult
	 */
	private void ExtractClausesFrom() {

	}

	/**
	 * Exctraire le attributs de la clause Where et les mettre dans queryResult
	 * Types d'attributs de la clause where (type,name,size,contenent,extention, )
	 */
	public void ExtractClausesWhere() {
		String transition = null;
		String [] str;
		ArrayList<String> tmp = new ArrayList<String>() ;
		 this.parser = SqlParser.create(this.queryToParse);
		 try {
			SqlSelect sel = (SqlSelect) this.parser.parseQuery();
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
				} catch (SqlParseException e) {
			e.printStackTrace();
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
	/*
	 * Gestion generique des de la persence des AND et OR dans un WHERE
	 */
	public void operationOR_AND(String and_ou_or) {
		ArrayList<String> tmp = new ArrayList<String>() ;
		this.parser = SqlParser.create(this.queryToParse);
		SqlSelect sel = null;
		try {
			sel = (SqlSelect) SqlParser.create(this.queryToParse).parseQuery();
		} catch (SqlParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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