package OsTraitement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QueryTraitement.attributsOfFile;

public class FindCommand extends OsTraitement {
	
	//lien documentation ls http://manpagesfr.free.fr/man/man1/ls.1.html
	//du -sh --apparent-size ./Bureau/ afiicher la taille réelle
	//ls -alld */ afficher tout les dossier 
	//ls -allF | grep -v '/$' afficher tout les fichiers

	/**
	 * Constructeur de cette classe
	 * @param toTraduct
	 */
	public FindCommand(Map<String, List<String>> toTraduct) {
		super(toTraduct);
	}

	/**
	 * Traduction de requette de type Select en OS.
	 */
	private void ExtractFind() {


	}

	/**
	 * Traduction de la clause select en commande OS
	 */
	public void addSelectTraduction() {

		Map<String, Boolean> flagAttribut = new HashMap<String, Boolean>();
		
		initFlagsAttributs(flagAttribut);

		if (queryToOs.containsKey("SELECT")) {

			addToCommand("ls");
			List<String> list = queryToOs.get("SELECT");
			for(String l : list) {
				if(l.equals("*")) {
					
				}
				if(l.equals("NAME")) {
				
				}


			}
		}
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
			
		initFlags.put("NAME", false);
		initFlags.put("TYPE", false);
		initFlags.put("SIZE", false);
		initFlags.put("EXTENTION", false);
	
		return initFlags;
	}
	

}
