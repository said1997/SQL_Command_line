package QueryTraitement;

public enum attributsOfFile {
	/**
	 * Les attributs de la table fichier
	 */
	TYPE("TYPE"),
	PATH("PATH"),
	SIZE("SIZE"),
	DATE("CREATIONTIME"),
	DATELACCES("LASTACCESSTIME"),
	DATELMODIFICATION("LASTMODIFIEDTIME"),
	ACCESRIGHTS("PERMISSIONS"),
	GUIDEUTILISATION("Bienvenue dans l'intérface de recherche de fichiers ou dossiers sur votre disque par des requettes SQL.\n"
			+ "-Pour afficher l'historique de vos requetes tapez show.\n"
			+ "-Pour chercher sur votre disque dur tapez un requette sql avec une syntaxe valide.\n"
			+ "-Pour des chemins contenant des caractères spéciaux ou majuscule veuillez les mettre entre \" \".\n"
			+ "Exemple : select * from \"/home/user/Bureau\"\n"
			+ "-Pour filtrer par date veuillez respécter la notation suivante 'yyyy-mm-dd hh:mm:ss'.\n"
			+ "Exemple Select path,permissions,size,creationTime from \"/home/user/Bureau\" where creationtime > '2020-05-01 00:00:00' and size > 5000\n"
			+ "-Attributs de fichier : type,path,size,creationTime,lastAccessTime,lastModificationTime,permissions (pas d'importance pour les majuscules et minuscules).\n"
			+ "Pour quitter l'application tapez exit.\n"
			+ "-C'est à vous !\n");
	
	private String attribut;
	
	private attributsOfFile(String A) {
		this.attribut=A;
	}
	
	public String get() {
		return this.attribut;
	}
	
}
