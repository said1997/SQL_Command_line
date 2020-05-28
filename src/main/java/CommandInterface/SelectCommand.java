package CommandInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		
		querySelect queryselect=new querySelect(this.Command);
		queryselect.ExtractClausesSelect();
		queryselect.ExtractClausesFrom();
		Map<String, List<String>> result=queryselect.getQueryResult();
		FindCommand cmd = new FindCommand(result);
		String [] tab = cmd.addSelectTraduction();
		String [] colonne;
		
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		
		for(String colName : result.get("SELECT"))
			headersList.add(colName);
		List<List<String>> rowsList = new ArrayList<>();
		
		cmd.AddFromTraduction();
		for(String s : result.get("FROM")) {
			for(String c : cmd.getFolderAndContainers(s)) {
				for(String d : OsTraitement.executeCommand(cmd.constructStatCommandFrom(tab,c))) {
					System.out.println(d);
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
