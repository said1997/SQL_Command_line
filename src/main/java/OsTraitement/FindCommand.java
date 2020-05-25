package OsTraitement;

import java.util.List;
import java.util.Map;

public class FindCommand extends OsTraitement {


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
	private void addSelectTraduction() {

		if (queryToOs.containsKey("Select")) {

			Command=addToCommand("find");
			List<String> list = queryToOs.get("Select");

			for(String l : list) {
				if(l.equals("name")) {
					this.Command = addToCommand(" -name");
				}
				if(l.equals("*")) {
					this.Command = addToCommand("ls --all");
				}

			}
		}
	}
    /**
     * 
     * @param toAdd le paramètre os correspendant.
     * @return la commande.
     */
	private String addToCommand(String toAdd) {
		this.Command = this.Command + toAdd;
		return Command;
	}


}
