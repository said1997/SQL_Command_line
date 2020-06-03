package OsTraitement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe qui permet dexplorer le contenu d'un dossier. 
 * @author UVSQTer
 *
 */
public class DiskFileExplorer {

	private String initialpath = "";
	private Boolean recursivePath = false;
	public int filecount = 0;
	public int dircount = 0;
	private List<String> list;

	/**
	 * Constructeur
	 * @param path chemin du répertoire
	 * @param subFolder analyse des sous dossiers
	 */
	public DiskFileExplorer(String path, Boolean subFolder) {
		super();
		this.initialpath = path;
		this.recursivePath = subFolder;
		this.list = new ArrayList<String>();
	}
	/**
	 * Permet d'avoir la liste des dossier et fichiers contenu dans un dossier.
	 * @return Liste de fichiers et dossiers
	 */
	public List<String> list() {
		this.listDirectory(this.initialpath);
		return this.list;
	}

	private void listDirectory(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory() == true) {
					this.list.add(files[i].getAbsolutePath());

				} else {
					this.list.add(files[i].getAbsolutePath());
				}
				if (files[i].isDirectory() == true && this.recursivePath == true) {
					this.listDirectory(files[i].getAbsolutePath());
				}
			}
		}
	}    
}
