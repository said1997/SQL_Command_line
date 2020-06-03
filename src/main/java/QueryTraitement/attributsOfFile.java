package QueryTraitement;
/**
 * Class Enum contenant les attributs d'un fichier ou dossier et le manuel d'utilisation.
 * @author UVSQTer.
 *
 */
public enum attributsOfFile {
	/**
	 * Les attributs de la table fichier
	 */
	TYPE("TYPE"),
	PATH("PATH"),
	NAME("NAME"),
	SIZE("SIZE"),
	DATE("CREATIONTIME"),
	DATELACCES("LASTACCESSTIME"),
	DATELMODIFICATION("LASTMODIFIEDTIME"),
	ACCESRIGHTS("PERMISSIONS"),
	GUIDEUTILISATION("Bienvenue dans l'interface de recherche de fichiers ou dossiers sur votre disque par des requêtes SQL.\n"
			+ "-Pour afficher l'historique de vos requêtes tapez show.\n"
			+ "-Pour chercher sur votre disque dur tapez une requête Sql avec une syntaxe valide.\n"
			+ "-Pour des chemins contenant  des caractères spéciaux ou majuscules veuillez les mettre entre \" \".\n"
			+ "Exemple : select * from \"/home/user/Bureau\"\n"
			+ "-Pour filtrer par date veuillez respécter la notation suivante 'yyyy-mm-dd hh:mm:ss'.\n"
			+ "Exemple Select path,permissions,size,creationTime from \"/home/user/Bureau\" where creationtime > '2020-05-01 00:00:00' and size > 5000\n"
			+ "-Attributs de fichier : type,path,name,size,creationTime,lastAccessTime,lastModificationTime,permissions (pas d'importance pour les majuscules et minuscules).\n"
			+ "-Pour quitter l'application tapez exit.\n\n"
			+ "*Afin d'avoir un bon affichage mettez le terminal en plein écran. C'est à vous !\n");
	
	private String attribut;
	
	private attributsOfFile(String A) {
		this.attribut=A;
	}
	/**
	 * Retourne l'énumération.
	 * @return attribut l'énumération.
	 */
	public String get() {
		return this.attribut;
	}
	
}
