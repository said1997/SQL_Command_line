package CommandInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import OsTraitement.FindCommand;
import OsTraitement.OsTraitement;
import QueryTraitement.querySelect;
import StructureInMemory.TablesInMemory;

public class SelectCommand implements InterfaceCommand {
	
	private String Command;
	
	public SelectCommand(String query) {
		this.Command=query;
	}

	@Override
	public void execute() {
		if(this.Command.contains("where") || this.Command.contains("WHERE"))
		TablesInMemory.Create();
		querySelect queryselect=new querySelect(this.Command);
		queryselect.ExtractClausesSelect();
		queryselect.ExtractClausesFrom();
		Map<String, List<String>> result=queryselect.getQueryResult();
		FindCommand cmd = new FindCommand(result);
		String [] tab = cmd.addSelectTraduction();
		String [] colonne;
		
		
		//Création de la Structure en mémoire ainsi que la création du header de la table à afficher
		for(String tableName : result.get("FROM") ) {
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			try {
				if(this.Command.contains("where") || this.Command.contains("WHERE"))
				TablesInMemory.CreateTableForAttrFrom(TablesInMemory.Connect(), tableName, result.get("SELECT"));
			} catch (Exception e) {
				System.out.println("Erreur de création de tables dans la bdd");
				e.printStackTrace();
			}
		for(String colName : result.get("SELECT")) {
			headersList.add(colName);
		}
		
		List<List<String>> rowsList = new ArrayList<>();
		
		cmd.AddFromTraduction();
		
			for(String c : cmd.getFolderAndContainers(tableName)) {
				for(String d : OsTraitement.executeCommand(cmd.constructStatCommandFrom(tab,c))) {
					if(this.Command.contains("where") || this.Command.contains("WHERE"))
					TablesInMemory.AddLineToTable(TablesInMemory.Connect(),tableName, d, result.get("SELECT"),this.Command);
					colonne=d.split(",");
					List<String> row = new ArrayList<>();
					for(int i = 0; i<colonne.length; i++) {
						if(colonne[i].length()>1)
						row.add(colonne[i]);
						}
					
					rowsList.add(row);
				}
				
			}
		System.out.println(tableGenerator.generateTable(headersList, rowsList)); 
		}
		
		if(this.Command.contains("where") || this.Command.contains("WHERE")) { 
		try {
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>();
			
			for(String colName : result.get("SELECT")) {
				headersList.add(colName);
			}
			
			List<List<String>> rowsList = new ArrayList<>();
			
			Statement statement = TablesInMemory.Connect().createStatement();
	    	ResultSet res;
	    	String chargingFromTable = this.Command.replace("\"", ""); 
	    	 System.out.println("Le résultat de la filtration à l'aide de la structure mémoire :");
	    	 res = statement.executeQuery(chargingFromTable.replace("/", ""));
	    	 
			 while (res.next()) {
				 List<String> row = new ArrayList<>();
		        	for(String s : result.get("SELECT"))
						row.add(res.getString(s));
		        	//System.out.println(" Chargement depuis bdd \n ");
		        	rowsList.add(row);
					}
			 System.out.println(tableGenerator.generateTable(headersList, rowsList)); 
			 //TablesInMemory.CloseConnexion();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
		}
         
       
	}
	
}
