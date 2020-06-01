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
	ACCESRIGHTS("PERMISSIONS");
	
	private String attribut;
	
	private attributsOfFile(String A) {
		this.attribut=A;
	}
	
	public String get() {
		return this.attribut;
	}
	
}
