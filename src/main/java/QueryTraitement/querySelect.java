package QueryTraitement;


import java.util.ArrayList;

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
				ExtractOrFromWhere();
			}
			else if(verifORAND.contains("AND")){
				ExtractAndFromWhere();
			}
			else {
				transition = sqlnode.toString();
				transition = transition.replace("(", "-");
				transition = transition.replace(")", "-");
				transition = transition.replace(",", "-");
				transition = transition.replace("\n", "-");
				transition = transition.replace("`", "-");
				transition = transition.replace(" ", "-");
				str =transition.split("");
				for(int i=0; i<str.length; i++) {
					while(str[i].equals("-") && (i<str.length -1)){
						i++;
					}
					tmp.add(str[i]);
				}
				if(tmp.get(tmp.size()-1).equals("-")) {
					tmp.remove(tmp.get(tmp.size()-1));
				}
				this.queryResult.put("WHERE", tmp);
			}
				} catch (SqlParseException e) {
			e.printStackTrace();
		 }
	}

	/**
	 *  Exctraire les sattributs de and de la clause Where et les mettre dans queryResult
	 */
	public void ExtractAndFromWhere() {
		System.out.println("renvoyez ici");
	}

	/**
	 *  Exctraire les sattributs de OR de la clause Where et les mettre dans queryResult
	 */
	public void ExtractOrFromWhere() {
		System.out.println("renvoyez ici");
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
