package OsTraitement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OsTraitement {
	
	protected Map<String,List<String>> queryToOs;
	protected String Command;
	
	/**
	 * Constructeur de La classe
	 */
	public OsTraitement(Map<String,List<String>> toTraduct) {
		this.queryToOs= new HashMap<String,List<String>>();	
		this.Command= new String("");
		this.queryToOs=toTraduct;
		}
	
	
	/**
     * Set l'attribut Commande de la classe OsTraitement.
     * @param newCommand la nouvelle commande.
     */
	public void setCommand(String newCommand) {
		this.Command = newCommand;
	}
	
	/**
     * Set l'attribut Commande de la classe OsTraitement.
     * @param newCommand la nouvelle commande.
     */
	public String getCommand() {
		return this.Command;
	}
}
