package QueryTraitement;


import java.util.List;
import java.util.ArrayList;

import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlOrderBy;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;



public class querySelect extends query {

	/**
	 * Le noeud Select retourné après avoir parsé un requette de type Select
	 */
	private SqlSelect SelectNode;

	/**
	 * Le constructeur de la classe querySelect.
	 * Exctraire le attributs de la clause Select et les mettre dans queryResult
	 */
	public querySelect(final String query) {
		super(query);
		setSelectNode(ParseSelectQuery());

	}

	/**
	 * Retourner un SqlSelect au debut même si la le node retourner est de type Order by.
	 * @return query Le premier noeud de la requette qui est un SqlSelect.
	 */
	private SqlSelect ParseSelectQuery() {

		if (super.getQuery() == null || super.getQuery().length() == 0) {
			System.err.println("Veuillez entrer une requette non vide");
			return null;
		}

		SqlNode all = super.parseQuery();
		SqlSelect query;
		if (all instanceof SqlSelect) {
			query = (SqlSelect) all;
			return query;

		} else if (all instanceof SqlOrderBy) {
			query = (SqlSelect) ((SqlOrderBy) all).query;
			return query;
		} else {
			throw new UnsupportedOperationException("Le noeud de la requette est de type " + all.getClass() + " qui n'est pas supporté ici.");
		}
	}
	/**
	 * Extraire les attributs de la clause Select et les mettre dans queryResult.
	 * La clé des la map queryResult est le nom de la Clause est la valeur sont les attributs de la clause.
	 */
	public void ExtractClausesSelect() {

		ArrayList<String> SelectAttributs=new ArrayList<String>();
		if (getSelectNode() != null) {
			SqlSelect selectNode =(SqlSelect) getSelectNode();
			SqlNodeList ListNode = selectNode.getSelectList();
			
			for (int i=0;i<ListNode.size();i++)
			{ 
				if (ListNode.get(i).toString().equals(attributsOfFile.PATH.get()) || 
						ListNode.get(i).toString().equals(attributsOfFile.NAME.get()) ||
						ListNode.get(i).toString().equals(attributsOfFile.TYPE.get()) || 
						ListNode.get(i).toString().equals(attributsOfFile.SIZE.get()) ||
						ListNode.get(i).toString().equals(attributsOfFile.DATELACCES.get()) ||
						ListNode.get(i).toString().equals(attributsOfFile.DATE.get()) ||
						ListNode.get(i).toString().equals(attributsOfFile.DATELMODIFICATION.get()) ||
						ListNode.get(i).toString().equals(attributsOfFile.ACCESRIGHTS.get()) )
					SelectAttributs.add(ListNode.get(i).toString());

				else if (ListNode.get(i).toString().equals("*"))
				{
					SelectAttributs.add(attributsOfFile.NAME.get());
					SelectAttributs.add(attributsOfFile.TYPE.get());
					SelectAttributs.add(attributsOfFile.SIZE.get());
					SelectAttributs.add(attributsOfFile.ACCESRIGHTS.get());
					SelectAttributs.add(attributsOfFile.DATE.get());
					SelectAttributs.add(attributsOfFile.DATELACCES.get());
					SelectAttributs.add(attributsOfFile.DATELMODIFICATION.get());

				}


				else { 
					System.err.print("Veuillez rentrer les bons parametres du select");
					System.exit(0);
				}
			}

			queryResult.put("SELECT", SelectAttributs);
		}
		else {
			System.err.println("Le select node est null");
		}

	}

	/**
	 * Exctraire le attributs de la clause From et les mettre dans queryResult.
	 * La clé des la map queryResult est le nom de la Clause est la valeur sont les attributs de la clause.
	 */
	public void ExtractClausesFrom() {
		if (getSelectNode() != null) {
			SqlSelect selectNode = getSelectNode();
			List<String> FromAttributs=new ArrayList<String>();

			if (selectNode.getFrom().getKind().equals(SqlKind.IDENTIFIER))
			{	
				if(checkLowerOrOppCaseString(selectNode.getFrom().toString()))
					FromAttributs.add(selectNode.getFrom().toString());
				else
					FromAttributs.add(selectNode.getFrom().toString().toLowerCase());
			}
			else {
				//Extraire les attributs de la clause from récursivement.
				SqlJoin join = (SqlJoin) selectNode.getFrom();
				this.RecurisiveClausesFrom(join,FromAttributs);
			}
			queryResult.put("FROM", FromAttributs);
		}
		else {
			System.err.println("Le SelectNode est null");
		}

	}

	/**
	 * Extraire de manière récursive les attributs de la clase from.
	 * Si la clause contient des alias AS seleument le vrai nom de la table sera extrait
	 * @param join le noeud de type SqlJoin de la clause from
	 * @param tables liste des attributs du noeud from.
	 * @return la liste des attribut de clause from
	 */
	public List<String> RecurisiveClausesFrom (SqlJoin join,List<String> tables)
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
		return RecurisiveClausesFrom((SqlJoin) join.getLeft(),tables);
	}
	
	/**
	 * Exctraire le attributs de la clause Where et les mettre dans queryResult
	 * Types d'attributs de la clause where (type,name,size,contenent,extention, )
	 * @throws SqlParseException 
	 */
	public void ExtractClausesWhere() throws SqlParseException {
		String transition = null;
		String [] str;
		ArrayList<String> tmp = new ArrayList<String>() ;
		 try {
			SqlSelect sel = getSelectNode();
			SqlNode sqlnode = sel.getWhere();
			if(sqlnode != null) {
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
			        if(tmp.get(j).equals(".")){
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
	 * Metttre à jour le Noeud Select
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



	/**
	 * Extraire les alias de de clause from si elle contiant des alias.
	 * @param node le noeud de la clause from
	 * @return
	 */
	public List<String> extrairesWithAliasFrom(SqlNode node) {
		final List<String> tables = new ArrayList<>();

		if (node == null) {
			return tables;
		}
		// si ya qu'une seule close.
		if (node.getKind().equals(SqlKind.AS)) {
			tables.add(((SqlBasicCall) node).operand(1).toString());
			return tables;
		}
		// si on a plus d'une seule clause.
		if (node.getKind().equals(SqlKind.JOIN)) {
			final SqlJoin from = (SqlJoin) node;
			// si on a le alias i.e:AS je prends l'alias.
			if (from.getLeft().getKind().equals(SqlKind.AS)) {
				tables.add(((SqlBasicCall) from.getLeft()).operand(1).toString());
			} else {
				// Si on a plus de 2 alias dans la requette query.
				if (from.getLeft() instanceof SqlJoin) {
					SqlJoin left = (SqlJoin) from.getLeft();

					while (!left.getLeft().getKind().equals(SqlKind.AS)) {
						tables.add(((SqlBasicCall) left.getRight()).operand(1).toString());
						left = (SqlJoin) left.getLeft();
					}
					tables.add(((SqlBasicCall) left.getLeft()).operand(1).toString());
					tables.add(((SqlBasicCall) left.getRight()).operand(1).toString());
				}
			}
			try {
				tables.add(((SqlBasicCall) from.getRight()).operand(1).toString());
				return tables;
			} catch (ClassCastException e) {

			}

		}

		return tables;
	}
}