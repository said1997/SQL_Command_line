package OsTraitement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QueryTraitement.attributsOfFile;

public class FindCommand extends OsTraitement {
	
	/**
	 * Constructeur de la classe OsTraitement
	 * @param toTraduct map qui contient comme clé Une clause et la liste des ses attributs.
	 */
	public FindCommand(Map<String, List<String>> toTraduct) {
		super(toTraduct);
	}


	/**
	 * Traduction de la clause select en commande OS.
	 * Pour chaque attribut présent dans la clause select on met son flag à true.
	 * @return Un tableau contenant la construction de la commende Shelle à qui le chemin du dossier sur la quel l'ecuter sera donné après.
	 */
	public String[] addSelectTraduction() {

		Map<String, Boolean> flagAttribut = new HashMap<String, Boolean>();

		initFlagsAttributs(flagAttribut,false);

		if (queryToOs.containsKey("SELECT")) {
			List<String> list = queryToOs.get("SELECT");
			for(String l : list) {
				if(l.equals("*")) {
					initFlagsAttributs(flagAttribut,true);
				}
				else {
					if(l.equals(attributsOfFile.PATH.get())) {
						setFlagsAttributs(flagAttribut, attributsOfFile.PATH.get());
					}
					if(l.equals(attributsOfFile.TYPE.get())) {
						setFlagsAttributs(flagAttribut, attributsOfFile.TYPE.get());
					}
					if(l.equals(attributsOfFile.DATE.get())) {
						setFlagsAttributs(flagAttribut, attributsOfFile.DATE.get());
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
			}
			return super.constructStatCommandSelect();
		}
		System.err.println("Pas de clause Select");
		return null;
	}
	

	/**
	 * Traduction de la clause From language OS.
	 * Chaque attribut de from est un dossier.
	 * @return paths la liste des clauses from
	 */
	public void AddFromTraduction(){
		List<String> paths = new ArrayList<String>();
		if (queryToOs.containsKey("FROM")) {
			paths = queryToOs.get("FROM");
			if(paths.size() != 0) {
				for(String p : paths) {
					addToFolderAndContainers(p, getListElementsOfFolder(p));
				}
			}
			else {
				System.err.println("Aucun attribut From");
			}
		}
	}
	
	/**
	 * Obtenir tout les élément d'un dossier.
	 * @param le chemin d'un dossier.
	 * @return List les élément d'un dossier
	 */
	public List<String> getListElementsOfFolder(String path) {
		List <String> ResultLsCommand = new ArrayList<String>();
		ResultLsCommand = super.DiskFileExplore(path);
		return ResultLsCommand;

	}
	
	/**
	 * Mettre un attribut de la clause select en true c'est à dire qu'il sera ajouté à la construction de la commande Stat à executer.
	 * @param toSet la mao qui contient comme clé lattribut de la clause select et comme valeur l'état de de lattribut si il est présent ou non.
	 * @param Key lattribut qui sera activé.
	 * @return toSet la map après avoir définit le statut de son attribut.
	 */
	private Map<String, Boolean> setFlagsAttributs(Map<String, Boolean> toSet, String Key){
		toSet.put(Key, true);
		return toSet;
	}

	/**
	 * Initialisation des flags pour l'ensemble des attributs de la clause select.
	 * @param toinitFlags une map qui a comme clé le flag et son état présent ou pas dans la clause select.
	 * @return la map après initialisation.
	 */
	private Map<String, Boolean> initFlagsAttributs(Map<String, Boolean> toInitFlags, Boolean isInClauseSelect){

		toInitFlags.put(attributsOfFile.DATE.get(), isInClauseSelect);
		toInitFlags.put(attributsOfFile.TYPE.get(), isInClauseSelect);
		toInitFlags.put(attributsOfFile.PATH.get(), isInClauseSelect);
		toInitFlags.put(attributsOfFile.DATE.get(), isInClauseSelect);
		toInitFlags.put(attributsOfFile.SIZE.get(), isInClauseSelect);
		toInitFlags.put(attributsOfFile.DATELMODIFICATION.get(), isInClauseSelect);
		toInitFlags.put(attributsOfFile.DATELACCES.get(), isInClauseSelect);
		toInitFlags.put(attributsOfFile.ACCESRIGHTS.get(), isInClauseSelect);

		return toInitFlags;
	}

	

	/**
	 * Méthode qui ajoute le path absolut aux élements d'un dossier.
	 * @param  Element les éléments d'un dossier.
	 * @param  Apath l'absolute path à ajouter à chaque élément du dossier.
	 * @return Les elements du dossier avec le chemin absolut ajouté.
	 *

	private List<String> AddPathAbsolut(List<String> Elements , String Apath){
		List<String> ResultLs = new ArrayList<String>();
		
		for(String Ap : Elements) {
			//if(Ap.endsWith("/"))
			ResultLs.add(Apath+"/"+Ap);
			//else
			 //ResultLs.add(Apath);
		}
		return ResultLs;
	}*/
	
}
