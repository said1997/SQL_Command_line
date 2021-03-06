package CommandInterface;

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.calcite.sql.parser.SqlParseException;

import OsTraitement.FindCommand;
import QueryTraitement.querySelect;
import StructureInMemory.TablesInMemory;
/**
 * Classe pour le traitement d'une requete du début à l'affichage.
 * @author root
 *
 */
public class SelectCommand implements InterfaceCommand {
	/**
	 * la requete à traiter.
	 */
	private String query;
	/**
	 * Constructeur de la classe.
	 * @param query la requete à traiter.
	 */
	public SelectCommand(String query) {
		this.query=query;
	}
	/**
	 * Traitement de la requete.
	 */
	@Override
	public void execute() {
		querySelect queryselect=new querySelect(this.query);
		queryselect.ExtractClausesSelect();
		queryselect.ExtractClausesFrom();
		try {
			queryselect.ExtractClausesWhere();
		} catch (SqlParseException e) {e.printStackTrace();}
		catch (NullPointerException e1) {e1.printStackTrace();}
		Map<String, List<String>> result=queryselect.getQueryResult();
		//Création de la Structure en mémoire ainsi que la création du header de la table à afficher
		for(String tableName : result.get("FROM") ) {
			if(result.get("FROM").size()<2 && result.containsKey("WHERE"))
			ExecuteWithFiltration(result, tableName);
			else
				ExecuteWithoutFiltration(result, tableName);
		}

	}

	/**
	 * Executer le requete de l'utilisateur sans l'aide de la structure mémoire
	 * @param result Map qui contient le resultat du la requete après le traitement.
	 * @param path le chemin extrait de la clause from.
	 */
	void ExecuteWithoutFiltration(Map<String,List<String>> result,String path) {

		FindCommand cmd = new FindCommand(result);
		String [] getAttrinutePrepare = cmd.addSelectTraduction();
		cmd.AddFromTraduction();
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		try {

		} catch (Exception e) {
			System.out.println("Erreur de création de tables dans la bdd");
			e.printStackTrace();
		}
		for(String colName : result.get("SELECT")) {
			headersList.add(colName);
		}

		List<List<String>> rowsList = new ArrayList<>();
		for(String c : cmd.getFolderAndContainers(path)) {
			Map<String,Object> line = cmd.execute(cmd.constructStatCommandFrom(getAttrinutePrepare,c));
			List<String> row = new ArrayList<>();
			if (line != null) {	
				for (String colContnent : result.get("SELECT")) {
					row.add(line.get(colContnent).toString());
				}
				rowsList.add(row);
			}
		}
		System.out.print("\n"+path+"/");
		System.out.print(tableGenerator.generateTable(headersList, rowsList)); 
	}

	/**
	 * Executer le requete de l'utilisateur avec l'aide de la structure mémoire pour la filtration.
	 * @param result Map qui contient le resultat du la requete après le traitement.
	 * @param tableName le chemin extrait de la clause from.
	 * @throws SQLException 
	 */
	void ExecuteWithFiltration(Map<String,List<String>> result,String tableName) {
			
		TablesInMemory table;
		try {
			table = new TablesInMemory();
			FindCommand cmd = new FindCommand(result);
			cmd.AddFromTraduction();
			RemplirStructureInMeory(table, tableName, result, cmd);
			getResultOfFiltration(table, result);
			table.SupprimerTables(table.Connect(),tableName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
			
		
		
	}
	
	/**
	 * Remplissage de la structure mémoire
	 */
	private void RemplirStructureInMeory(TablesInMemory table,String tableName, Map<String,List<String>> result, FindCommand cmd) {
		String [] getAttrinutePrepare = cmd.addSelectTraduction();
		try {
			table.CreateTable(table.Connect(), tableName, result.get("SELECT"));
		} catch (Exception e) {
			System.out.println("Erreur de création de tables dans la structure mémoire");
			e.printStackTrace();
		}

		for(String c : cmd.getFolderAndContainers(tableName)) {
			Map<String,Object> line = cmd.execute(cmd.constructStatCommandFrom(getAttrinutePrepare,c));
			if (line != null) {	
				table.AddLineToTable(table.Connect(), tableName, line, result.get("SELECT"));
			}
		}
		System.out.print("\n"+tableName+"/");
	}
	
	/**
	 * Affichage du résultat de la filtration à l'aide de la structure mémoire
	 */
	private void getResultOfFiltration(TablesInMemory table, Map<String,List<String>> result) {
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		try {
			for(String colName : result.get("SELECT")) {
				headersList.add(colName);
			}
			List<List<String>> rowsList = new ArrayList<>();
			Statement statement = table.Connect().createStatement();
			ResultSet res;
			String chargingFromTable = this.query.replace("\"", ""); 
			System.out.println("(Le résultat de la filtration à l'aide de la structure mémoire) :");
			res = statement.executeQuery(chargingFromTable.replace("/", ""));
			while (res.next()) {
				List<String> row = new ArrayList<>();
				for(String s : result.get("SELECT"))
					row.add(res.getString(s));

				rowsList.add(row);
			}
			System.out.println(tableGenerator.generateTable(headersList, rowsList)); 
		} catch (SQLDataException e) {
			System.out.println("Veuillez respecter le format de la date suivant : 'yyyy-mm-dd hh:mm:ss' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
