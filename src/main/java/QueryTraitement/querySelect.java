package QueryTraitement;


import java.util.ArrayList;
import java.util.List;

import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.SqlBasicCall;

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
		final SqlNode node = (SqlJoin) getFirstNode().getFrom();
		final List<String> tables = new ArrayList<>();
		if (node != null) {
		// Si on a un orderBy dans la requete.
				if (node.getKind().equals(SqlKind.ORDER_BY)) {
					// Retrouver le vrai Select.
					//from = ((SqlSelect) ((SqlOrderBy) from).query).getFrom();
				} else {
					//from = ((SqlSelect) from).getFrom();	
				}
				
				// si ya qu'une seule close.
				if (node.getKind().equals(SqlKind.AS)) {
					tables.add(((SqlBasicCall) node).operand(1).toString());
					//return tables;
				}

				// si on a plus d'une seule clause.
				if (node.getKind().equals(SqlKind.JOIN)) {
					final SqlJoin from = (SqlJoin) node;
					// si on a le alias i.e:AS je prends l'alias.
					if (from.getLeft().getKind().equals(SqlKind.AS)) {
						tables.add(((SqlBasicCall) from.getLeft()).operand(1).toString());
					} else {
						// Case when more than 2 data sets are in the query.
						SqlJoin left = (SqlJoin) from.getLeft();

						// Traverse until on get un AS.
						while (!left.getLeft().getKind().equals(SqlKind.AS)) {
							tables.add(((SqlBasicCall) left.getRight()).operand(1).toString());
							left = (SqlJoin) left.getLeft();
						}

						tables.add(((SqlBasicCall) left.getLeft()).operand(1).toString());
						tables.add(((SqlBasicCall) left.getRight()).operand(1).toString());
					}

					tables.add(((SqlBasicCall) from.getRight()).operand(1).toString());
					setQueryResult("From", tables);
				}
		}
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

}
