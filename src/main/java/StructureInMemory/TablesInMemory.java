package StructureInMemory;


import java.nio.file.attribute.FileTime;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QueryTraitement.attributsOfFile;


public class TablesInMemory {

	/**
	 * Nom de la Structure en mémoire 
	 */
	private String StructInMemory;
	/**
	 * Connexion à la structure
	 */
	private Connection connection;

	/**
	 * Creation de la structure.
	 * @throws SQLException en cas d'erreur de creation
	 */
	public TablesInMemory()  {

		try {
			setNomStruct("StructTable");
			connection = DriverManager.getConnection("jdbc:derby:memory:"+getNomStruct()+";create=true");

		} catch (SQLException e) {
			System.err.println("Impossible de Créer la Table représentant un dossier et ses attributs en mémoire \n"
					+ "du à un arrêt brutal de l'application (sans commande exit)\n");
			System.err.println("Veuillez relancer l'application");
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Se connecter a la Structure.
	 * @return connection a la Structure
	 */

	public  Connection Connect() {
		try {
			return DriverManager.getConnection("jdbc:derby:memory:"+getNomStruct()+";create=false");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Fermer la connexion
	 * @throws SQLException 
	 */
	public void CloseConnexion() throws SQLException {
		connection.close();
	}
	/**
	 * Retourner le nom qui identifie la structure en mémoire
	 * @return nom de la structure
	 * @throws SQLException
	 */
	public  String getNomStruct() throws SQLException {
		return this.StructInMemory;
	}

	/**
	 * Donner un nom à la structure à créer en mémoire.
	 * @param name nom
	 */
	public  void setNomStruct(String name) {
		StructInMemory = name + "";
	}


	/* 
	 * supprime les tables.
	 * @param connect connection a la bdd
	 *
    private static void SupprimerTables(Connection conn,String nameTable) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.execute("drop table " +nameTable.replace("/", ""));
        } catch (SQLException e) {
        }

    }*/

	/**
	 * Creation de  la table Figure.
	 * @param connection connexion a la BDD
	 * @throws SQLException en cas d'erreur de creation
	 */
	public void CreateTable(Connection conn, String NameTable, List<String> attribut)throws SQLException {
		Map<String, String > map = new HashMap<String, String>();
		map.put(attributsOfFile.PATH.get(), attributsOfFile.PATH.get()+" varchar(80),");
		map.put(attributsOfFile.TYPE.get(), attributsOfFile.TYPE.get()+" varchar(80),");
		map.put(attributsOfFile.SIZE.get(), attributsOfFile.SIZE.get()+" int,");
		map.put(attributsOfFile.ACCESRIGHTS.get(), attributsOfFile.ACCESRIGHTS.get()+" varchar(80),");
		map.put(attributsOfFile.DATE.get(), attributsOfFile.DATE.get()+" timestamp,");
		map.put(attributsOfFile.DATELACCES.get(), attributsOfFile.DATELACCES.get()+" timestamp,");
		map.put(attributsOfFile.DATELMODIFICATION.get(), attributsOfFile.DATELMODIFICATION.get()+" timestamp,");

		String query = "CREATE TABLE "+ NameTable.replace("/", "") +" ( ";

		for(String Attr : attribut) {
			query = query + map.get(Attr);
		}

		if(query.endsWith(",")) 
			query = query.substring(0, query.length() - 1);

		query = query + " )";
		Statement statement = conn.createStatement();
		statement.execute(query);
	}

	/**
	 * Ajout d'un ligne à une TableEnMémoire
	 * @throws SQLException 
	 */

	public void AddLineToTable(Connection conn,String Table, Map<String,Object> line , List<String> attribut) {

		String attr="";
		String ValuePrep="";
		Map<String, String > map = new HashMap<String, String>();
		map.put(attributsOfFile.PATH.get(), attributsOfFile.PATH.get()+",");
		map.put(attributsOfFile.TYPE.get(), attributsOfFile.TYPE.get()+",");
		map.put(attributsOfFile.SIZE.get(), attributsOfFile.SIZE.get()+",");
		map.put(attributsOfFile.ACCESRIGHTS.get(), attributsOfFile.ACCESRIGHTS.get()+",");
		map.put(attributsOfFile.DATE.get(), attributsOfFile.DATE.get()+",");
		map.put(attributsOfFile.DATELACCES.get(), attributsOfFile.DATELACCES.get()+",");
		map.put(attributsOfFile.DATELMODIFICATION.get(), attributsOfFile.DATELMODIFICATION.get()+",");

		for(String A : attribut) {
			ValuePrep = ValuePrep + "? ,";
			attr = attr + map.get(A);
		}
		ValuePrep = ValuePrep.substring(0, ValuePrep.length() - 1);
		if(attr.endsWith(",")) 
			attr = attr.substring(0, attr.length() - 1);
		//System.out.println("INSERT INTO "+Table.replace("/", "")+" ("+ attr +") VALUES("+ValuePrep+")");

		PreparedStatement prepare;
		try {
			prepare = conn.prepareStatement("INSERT INTO "+Table.replace("/", "")+" ("+ attr +") VALUES("+ValuePrep+")");
			int n=1;
			for(String key : attribut) {		
				if(line.get(key).getClass() == FileTime.class) {
					String [] t;
					t=line.get(key).toString().split(" ");
					prepare.setDate(2, Date.valueOf(t[0]));
				}
				else if(line.get(key).getClass() == Long.class) {
					Long size = (Long) line.get(key);
					prepare.setInt(n, size.intValue());
				}
				 
				else {
						prepare.setString(n, (String)line.get(key));
				}
				n++;
			}
			prepare.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}