package QueryTraitement;


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
		
		 this.parser = SqlParser.create(this.queryToParse);
		 try {
			SqlSelect sel = (SqlSelect) this.parser.parseQuery();
			SqlNode sqlnode = sel.getWhere();
			this.node.put("WHERE", sqlnode);
		} catch (SqlParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *  Exctraire les sattributs de and de la clause Where et les mettre dans queryResult
	 */
	public void ExtractAndFromWhere() {
		String FindAnd = null;
		 this.parser = SqlParser.create(this.queryToParse);
		 try {
			SqlSelect sel = (SqlSelect) this.parser.parseQuery();
			FindAnd = sel.getWhere().toString().toUpperCase();
			if(FindAnd.contains("AND")) {
				System.out.println("contains AND ");
			}
		} catch (SqlParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *  Exctraire les sattributs de OR de la clause Where et les mettre dans queryResult
	 */
	public void ExtractOrFromWhere() {
		String FindOr = null;
		 this.parser = SqlParser.create(this.queryToParse);
		 try {
			SqlSelect sel = (SqlSelect) this.parser.parseQuery();
			FindOr = sel.getWhere().toString().toUpperCase();
			if(FindOr.contains("Or")) {
				System.out.println("contains Or ");
			}
		} catch (SqlParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
