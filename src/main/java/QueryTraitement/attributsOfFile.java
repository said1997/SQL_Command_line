package QueryTraitement;

public enum attributsOfFile {
	/**
	 * Les attributs de la table fichier
	 */
	TYPE("TYPE"),
	NOM("PATH"),
	SIZE("SIZE"),
	EXTENTION("EXTENTION"),
	DATE("DATE"),
	DATELACCES("DATELASTACCESS"),
	DATELMODIFICATION("DATELASTMODIFICATION"),
	ACCESRIGHTS("ACCESSRIGHTS");
	
	private String attribut;
	
	private attributsOfFile(String A) {
		this.attribut=A;
	}
	
	public String get() {
		return this.attribut;
	}
	
}
