package OsTraitement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.*;

import QueryTraitement.attributsOfFile;

public abstract class OsTraitement {

	/**
	 * La mape qui contient la requete parsée Comme clé la clause et le contenu est la liste de ses éléments.
	 */
	protected Map<String,List<String>> queryToOs;

	/**
	 * Une map qui contient un dossier et la liste de ses fichiers ou dossiers.
	 * Chaque dossier est une clause de l'attrubt where.
	 */
	protected  Map<String,List<String>> FolderAndContiners;

	/**
	 * Constructeur de La classe OsTraitement
	 * @param toTraduct un mape qui contien comme clé la clause de la requette et la liste de ses attributs qui seront traduis en une commande shell.
	 */
	public OsTraitement(Map<String,List<String>> toTraduct) {
		this.queryToOs = new HashMap<String,List<String>>();	
		this.FolderAndContiners = new HashMap<String,List<String>>();
		this.queryToOs=toTraduct;
	}

	/**
	 * Prend une commende Shell en paramètre et l'éxecute
	 * @param une commande Shell sous forme d'un string à éxecuter.
	 * @return Le resultat de la commande.
	 */
	public static List<String> DiskFileExplore(String command) {
		DiskFileExplorer content = new DiskFileExplorer(command, false);
		return content.list();
	}

	/**
	 * Prend une commende Shell sous forme d'un tableau en paramètre à executer.
	 * Le tableau permet de construir la commende stat selon les attributs entrés par l'utilisateur.
	 * @param une commande Shell à éxecuter sous forme d'un tableau.
	 * @return Le resultat de la commande sous forme d'une liste String.
	 */
	public  Map<String,Object> executeCommand(String [] command) {

		Path path = Paths.get(command[2]);
		try {
			
			Map<String,Object> exec = Files.readAttributes(path, command[0]);
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
			
			if(isInSelect(attributsOfFile.SIZE.get())) {
				attribs.put(attributsOfFile.SIZE.get(),attribs.get("size"));
			}
			if(isInSelect(attributsOfFile.PATH.get()))
				attribs.put(attributsOfFile.PATH.get(), (Object)command[2]);
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
	 * Construction de la commande Stat selon les attribut contenus dans la clause Select
	 * @param flagsAttributs une map qui indique pour chaque attribut du ficher s'il est contenu dans la clause select.
	 * @return initStatCommand la commande stat construite à la quelle le chamin du fichier sur la quelle l'executer sera ajouter après.
	 */
	public String [] constructStatCommandSelect(Map<String,Boolean> flagsAttributs) {
		//[0]stat [1]--format [2]"file name : %n, Type : %F, Size : %s, Access Rights : %A, Time Of last Access : %x, Time of last modif %y " [3]path
		String [] initStatCommand = new String [3];
		initStatCommand[0]=convertSelectAttributes(flagsAttributs);;
		initStatCommand[1]=convertSelectAttributes(flagsAttributs);
		initStatCommand[2]="";
		return initStatCommand;
	}

	/**
	 * Construction de la commande Stat pour un chemin de fichier ou dossier donné.
	 * @param AttributSelect Contient la commande stat construite à partir de la clause select.
	 * @param path Le chemin sur le quel executer la commande Stat.
	 * @return AttributSelect le tableau qui contient la commande stat totalement construite à executer.
	 */
	public String [] constructStatCommandFrom(String [] AttributSelect, String path) {
		AttributSelect[2]=path;
		return AttributSelect;
	}

	/**
	 * Prend les attributs présent dans la clause Select et les transforme en commande Shell.
	 * @param flagsAttributs les attributs selon les quels la commande stat sera construite.
	 * @return argument Les attributs de la clause select traduit en shell.
	 */
	public  String convertSelectAttributes(Map<String,Boolean> flagsAttributs) {
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
	 * @return FolderAndContainers une mape qui a pour clé le dossier et contenu la liste des ses élements.
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
	 * Retourne la structure contenant les clauses et les attributs
	 */
	public Map<String, List<String>> getQuerytoOs(){
		return this.queryToOs;
	}

	/**
	 * Verifier si un attribut est dans clause Select
	 * Boolean si l'attribut existe.
	 */
	public Boolean isInSelect(String attribut){
		for(String attr : getQuerytoOs().get("SELECT")) {
			if (attr.equals(attribut))
				return true;
		}
		return false;
	}


	/**
	 * Mettre les permission comme l'affichage dans le terminal
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
