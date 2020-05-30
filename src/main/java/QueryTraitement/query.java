package QueryTraitement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlOrderBy;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

public abstract class query {
	
	
	/**
	 * La requette à parser.
	 */
	private String queryToParse;
	
	/**
	 * Le parser Sql qui sera utilisé pour parser la requette.
	 */
	private SqlParser  parser;
	
	
	/**
	 * Le résultat qui sera traité par OsTraitement après avoir parsé la requette.
	 * Il sous forme d'une map qui a pour clé la clause et la liste de ses attributs.
	 */
	protected Map<String,List<String>> queryResult;
	
	/**
	 * Un node sql après le traitement sous fore d'une clé qui est le noeud et un sql Node.
	 */
	protected Map<String,SqlNode> node;
	
	/**
	 * Constructeur de cette classe
	 */
	public query(final String query) {
		
		this.setQuery(query);
		this.setParser(getQuery());
		queryResult = new HashMap<String,List<String>>();
		node = new HashMap<String,SqlNode>();
	}
	
	/**
	 * Retourner la requette à parser
	 * @return la requette à parser
	 */
	public String getQuery() {
		return this.queryToParse;
	}
	
	/**
	 * Set la requette à parser
	 */
	public void setQuery(final String query) {
		this.queryToParse=query;
	}
	
	/**
	 * Parser la requette query.
	 * @return un SqlNode le premier noeud de la requette.
	 */
	protected SqlNode parseQuery() {
		try { 
			
			return this.parser.parseQuery();
		}
		catch (Exception e) {
			System.err.println("Erreur dans la syntaxe de la requette");
			return null;
		}
	}
	
	/**
	 * Retourne le parser se la requette à parser
	 * @return
	 */
	public SqlParser getParser() {
		return this.parser;
	}

	/**
	 * crée un parseur pour la requette à parser
	 * @param parser
	 */
	public void setParser(final String query) {
		this.parser = SqlParser.create(query);
	}
	
	/**
	 * Retourner la querry final à en voyer au module OS
	 * @return
	 */
	public Map<String,List<String>> getQueryResult(){
		return this.queryResult;
	}
	
	
	/**
	 * Remplir la hash map à envoyer au module OS
	 * @param Clause la clause qui est la valeur de la clé
	 * @param attribut la trribut a ajout 
	 */
	public void setQueryResult(String Clause, List<String> attributs) {
		this.queryResult.put(Clause,attributs);
	}
	
	/**
	 * Remplir la hash map d'un noeur avec des sous noeuds si il esxistent
	 * @param Clause la clause qui est la valeur de la clé
	 * @param attribut la trribut a ajout 
	 */
	public void setSousNode(String Clause, SqlNode sousNoeud) {
		this.node.put(Clause,sousNoeud);
		
	}
	
	/**
	 * Retourne la hash map d'un avec ses sous noeuds si ils existent.
	 * @param Clause la clause qui est la valeur de la clé
	 * @param attribut la trribut a ajout 
	 */
	public SqlNode getSousNode(String Clause) {
		if (this.node.get(Clause) != null) {
			return this.node.get(Clause); 
		}else {
			return null;
		}
		
	}
	
	
	
	
	
	/**
	 * Regarde si elle contient au moin une minuscule ou une majuscule.
	 * @param str prends une chaine de caractère.
	 * @return boolean true or false.
	 */
	protected boolean checkLowerOrOppCaseString(String str) {
	    char ch;
	    boolean capitalFlag = false;
	    boolean lowerCaseFlag = false;

	    if(str != null && !str.equals("")) {
	    for(int i=0;i < str.length();i++) {
	        ch = str.charAt(i);
	       if (Character.isUpperCase(ch)) {
	            capitalFlag = true;
	        } else if (Character.isLowerCase(ch)) {
	            lowerCaseFlag = true;
	        }
	        if(capitalFlag && lowerCaseFlag)
	            return true;
	    }
	    }
	    return false;
	}

	  	  
}