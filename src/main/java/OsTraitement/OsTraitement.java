package OsTraitement;

import java.util.List;
import java.util.Map;

public abstract class OsTraitement {
	
	protected Map<String,List<String>> queryToOs;
	protected String Command;
	
	/**
	 * Constructeur de La classe
	 */
	public OsTraitement(Map<String,List<String>> toTraduct) {
		this.queryToOs=toTraduct;	
		this.Command= new String("");
		}
	
	
}
