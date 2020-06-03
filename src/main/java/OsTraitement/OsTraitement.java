package OsTraitement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.*;

import QueryTraitement.attributsOfFile;
/**
 * Classe qui se charge de récupérer tout les attributs souhaités d'un fichier sur le disque.
 * @author UVSQTer
 *
 */
public abstract class OsTraitement {

	/**
	 * La Map qui contient la requete parsée Comme clé la clause et le contenu est la liste de ses éléments.
	 */
	protected Map<String,List<String>> queryToOs;

	/**
	 * Une map qui contient un dossier et la liste de ses fichiers ou dossiers.
	 * Chaque dossier est une clause de l'attrubt where.
	 */
	protected  Map<String,List<String>> FolderAndContiners;

	/**
	 * Constructeur de La classe OsTraitement
	 * @param toTraduct un mape qui contien comme clé la clause de la requete et la liste de ses attributs qui seront 
	 * traduits en méthode getAttribute à executer.
	 */
	public OsTraitement(Map<String,List<String>> toTraduct) {
		this.queryToOs = new HashMap<String,List<String>>();	
		this.FolderAndContiners = new HashMap<String,List<String>>();
		this.queryToOs=toTraduct;
	}

	/**
	 * Lister le contenu d'un dosier.
	 * @param path le chemin du dossier à lister.
	 * @return list Le resultat de la recherche sous forme d'une liste.
	 */
	public static List<String> DiskFileExplore(String path) {
		DiskFileExplorer content = new DiskFileExplorer(path, false);
		return content.list();
	}

	/**
	 * Prend des attributs préparés sous forme d'un tableau en paramètre à executer.
	 * Le tableau permet d'obtenir les informations sur un fichier selon les attributs entrés par l'utilisateur.
	 * @param prepare les attributs préparés à éxecuter sous forme d'un tableau.
	 * @return attribs Le resultat de la recherche sous forme d'une Map<attribut,contenu>.
	 */
	public  Map<String,Object> execute(String [] prepare) {

		Path path = Paths.get(prepare[2]);
		try {
			
			Map<String,Object> exec = Files.readAttributes(path, prepare[0]);
			Map<String, Object> attribs = new HashMap<>(exec);
			if(isInSelect(attributsOfFile.DATE.get())) {
				attribs.put(attributsOfFile.DATE.get(),reOraniseDate(attribs.get("creationTime")));
			}
			if(isInSelect(attributsOfFile.DATELACCES.get())) {
				attribs.put(attributsOfFile.DATELACCES.get(),reOraniseDate(attribs.get("lastAccessTime")));
			}
			if(isInSelect(attributsOfFile.DATELMODIFICATION.get())) {
				attribs.put(attributsOfFile.DATELMODIFICATION.get(),reOraniseDate(attribs.get("lastModifiedTime")));
			}
			
			if(isInSelect(attributsOfFile.NAME.get())) {
				Path p = Paths.get(prepare[2]);
				Path fileName = p.getFileName();
				attribs.put(attributsOfFile.NAME.get(),fileName.toString());
			}
			
			if(isInSelect(attributsOfFile.SIZE.get())) {
				attribs.put(attributsOfFile.SIZE.get(),attribs.get("size"));
			}
			if(isInSelect(attributsOfFile.PATH.get()))
				attribs.put(attributsOfFile.PATH.get(), (Object)prepare[2]);
			if(isInSelect(attributsOfFile.TYPE.get())) {
				if((boolean) attribs.get("isDirectory")) { 
					attribs.put(attributsOfFile.TYPE.get(), "Directory");
					attribs.remove("isDirectory");
				}
				else {
					attribs.put(attributsOfFile.TYPE.get(), "File");
					attribs.remove("isDirectory");
				}
			}

			if(isInSelect(attributsOfFile.ACCESRIGHTS.get())) {
				String permissions = Permessions(path,(String)attribs.get(attributsOfFile.TYPE.get()));
				attribs.put(attributsOfFile.ACCESRIGHTS.get(), permissions);
			}
			
			return attribs;

		} catch (IOException e) {

		}
		return null;


	}

	/**
	 * Construction des attributs pour la méthode readAttributs() selon les attribut contenus dans la clause Select.
	 * @return initStatCommand les attributs préparés à la quelle le chemin du fichier sur la quelle l'executer sera ajouter après.
	 */
	public String [] getAttributeMethodeforSelectClause() {
	
		String [] initStatCommand = new String [3];
		initStatCommand[0]=convertSelectAttributes();
		initStatCommand[1]=convertSelectAttributes();
		initStatCommand[2]="";
		return initStatCommand;
	}

	/**
	 * Construction des attributs pour la méthode readAttributs() pour un chemin de fichier ou dossier donné.
	 * @param AttributSelect Contient les attributs pour la méthode readAttributs() construit à partir de la clause select.
	 * @param path Le chemin sur le quel executer la méthode readAttributs().
	 * @return AttributSelect le tableau qui contient les attributs pour la méthode readAttributs() totalement construit à executer.
	 */
	public String [] constructStatCommandFrom(String [] AttributSelect, String path) {
		AttributSelect[2]=path;
		return AttributSelect;
	}

