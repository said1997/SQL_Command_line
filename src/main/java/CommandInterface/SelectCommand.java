package CommandInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GestionnaireJDBC.BDD;
import OsTraitement.FindCommand;
import OsTraitement.OsTraitement;
import QueryTraitement.querySelect;

public class SelectCommand implements InterfaceCommand {
	
	private String Command;
	
	public SelectCommand(String query) {
		this.Command=query;
	}

	@Override
	public void execute() {
		BDD.setNomBdd("BDD");
		BDD.Bdd();
		querySelect queryselect=new querySelect(this.Command);
		queryselect.ExtractClausesSelect();
		queryselect.ExtractClausesFrom();
		Map<String, List<String>> result=queryselect.getQueryResult();
		FindCommand cmd = new FindCommand(result);
		String [] tab = cmd.addSelectTraduction();
		String [] colonne;
		
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		//Cration de la bdd en mémoire ainsi que la création du header de la table à afficher
		for(String tableName : result.get("FROM") ) {
			
			try {
				BDD.resetBddTables(tableName, result.get("SELECT"));
			} catch (Exception e) {
				System.out.println("Erreur de création de tables dans la bdd");
				e.printStackTrace();
			}
		for(String colName : result.get("SELECT")) {
			headersList.add(colName);
		}
		}
		List<List<String>> rowsList = new ArrayList<>();
		
		cmd.AddFromTraduction();
		for(String s : result.get("FROM")) {
			for(String c : cmd.getFolderAndContainers(s)) {
				for(String d : OsTraitement.executeCommand(cmd.constructStatCommandFrom(tab,c))) {
					colonne=d.split(",");
					List<String> row = new ArrayList<>();
					for(int i = 0; i<colonne.length; i++) {
						if(colonne[i].length()>1)
						row.add(colonne[i]);
						}
					
					rowsList.add(row);
				}
				
			}
		}
		//select size,name from src
		System.out.println(tableGenerator.generateTable(headersList, rowsList)); 
	}
	
}
