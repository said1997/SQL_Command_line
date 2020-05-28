package OsTraitement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QueryTraitement.attributsOfFile;

public abstract class OsTraitement {

	protected Map<String,List<String>> queryToOs;
	protected String Command;
	protected String statCommand;
	protected  Map<String,List<String>> FolderAndContiners;

	/**
	 * Constructeur de La classe
	 */
	public OsTraitement(Map<String,List<String>> toTraduct) {
		this.queryToOs = new HashMap<String,List<String>>();	
		this.FolderAndContiners = new HashMap<String,List<String>>();
		this.Command= new String("");
		this.queryToOs=toTraduct;
		setStatCommand(new String(""));
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

	/**
	 * Prend une commende Shell en paramètre et l'éxecute
	 * @param une commande Shell à éxecuter sous forme d'un string
	 * @return Le resultat de la commande.
	 */
	public static List<String> executeCommand(String command) {

		List<String> getExecute = new ArrayList<String>();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";			
			while ((line = reader.readLine())!= null) {
				getExecute.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getExecute;
	}

	/**
	 * Prend une commende Shell en paramètre et l'éxecute
	 * @param une commande Shell à éxecuter sous forme d'un string
	 * @return Le resultat de la commande.
	 */
	public static List<String> executeCommand(String [] command) {

		List<String> getExecute = new ArrayList<String>();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";			
			while ((line = reader.readLine())!= null) {
				getExecute.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return getExecute;
	}

	/**
	 * Retourne la commande Stat à utiliser
	 * @return l'attribut statCommand de cette classe.
	 */
	public String getStatCommand() {
		return this.statCommand;
	}

	/**
	 * Set l'attribut commandeStat
	 */
	public void setStatCommand(String newStatCommand) {
		this.statCommand=newStatCommand;
	}

	/**
	 * 
	 */


	/**
	 * Construction de la commende State
	 */
	public String [] constructStatCommandSelect(Map<String,Boolean> flagsAttributs) {
		//stat --format "file name : %n, Type : %F, Size : %s, Access Rights : %A, Time Of last Access : %x, Time of last modif %y " target
		String [] initStatCommand = new String [4];
		initStatCommand[0]="stat";
		initStatCommand[1]="--format";
		initStatCommand[2]=convertSelectAttributes(flagsAttributs);
		initStatCommand[3]="";
		return initStatCommand;
	}
	
	/**
	 * Construction de la commende State
	 */
	public String [] constructStatCommandFrom(String [] AttributSelect, String path) {
		//stat --format "file name : %n, Type : %F, Size : %s, Access Rights : %A, Time Of last Access : %x, Time of last modif %y " target
		AttributSelect[3]=path;
		
		return AttributSelect;
	}


	/**
	 * Convert attributs of clause Select to OS
	 */
	public  String convertSelectAttributes(Map<String,Boolean> flagsAttributs) {
		String arguments = new String("");
		
		
		
		if(flagsAttributs.get(attributsOfFile.NOM.get()))
			arguments = arguments + "file name : %n, " ;
		if(flagsAttributs.get(attributsOfFile.TYPE.get()))
			arguments = arguments + "Type : %F, " ;
		if(flagsAttributs.get(attributsOfFile.SIZE.get()))
			arguments = arguments + "Size : %s, " ;
		if(flagsAttributs.get(attributsOfFile.ACCESRIGHTS.get()))
			arguments = arguments + "Access Rights : %A, " ;
		if(flagsAttributs.get(attributsOfFile.DATELACCES.get()))
			arguments = arguments + "Time Of last Access : %x, " ;
		if(flagsAttributs.get(attributsOfFile.DATELMODIFICATION.get()))
			arguments = arguments + " Time of last modif %y " ;
		
		return arguments;
	}

	/**
	 * Get folder and Containers
	 */
	public Map<String,List<String>> getFolderAndContainers(){
		return this.FolderAndContiners;
	}
	
	/**
	 * Get contenets of a folder
	 * @param name of folder
	 */
	public List<String> getFolderAndContainers(String folder){
		if(getFolderAndContainers().containsKey(folder))
			return getFolderAndContainers().get(folder);
		else return null;
	}

	/**
	 * Remplis un folder avec ses fichiers contenus
	 */
	public void addToFolderAndContainers(String path, List<String> contenents){
		this.FolderAndContiners.put(path, contenents);
	}

}
