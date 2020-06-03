package QueryTraitement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.calcite.sql.SqlNode;
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
	 * Constructeur de cette classe
	 * @param query la requette à parser.
	 */
	public query(final String query) {
		
		this.setQuery(query);
		this.setParser(getQuery());
		queryResult = new HashMap<String,List<String>>();
	}
	
	/**
	 * Parser la requette query.
	 * @return SqlNode un SqlNode le premier noeud de la requette.
	 */
	protected SqlNode parseQuery() {
		try { 
			return getParser().parseQuery();
		}
		catch (Exception e) {
			System.err.println("Erreur dans la syntaxe de la requette");
			return null;
		}
	}
	
	/**
	 * crée un parser pour la requette à parser
	 * @param query la requette.
	 */
	public void setParser(final String query) {
		this.parser = SqlParser.create(query);
	}
	
	/**
	 * Retourne le parser de la requette à parser.
	 * @return parser le parser sur la requette.
	 */
	public SqlParser getParser() {
		return this.parser;
	}
	
	/**
	 * Set la requette à parser.
	 * @param query la requette.
	 */
	public void setQuery(final String query) {
		this.queryToParse=query;
	}
	
	/**
	 * Retourner la requette à parser.
	 * @return queryToParse la requette à parser.
	 */
	public String getQuery() {
		return this.queryToParse;
	}
	
	/**
	 * Retourner la querry final à envoyer au module OsTraitement.
	 * @return queryResult une map qui à comme clé la clause et valeur la liste des ses attributs.
	 */
	public Map<String,List<String>> getQueryResult(){
		return this.queryResult;
	}
		
	
	/**
	 * Remplir la hash map à envoyer au module OsTraitement.
	 * @param Clause la clause qui est la valeur de la clé.
	 * @param attribut la Liste des atrributs à ajouter. 
	 */
	public void setQueryResult(String Clause, List<String> attributs) {
		this.queryResult.put(Clause,attributs);
	}
	
	
	/**
	 * Regarde si une chaine de caractères contient au moin une minuscule ou une majuscule.
	 * Si un utilisateur entre un chemin en minuscule il sera remis en minuscules après le traitement de la requette.
	 * Si un utilisateur entre des caractères spéciaux ou des majuscules il doit mettre des " " pour l'attribut. 
	 * @param str une chaine de caractères.
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