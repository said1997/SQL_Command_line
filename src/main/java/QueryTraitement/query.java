package QueryTraitement;

import java.util.List;
import java.util.Map;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParser;

public abstract class query {
	
	
	/**
	 * La requette à parser.
	 */
	protected String queryToParse;
	
	/**
	 * Le parser Sql qui sera utilisé pour parser la requette.
	 */
	protected SqlParser  parser;
	
	/**
	 * Le résultat qui sera traité par OsTraitement après avoir parsé la requette.
	 * Il sous forme d'une map qui a pour clé la clause et la liste de ses attributs.
	 */
	protected Map<String,List<String>> queryResult;
	
	/**
	 * Un node sql après le traitement sous fore d'une clé qui est le noeud et un sql Node.
	 */
	protected Map<String,SqlNode> Node;
	
	/**
	 * Retourner la requette à parser
	 */
	public String getQueryToParsse() {
		return this.queryToParse;
	}
	 
	  
	  
}
