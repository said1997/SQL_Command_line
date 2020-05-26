package QueryTraitement;

public enum attributsOfFile {
	/**
	 * Les attributs de la table fichier
	 */
	TYPE("TYPE"),
	NOM("NOM"),
	SIZE("SIZE"),
	EXTENTION("EXTENTION"),
	DATE("DATE"),
	DATELACCES("DATELASTACCESS"),
	DATELMODIFICATION("DATELASTMODIFICATION");
	
	private String attribut;
	
	private attributsOfFile(String A) {
		this.attribut=A;
	}
	
	public String getAttribut() {
		return this.attribut;
	}
	
}