	/**
	 * Prend les attributs présent dans la clause Select et les transforme en attributs pour la méthode readAttributs().
	 * @return argument Les attributs de la clause select traduit en attributs de la méthode readAttributs().
	 */
	public String convertSelectAttributes() {
		//basic:size,lastModifiedTime,creationTime
		String arguments = new String("basic:");
		List<String> listSelectAttributs = new ArrayList<String>();
		Map<String,String> SelectAttToStatAtt = new HashMap<String,String>();
		SelectAttToStatAtt.put(attributsOfFile.TYPE.get(),"isDirectory,");
		SelectAttToStatAtt.put(attributsOfFile.SIZE.get(),"size,");	
		SelectAttToStatAtt.put(attributsOfFile.DATE.get(),"creationTime,");
		SelectAttToStatAtt.put(attributsOfFile.DATELACCES.get(),"lastAccessTime,");
		SelectAttToStatAtt.put(attributsOfFile.DATELMODIFICATION.get(),"lastModifiedTime,");
		listSelectAttributs=this.queryToOs.get("SELECT");
		for(String s : listSelectAttributs) {
			if(SelectAttToStatAtt.get(s)!=null)
				arguments = arguments + SelectAttToStatAtt.get(s);
		}
		arguments = arguments.substring(0, arguments.length() - 1);
		if (!arguments.contains(":"))
			arguments = arguments +":"+"size";
		return arguments;
	}

	/**
	 * Get le folder et ses Containers chaque attribut de la clause from est un dossier qui a un contenu de fichier et dossiers.
	 * @return FolderAndContainers une Map qui a pour clé le dossier et contenu la liste des ses élements.
	 */
	public Map<String,List<String>> getFolderAndContainers(){
		return this.FolderAndContiners;
	}

	/**
	 * Retourner le contenu d'un dossier donné en paramète.
	 * @param Nom du dossier qui est la clé.
	 * @return Une liste qui contient Les éléments du dossier.
	 */
	public List<String> getFolderAndContainers(String folder){
		if(getFolderAndContainers().containsKey(folder))
			return getFolderAndContainers().get(folder);
		else return null;
	}

	/**
	 * Ajouter un dossier et son contenu.
	 * @param path le chemin do dossier.
	 * @param contenents la liste des fichier et dossiers contenus dans le path.
	 */
	public void addToFolderAndContainers(String path, List<String> contenents){
		this.FolderAndContiners.put(path, contenents);
	}

	/**
	 * Retourne la structure contenant les clauses et les attributs.
	 * @return queryToos Map<Clause,attributs>.
	 */
	public Map<String, List<String>> getQuerytoOs(){
		return this.queryToOs;
	}

	/**
	 * Verifier si un attribut est dans clause Select
	 * @return true Boolean si l'attribut existe.
	 * @return false Boolean si l'attribut n'existe pas.
	 */
	public Boolean isInSelect(String attribut){
		for(String attr : getQuerytoOs().get("SELECT")) {
			if (attr.equals(attribut))
				return true;
		}
		return false;
	}


	/**
	 * Avoir les permissions d'un fichier et les représenter comme un affichage dans le terminal.
	 * @param path chemin du fichier.
	 * @param type Type du fichier.
	 * @return s Permissions sous la forme -rwx-wxr-x
	 */
	private String Permessions(Path path,String type) {
		try {

			String attrubuts="posix:permissions,"; 
			Map<String,Object> attribs = Files.readAttributes(path, attrubuts);
			String attr = attribs.get("permissions").toString();
			attr=attr.replace(" ", "");
			attr=attr.replace("[", "");
			attr=attr.replace("]", "");
			attr = attr +","+type;
			String [] tab = attr.split(",");
			Character [] permissions = {'-','-','-','-','-','-','-','-','-','-'};
			for(String s : tab) {
				switch (s)
				{
				case "Directory":
					permissions[0]='d';
					break;
				case "OWNER_READ":
					permissions[1]='r';
					break;
				case "OWNER_WRITE":
					permissions[2]='w';
					break;
				case "OWNER_EXECUTE":
					permissions[3]='x';
					break;
				case "GROUP_READ":
					permissions[4]='r';
					break;
				case "GROUP_WRITE":
					permissions[5]='w';
					break;
				case "GROUP_EXECUTE":
					permissions[6]='x';
					break;
				case "OTHERS_READ":
					permissions[7]='r';
					break;
				case "OTHERS_WRITE":
					permissions[8]='w';
					break;
				case "OTHERS_EXECUTE":
					permissions[9]='x';
					break;  

				}
			}
			String s="";
			for(Character c : permissions)
				s=s+c;
			return s;
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}

	/**
	 * Réorganisation de le date sous format YYYY-MM-DD hh-mm-ss
	 * @return s la date sous le format YYYY-MM-DD hh-mm-ss 
	 */
	private String reOraniseDate(Object object) {
		if(object.toString().contains(".")) {
			String[] date = object.toString().split("\\.");
			date[0].replace("Z","");
			return date[0].replace("T", " ");
		}
		else {
			String s = object.toString().replace("Z", ""); 
			return s.replace("T", " ");
		}
	}
}
