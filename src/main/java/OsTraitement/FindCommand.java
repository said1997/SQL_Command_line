package OsTraitement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QueryTraitement.attributsOfFile;

public class FindCommand extends OsTraitement {
	//https://www.howtogeek.com/451022/how-to-use-the-stat-command-on-linux/
	//lien documentation ls http://manpagesfr.free.fr/man/man1/ls.1.html
	//du -sh --apparent-size ./Bureau/ afiicher la taille réelle
	//ls -alld */ afficher tout les dossier 
	//ls -allF | grep -v '/$' afficher tout les fichiers
	//stat --format "la taille du fichier %z" src/
	/**
	 * Constructeur de cette classe
	 * @param toTraduct
	 */
	public FindCommand(Map<String, List<String>> toTraduct) {
		super(toTraduct);
	}

	/**
	 * Traduction de la clause From language OS.
	 * @return paths la liste des clauses from
	 */
	public List<String> addFromTraduction() {
		List<String> contenent = new ArrayList<String>();
		List<String> paths = new ArrayList<String>();
		
		if (queryToOs.containsKey("FROM")) {
			paths = queryToOs.get("FROM");
			for(String p : paths) {
				contenent=executeCommand(p);
				
			}
		}
		
		return paths;
	}

	/**
	 * Traduction de la clause select en commande OS
	 */
	public String[] addSelectTraduction() {

		Map<String, Boolean> flagAttribut = new HashMap<String, Boolean>();
		
		initFlagsAttributs(flagAttribut);

		if (queryToOs.containsKey("SELECT")) {
			addToCommand("ls");
			List<String> list = queryToOs.get("SELECT");
			for(String l : list) {
				if(l.equals("*")) {
					
				}
				if(l.equals(attributsOfFile.NOM.get())) {
					setFlagsAttributs(flagAttribut, attributsOfFile.NOM.get());
				}
				if(l.equals(attributsOfFile.TYPE.get())) {
					setFlagsAttributs(flagAttribut, attributsOfFile.TYPE.get());
				}
				if(l.equals(attributsOfFile.DATE.get())) {
					setFlagsAttributs(flagAttribut, attributsOfFile.DATE.get());
				}
				if(l.equals(attributsOfFile.SIZE.get())) {
					setFlagsAttributs(flagAttribut, attributsOfFile.SIZE.get());
				}
				if(l.equals(attributsOfFile.DATELACCES.get())) {
					setFlagsAttributs(flagAttribut, attributsOfFile.DATELACCES.get());
				}
				if(l.equals(attributsOfFile.DATELMODIFICATION.get())) {
					setFlagsAttributs(flagAttribut, attributsOfFile.DATELMODIFICATION.get());
				}
				if(l.equals(attributsOfFile.ACCESRIGHTS.get())) {
					setFlagsAttributs(flagAttribut, attributsOfFile.ACCESRIGHTS.get());
				}
			}
			return constructStatCommand(flagAttribut);
		}
		System.err.println("Pas de clause Select");
		return null;
	}
	/**
	 * Ajouter un attribut a la ligne de commande
	 * @param toAdd le paramètre os correspendant à ajouter dans Command.
	 */
	private void addToCommand(String toAdd) {
		setCommand(getCommand() + toAdd);

	}
	
	/**
	 * Initialisation des attributs de Select
	 */
	private Map<String, Boolean> initFlagsAttributs(Map<String, Boolean> initFlags){
			
		initFlags.put(attributsOfFile.DATE.get(), false);
		initFlags.put(attributsOfFile.TYPE.get(), false);
		initFlags.put(attributsOfFile.NOM.get(), false);
		initFlags.put(attributsOfFile.SIZE.get(), false);
		initFlags.put(attributsOfFile.EXTENTION.get(), false);
		initFlags.put(attributsOfFile.DATELMODIFICATION.get(), false);
		initFlags.put(attributsOfFile.DATELACCES.get(), false);
		initFlags.put(attributsOfFile.ACCESRIGHTS.get(), false);
	
		return initFlags;
	}
	
	/**
	 * Mettre un attribut de la clause select en true c'est à dire qu'on doit le chercher
	 * @return la liste après modification
	 */
	private Map<String, Boolean> setFlagsAttributs(Map<String, Boolean> toSet, String Key){
		
		toSet.put(Key, true);
		return toSet;
	}
	

}
